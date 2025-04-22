package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la entidad Rol.
 */
public class RolDTO {

    private Integer idRol;

    @NotBlank(message = "El nombre del rol no puede estar en blanco")
    @Size(max = 20, message = "El nombre del rol no puede exceder los 20 caracteres")
    private String nombreRol;

    private Integer idPerfil; // Referencia al ID del perfil asociado

    /**
     * Constructor vacío para la entidad Rol.
     */
    public RolDTO() {}

    /**
     * Constructor con parámetros para la entidad Rol.
     */
    public RolDTO(Integer idRol, String nombreRol, Integer idPerfil) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.idPerfil = idPerfil;
    }

}
