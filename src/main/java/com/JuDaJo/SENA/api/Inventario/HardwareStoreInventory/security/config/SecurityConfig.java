package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.config;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.filter.JwtRequestFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * Clase de configuración utilizada para establecer políticas de seguridad en la aplicación,
 * asegurando que las solicitudes HTTP sean gestionadas de manera segura.
 * Proporciona configuraciones como la administración de autenticación, filtros JWT,
 * políticas de acceso seguro a recursos, CORS, y encriptación de contraseñas.
 *
 * Los principales componentes configurados incluyen:
 * - Cadena de filtros de seguridad para manejar autenticaciones y autorizaciones.
 * - Filtro personalizado para procesar JWT para autenticar solicitudes.
 * - Configuración de CORS para gestionar el intercambio de recursos entre diferentes orígenes.
 * - Gestión del AuthenticationManager.
 * - Provisión de un codificador de contraseñas compatible con BCrypt.
 */
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configura la cadena de filtros de seguridad utilizada para gestionar las solicitudes HTTP, habilitando
     * controles de seguridad específicos para las API REST. Esto incluye deshabilitar la protección CSRF,
     * configurar las autorizaciones de solicitudes, establecer políticas de gestión de sesiones
     * y añadir un filtro JWT personalizado para la autenticación.
     *
     * @param http: el objeto {@link HttpSecurity} para configurar la cadena de filtros de seguridad.
     * @return: la instancia {@link SecurityFilterChain} completamente compilada y configurada con
     * las políticas de seguridad especificadas.
     * @throws: genera una excepción si se produce un error durante el proceso de configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf(csrf -> csrf.disable())
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                        )
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No usar sesiones, solo JWT
                        )
                        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }


    /**
     * Configura y proporciona una fuente de configuración CORS (Cross-Origin Resource Sharing)
     * para la aplicación. Esta configuración permite el intercambio de recursos entre diferentes
     * orígenes de manera controlada y segura.
     *
     * La configuración específica incluye:
     * - Permite solicitudes solo desde http://localhost:5173
     * - Permite los métodos HTTP: GET, POST, PUT, DELETE y OPTIONS
     * - Permite todos los encabezados (headers) con "*"
     * - Permite el envío de credenciales en las solicitudes
     *
     * @return Una instancia de CorsConfigurationSource configurada con las políticas CORS especificadas
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Registra un bean {@link AuthenticationManager} para gestionar los procesos de autenticación
     * dentro de la aplicación. La configuración se obtiene de la {@link AuthenticationConfiguration} proporcionada.
     *
     * @param: configura la {@link AuthenticationConfiguration} que contiene la configuración de autenticación.
     * @return: retorna la instancia de {@link AuthenticationManager} configurada.
     * @throws: genera una excepción si se produce un error durante la inicialización de la {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Proporciona un codificador de contraseñas utilizando el algoritmo BCrypt.
     * BCrypt es un algoritmo de hash de contraseñas que implementa automáticamente
     * el salting y tiene un costo computacional ajustable para mayor seguridad.
     *
     * Este bean se utiliza en toda la aplicación para:
     * - Cifrar contraseñas nuevas antes de almacenarlas en la base de datos
     * - Verificar contraseñas durante el proceso de autenticación
     *
     * @return Una instancia de PasswordEncoder que utiliza el algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
