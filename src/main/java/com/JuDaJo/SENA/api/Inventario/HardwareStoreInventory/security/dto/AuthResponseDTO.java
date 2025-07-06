
package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.dto;

/**
 * Objeto de Transferencia de Datos (DTO) utilizado para encapsular los datos de respuesta de autenticación.
 * Esta clase está diseñada para transportar la información del token generado durante el proceso de
 * autenticación.
 */
public class AuthResponseDTO {

    // Atributos de la clase AuthResponseDTO.
    private String token;

    // Constructor de la clase AuthResponseDTO.
    public AuthResponseDTO(String token) {
        this.token = token;
    }

    // Getter de la clase AuthResponseDTO.
    public String getToken() {
        return token;
    }
}