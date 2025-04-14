package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para gestionar la información de un proveedor.
 */
public class ProveedorDTO {
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombreProveedor;

    @NotBlank(message = "El NIT del proveedor es obligatorio")
    @Size(max = 20, message = "El NIT no puede exceder los 20 caracteres")
    private String nitProveedor;

    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    private String telefonoProveedor;

    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String direccionProveedor;
    // Constructor vacío
    public ProveedorDTO() {}
    // Constructor con todos los atributos
    public ProveedorDTO(int idProveedor, String nombreProveedor, String nitProveedor, String telefonoProveedor, String direccionProveedor) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nitProveedor = nitProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
    }

    // Getters y Setters
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }
}
