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
}
