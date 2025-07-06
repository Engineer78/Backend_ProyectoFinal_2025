package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.dto.AuthRequestDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.dto.AuthResponseDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.util.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * La clase AuthController maneja los endpoints relacionados con la autenticación de la aplicación.
 * Proporciona funcionalidad para el inicio de sesión de usuarios, incluyendo la validación de
 * credenciales y la generación de tokens JWT para propósitos de autenticación.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UsuarioRepository usuarioRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Maneja el inicio de sesión del usuario validando las credenciales y generando un token JWT
     * cuando la autenticación es exitosa.
     *
     * @param request La solicitud de autenticación que contiene el nombre de usuario y la contraseña.
     * @return Un ResponseEntity que contiene un token JWT si la autenticación es exitosa,
     *         o un mensaje de error si las credenciales son inválidas.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        // Buscar usuario por nombre de usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getUsername())
                .orElse(null);

        // Validar existencia y contraseña con BCrypt
        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getContrasena())) {
            System.out.println("🚨 Usuario no encontrado para: " + request.getUsername());
            return ResponseEntity.badRequest().body("❌ Usuario o contraseña incorrectos.");
        }

        System.out.println("✅ Usuario encontrado: " + usuario.getNombreUsuario());
        System.out.println("🔐 Contraseña en BD (hash): " + usuario.getContrasena());
        System.out.println("🔐 Contraseña recibida: " + request.getPassword());

        if (!passwordEncoder.matches(request.getPassword(), usuario.getContrasena())) {
            System.out.println("🚨 Contraseña inválida.");
            return ResponseEntity.badRequest().body("❌ Usuario o contraseña incorrectos.");
        }

        // Generar token JWT
        String token = jwtUtil.generarToken(usuario);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}