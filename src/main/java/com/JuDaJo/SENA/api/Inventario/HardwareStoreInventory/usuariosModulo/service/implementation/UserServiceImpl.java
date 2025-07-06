package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioChangePasswordDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de Usuario.
 * Se encarga de la lógica de negocio relacionada con la creación de usuarios.
 */
@Service
public class UserServiceImpl implements UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Inyección de dependencia del repositorio de usuarios.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que recibe el repositorio como parámetro.
     * @param usuarioRepository repositorio para la gestión de usuarios.
     */
    public UserServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea un nuevo usuario a partir del DTO recibido.
     * @param usuarioDTO DTO con la información del nuevo usuario.
     * @return DTO del usuario creado.
     */
    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {

        // 🔒 Validar nombre de usuario duplicado
        if (usuarioRepository.existsByNombreUsuario(usuarioDTO.getNombre())) {
            throw new DuplicadoException("El nombre de usuario ya existe");
        }

        // 🛠️ Conversión de DTO a Entidad
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombre());

        // ✅ Codificación de contraseña desde el DTO, ya validada por @Pattern
        String contrasenaCodificada = passwordEncoder.encode(usuarioDTO.getContrasena());
        usuario.setContrasena(contrasenaCodificada);

        // 💾 Guardar en la base de datos
        Usuario guardado = usuarioRepository.save(usuario);

        // 🔁 Convertir la entidad guardada de nuevo a DTO para la respuesta
        UsuarioDTO respuesta = new UsuarioDTO();
        respuesta.setNombre(guardado.getNombreUsuario());

        return respuesta;

    }

    @Override
    public void cambiarContrasena(String nombreUsuario, UsuarioChangePasswordDTO dto) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getActual(), usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual no es correcta");
        }

        if (dto.getNueva().equals(dto.getActual())) {
            throw new RuntimeException("La nueva contraseña no puede ser igual a la actual");
        }

        if (!dto.getNueva().equals(dto.getConfirmar())) {
            throw new RuntimeException("La nueva contraseña y su confirmación no coinciden");
        }

        usuario.setContrasena(passwordEncoder.encode(dto.getNueva()));
        usuarioRepository.save(usuario);

        System.out.println("Contraseña actual enviada: " + dto.getActual());
        System.out.println("Contraseña almacenada: " + usuario.getContrasena());

    }

}
