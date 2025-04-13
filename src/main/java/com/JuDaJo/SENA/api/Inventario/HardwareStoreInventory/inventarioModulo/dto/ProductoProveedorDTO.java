package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para gestionar la información de ProductoProveedor.
 */
public class ProductoProveedorDTO {

    private int idProductoProveedor;

    @NotNull(message = "El producto es obligatorio")
    private int idProducto;

    @NotNull(message = "El proveedor es obligatorio")
    private int idProveedor;

    @Min(value = 0, message = "El precio de compra debe ser mayor o igual a 0")
    private double precioCompra;

    // Constructor vacío
    public ProductoProveedorDTO() {}

    // Constructor con parámetros
    public ProductoProveedorDTO(int idProductoProveedor, int idProducto, int idProveedor, double precioCompra) {
        this.idProductoProveedor = idProductoProveedor;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.precioCompra = precioCompra;
    }

    // Getters y Setters
    public int getIdProductoProveedor() {
        return idProductoProveedor;
    }

    public void setIdProductoProveedor(int idProductoProveedor) {
        this.idProductoProveedor = idProductoProveedor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }
}
