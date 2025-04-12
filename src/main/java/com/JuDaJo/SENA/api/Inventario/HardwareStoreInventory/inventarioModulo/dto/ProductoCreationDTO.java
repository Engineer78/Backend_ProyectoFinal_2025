package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de un producto.
 * Este DTO se utiliza para recibir los datos necesarios para crear un nuevo producto
 * incluyendo información de la categoría y el proveedor.
 */

public class ProductoCreationDTO {

    // Encapsulación de la clase ProductoDTO
    // Se utiliza para validar los datos del producto

    @NotNull(message = "El producto es obligatorio")
    @Valid
    private ProductoDTO producto;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 255, message = "El nombre de la categoría no puede exceder los 255 caracteres")
    private String nombreCategoria;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 255, message = "El nombre del proveedor no puede exceder los 255 caracteres")
    private String nombreProveedor;

    @NotBlank(message = "El NIT del proveedor es obligatorio")
    @Size(min = 8, max = 15, message = "El NIT del proveedor debe tener entre 8 y 15 caracteres")
    private String nitProveedor;

    @NotBlank(message = "La dirección del proveedor es obligatoria")
    @Size(max = 255, message = "La dirección del proveedor no puede exceder los 255 caracteres")
    private String direccionProveedor;

    @NotBlank(message = "El teléfono del proveedor es obligatorio")
    @Size(min = 7, max = 15, message = "El teléfono del proveedor debe tener entre 7 y 15 caracteres")
    private String telefonoProveedor;

}
