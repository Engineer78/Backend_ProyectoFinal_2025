package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

/**
 * DTO para la entidad Rol.
 */
public class RolDTO {

    private Integer idRol;

    private String nombreRol;

    private String descripcion;

    private Integer idPerfil; // Referencia al ID del perfil asociado

    private String nombrePerfil; // Referencia al Nombre del perfil asociado

    /**
     * Constructor vacío para la entidad Rol.
     */
    public RolDTO() {}

    /**
     * Constructor con todos los parámetros.
     *
     * @param idRol ID del rol
     * @param nombreRol Nombre del rol
     * @param descripcion Descripción del rol
     * @param idPerfil ID del perfil asociado
     * @param nombrePerfil Nombre del perfil asociado
     */
    public RolDTO(Integer idRol, String nombreRol, String descripcion, Integer idPerfil, String nombrePerfil) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.idPerfil = idPerfil;
        this.nombrePerfil = nombrePerfil;
    }

    /**
     * Getters y Setters para la entidad Rol.
     */
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String NombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
}
