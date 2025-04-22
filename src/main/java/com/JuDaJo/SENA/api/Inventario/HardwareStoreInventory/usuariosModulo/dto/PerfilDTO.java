package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la entidad Perfil.
 * Contiene solo los datos necesarios que se enviarán o recibirán desde el frontend.
 */
public class PerfilDTO {

    /**
     * Identificador del perfil.
     */
    private Integer idPerfil;

    /**
     * Nombre del perfil.
     */
    private String nombrePerfil;

    /**
     * Descripción del perfil.
     */
    private String descripcion;

    /**
     * Constructor vacío para la entidad Perfil.
     */
    public PerfilDTO() {}

    /**
     * Constructor con todos los parámetros.
     *
     * @param idPerfil ID del perfil
     * @param nombrePerfil Nombre del perfil
     * @param descripcion Descripción del perfill
     */
    public PerfilDTO(Integer idPerfil, String nombrePerfil, String descripcion) {
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
        this.descripcion = descripcion;
    }
}
