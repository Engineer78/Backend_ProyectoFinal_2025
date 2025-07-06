package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.security.dto;

/**
 * Objeto de Transferencia de Datos (DTO) utilizado para encapsular los datos de solicitud de autenticación.
 * Esta clase se utiliza comúnmente en procesos de inicio de sesión o autenticación para transportar las
 * credenciales del usuario.
 */
public class AuthRequestDTO {

    // Atributos de la clase AuthRequestDTO.
    private String username;
    private String password;

    // Constrtor de la clase AuthRequestDTO.
    public AuthRequestDTO() {}

    // Constrtor de la clase AuthRequestDTO con argumentos.
    public AuthRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters de la clase AuthRequestDTO.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}