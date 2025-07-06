package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.filter;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que extiende de OncePerRequestFilter para procesar y validar tokens JWT en las
 * solicitudes HTTP entrantes. Se encarga de:
 *
 * - Extraer el JWT del encabezado Authorization
 * - Validar el token y su expiración
 * - Cargar la información del usuario
 * - Establecer el contexto de seguridad
 *
 * Este filtro es fundamental para la autenticación sin estado usando JWT y se integra
 * con la cadena de filtros de Spring Security.
 *
 * Notas:
 * - Se aplica una vez por solicitud
 * - Utiliza JwtUtil para operaciones con tokens
 * - Requiere UserDetailsService para cargar detalles del usuario
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Inyección de dependencias:
     * - jwtUtil: Utilidad para manejar operaciones relacionadas con JWT (generación, validación)
     * - userDetailsService: Servicio para cargar información del usuario durante la autenticación
     */
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Procesa una solicitud HTTP entrante verificando la presencia y validez de un
     * token JWT en el encabezado Authorization. Si se encuentra un token válido,
     * recupera el nombre de usuario, valida el token y autentica al usuario.
     * Finalmente, pasa la solicitud a lo largo de la cadena de filtros.
     *
     * @param request  objeto de solicitud HTTP que contiene la información del cliente
     * @param response objeto de respuesta HTTP para enviar datos al cliente
     * @param chain    cadena de filtros para pasar la solicitud y respuesta al siguiente filtro
     * @throws ServletException si ocurren errores específicos del servlet durante el filtrado
     * @throws IOException      si ocurre un error de E/S durante el filtrado
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        System.out.println("📥 Filtro JWT activo para: " + request.getRequestURI());


        // 🔒 Lógica JWT normal
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verifica si hay un token en el encabezado Authorization
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Elimina el prefijo "Bearer "
            username = jwtUtil.extractUsername(jwt); // Nuevo método adaptado con jjwt 0.11.x
        }

        // Si hay usuario y aún no está autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validar que el token JWT sea válido
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("✅ Token válido. Autenticando a: " + username);
            }
        }
        System.out.println("🔐 Encabezado Authorization: " + authHeader);
        System.out.println("🔎 Usuario extraído del token: " + username);
        System.out.println("👤 Ya está autenticado?: " + (SecurityContextHolder.getContext().getAuthentication() != null));

        chain.doFilter(request, response);
    }
}