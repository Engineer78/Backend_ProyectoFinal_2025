package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min; // Esta importación también es de Jakarta

public class ProductoUpdateDTO {
    @NotNull(message = "El código del producto no puede ser nulo")
    private Integer codigoProducto;

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    private String nombreProducto;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

    @Min(value = 0, message = "El valor unitario no puede ser negativo")
    private double valorUnitarioProducto;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 255, message = "El nombre de la categoría no puede exceder los 255 caracteres")
    private String nombreCategoria;

    @Lob // Indica que es un campo de gran tamaño (Large Object)
    private String imagen;

    @NotNull
    private Integer idProveedor;

    @NotBlank
    private String nitProveedor;

    @NotBlank
    private String direccionProveedor;

    @NotBlank
    private String telefonoProveedor;
}
