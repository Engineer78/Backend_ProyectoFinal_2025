package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto;

import jakarta.validation.constraints.*;

/**
 * DTO (Data Transfer Object) para la entidad Permiso.
 * Representa los datos de un permiso que serán transferidos entre
 * la capa de control y la capa de servicio.
 */
public class PermisoDTO {

    private int idPermiso;

    @NotBlank(message = "El nombre del permiso no puede estar en blanco")
    @Size(max = 100, message = "El nombre del permiso no puede exceder los 100 caracteres")
    private String nombrePermiso;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;

    // Constructor vacío
    public PermisoDTO() {}

    // Constructor completo
    public PermisoDTO(int idPermiso, String nombrePermiso, String descripcion) {
        this.idPermiso = idPermiso;
        this.nombrePermiso = nombrePermiso;
        this.descripcion = descripcion;
    }

    // Getters y Setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
