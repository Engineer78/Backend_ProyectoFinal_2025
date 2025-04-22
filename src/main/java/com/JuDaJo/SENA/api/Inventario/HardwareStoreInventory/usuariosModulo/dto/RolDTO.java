package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

/**
 * DTO para la entidad Rol.
 */
public class RolDTO {

    private Integer idRol;

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

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }
}
