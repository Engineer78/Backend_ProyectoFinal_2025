package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

/**
 * DTO (Data Transfer Object) para representar la relación entre un rol y un permiso.
 */
public class RolPermisoDTO {

    private int idRolPermiso;

    private int idRol;

    private String nombreRol;

    private int idPermiso;

    private String nombrePermiso;

    // Constructor vacío
    public RolPermisoDTO() {
    }

    // Constructor completo
    public RolPermisoDTO(int idRolPermiso, int idRol, String nombreRol, int idPermiso, String nombrePermiso) {
        this.idRolPermiso = idRolPermiso;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.idPermiso = idPermiso;
        this.nombrePermiso = nombrePermiso;
    }

    public int getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(int idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombrePermiso() {
        return nombrePermiso;
    }

    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }
}
