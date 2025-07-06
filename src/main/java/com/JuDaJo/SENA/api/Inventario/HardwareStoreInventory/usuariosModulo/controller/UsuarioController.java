package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.util.JwtUtil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioChangePasswordDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Controlador Rest para gestionar la creación de usuarios en la entidad Empleado
 * Proporcionará la funcionalidad: CrearUsuarios
 * */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Constructor para inicializar los repositorios necesarios
     * @param usuarioRepository
     * @param rolRepository
     * */
    public UsuarioController(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }
    /**
     * Crear un Nuevo Usuario
     * */
    @PostMapping
    @Transactional
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult bindingResult) {
        // Validación de campos
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Buscar el rol por ID
            Rol rol = rolRepository.findById(usuarioDTO.getRol())
                    .orElseGet(() -> {
                        throw new RuntimeException("Rol no encontrado con ID: " + usuarioDTO.getRol());
                    });

            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombreUsuario(usuarioDTO.getNombre());
            nuevoUsuario.setContrasena(usuarioDTO.getContrasena()); // Aquí podrías cifrar la contraseña si usas BCrypt
            nuevoUsuario.setRol(rol);

            // Guardar en la base de datos
            nuevoUsuario = usuarioRepository.save(nuevoUsuario);

            // Crear respuesta DTO (sin contraseña por seguridad)
            UsuarioDTO respuestaDTO = new UsuarioDTO(
                    nuevoUsuario.getNombreUsuario(),
                    null, // No retornamos contraseña
                    nuevoUsuario.getRol().getIdRol()
            );

            return new ResponseEntity<>(respuestaDTO, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al crear el usuario: " + e.getMessage());
        }
    }

    /**
     * Cambia la contraseña de un usuario autenticado mediante un token de autorización.
     *
     * @param dto   Objeto que contiene los datos necesarios para realizar el cambio de contraseña.
     *              Debe incluir la contraseña actual y la nueva contraseña, y debe ser validado.
     * @param token Token de autorización proporcionado en el encabezado de la solicitud.
     *              Este token se utiliza para identificar al usuario actual.
     * @return Una respuesta HTTP que indica que la contraseña fue actualizada exitosamente.
     */
    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(
            @RequestBody @Valid UsuarioChangePasswordDTO dto,
            @RequestHeader("Authorization") String token
    ) {
        String usuarioActual = jwtUtil.extractUsername(token.substring(7));
        usuarioService.cambiarContrasena(usuarioActual, dto);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

}
