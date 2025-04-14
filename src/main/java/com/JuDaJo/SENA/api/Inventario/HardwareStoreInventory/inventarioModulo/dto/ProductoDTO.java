package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Producto;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO (Data Transfer Object) para la entidad Producto.
 * Este DTO se utiliza para transferir datos de la entidad Producto entre la capa de servicio y la capa de presentación.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductoDTO {
    private int idProducto;

    @NotNull(message = "El código del producto no puede ser nulo")
    private Integer codigoProducto;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombreProducto;

    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;

    @PositiveOrZero(message = "El valor unitario debe ser mayor o igual a cero")
    private double valorUnitarioProducto;

    private double valorTotalProducto; // Nuevo campo para el valor total del producto

    private String nombreCategoria;

    private Integer idProveedor;

    private String nombreProveedor;

    private String nitProveedor;

    private String direccionProveedor;

    private String telefonoProveedor;

    private List<Integer> productoProveedores;
    /**
     * Imagen del producto en formato Base64.
     */
    private String imagen;
    // Constructor vacío para serialización/deserialización
    public ProductoDTO() {}

    // Constructor que inicializa los campos desde la entidad Producto
    public ProductoDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.codigoProducto = producto.getCodigoProducto();
        this.nombreProducto = producto.getNombreProducto();
        this.cantidad = producto.getCantidad();
        this.valorUnitarioProducto = producto.getValorUnitarioProducto();
        this.valorTotalProducto = producto.getCantidad() * producto.getValorUnitarioProducto();
        this.nombreCategoria = (producto.getCategoria() != null) ? producto.getCategoria().getNombreCategoria() : null;
        //this.imagen = producto.getImagen();
        if (producto.getImagen() != null && !producto.getImagen().startsWith("data:image")) {
            this.imagen = "data:image/png;base64," + producto.getImagen();
        } else {
            this.imagen = producto.getImagen();
        }
        if (producto.getProveedor() != null) {
            this.idProveedor = producto.getProveedor().getIdProveedor();
            this.nombreProveedor = producto.getProveedor().getNombreProveedor();
            this.nitProveedor = producto.getProveedor().getNitProveedor();
            this.telefonoProveedor = producto.getProveedor().getTelefonoProveedor();
            this.direccionProveedor = producto.getProveedor().getDireccionProveedor();
        }
    }
}
