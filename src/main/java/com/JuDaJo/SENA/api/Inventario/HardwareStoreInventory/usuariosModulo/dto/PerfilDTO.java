package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;

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

    /**
     * Representación en cadena de texto del objeto PerfilDTO.
     *
     * @return Una cadena representando los atributos del perfil.
     */
    @Override
    public String toString() {
        return "PerfilDTO{idPerfil=" + idPerfil + ", nombrePerfil='" + nombrePerfil + "', descripcion='" + descripcion + "'}";
    }

    /**
     * Compara dos objetos PerfilDTO para determinar si son iguales.
     *
     * @param obj El objeto con el que se comparará.
     * @return true si los objetos son iguales, false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PerfilDTO that = (PerfilDTO) obj;
        return idPerfil.equals(that.idPerfil);
    }

    /**
     * Calcula el valor hash de un objeto PerfilDTO.
     *
     * @return El valor hash calculado para el perfil.
     */
    @Override
    public int hashCode() {
        return idPerfil.hashCode();
    }
}
