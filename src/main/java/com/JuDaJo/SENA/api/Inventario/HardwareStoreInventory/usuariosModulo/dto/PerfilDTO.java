package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

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

    // Getters y Setters
    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
