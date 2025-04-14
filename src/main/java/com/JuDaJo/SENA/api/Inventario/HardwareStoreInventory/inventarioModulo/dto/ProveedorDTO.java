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
}
