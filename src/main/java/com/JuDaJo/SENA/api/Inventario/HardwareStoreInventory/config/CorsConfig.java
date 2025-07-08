package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Clase de configuración para establecer el Intercambio de Recursos de Origen Cruzado (CORS) en la aplicación.
 * Implementa la interfaz {@link WebMvcConfigurer} para personalizar el comportamiento del mapeo CORS.
 *
 * Esta configuración permite establecer orígenes específicos, métodos HTTP, cabeceras y credenciales
 * para las solicitudes entrantes desde fuentes externas al servidor.
 *
 * La configuración especifica:
 * - Todas las rutas están habilitadas para los mapeos CORS.
 * - Solo se permiten solicitudes que se originen desde "http://localhost:5173".
 * - Los métodos HTTP permitidos incluyen GET, POST, PUT, DELETE y OPTIONS.
 * - Las cabeceras permitidas incluyen "Authorization" y "Content-Type".
 * - Se permiten las credenciales.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer{

    /**
     * Configura los ajustes de Intercambio de Recursos de Origen Cruzado (CORS) para la aplicación.
     *
     * El método retorna un {@link WebMvcConfigurer} que personaliza el comportamiento de los mapeos CORS.
     * Define la política CORS para todas las rutas HTTP permitiendo orígenes específicos, métodos HTTP,
     * cabeceras y habilitando el uso de credenciales.
     *
     * @return una instancia de {@link WebMvcConfigurer} con la configuración CORS definida
     */
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // Cambia si tu frontend cambia de puerto
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type")
                        .allowCredentials(true);
            }
        };
    }
}