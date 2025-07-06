package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * CustomUserDetailsService es una implementación del servicio UserDetailsService de Spring Security.
 * Es responsable de obtener los detalles del usuario desde la base de datos necesarios para la
 * autenticación y autorización. Esta implementación se integra con UsuarioRepository para
 * recuperar la información del usuario.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor de la clase CustomUserDetailsService que inicializa el repositorio
     * de usuarios necesario para cargar los detalles del usuario desde la base de datos.
     *
     * @param usuarioRepository el repositorio de usuarios que se utilizará para realizar
     * las consultas en la base de datos.
     */
    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga un usuario desde la base de datos utilizando su nombre de usuario.
     * Esta funcionalidad es especialmente útil para la autenticación y autorización
     * en una aplicación Spring Security.
     *
     * @param nombreUsuario el nombre de usuario que se utilizará para buscar al usuario en la base de datos.
     * @return un objeto {@link UserDetails} que representa los detalles del usuario encontrado.
     * @throws UsernameNotFoundException si no se encuentra ningún usuario con el nombre proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        // Se usa solo un rol por usuario (como en tu modelo actual)
        return new User(
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol()))
        );
    }
}