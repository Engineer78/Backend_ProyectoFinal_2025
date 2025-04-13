package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Clase que representa la relación entre un producto y un proveedor.
 * Modela una asociación entre un producto y el proveedor que lo suministra, junto con el precio de compra.
 */
@Entity
public class ProductoProveedor {

    /**
     * Identificador único de la relación Producto-Proveedor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProductoProveedor;

    /**
     * Relación con el producto asociado.
     * Utiliza una clave foránea que referencia el ID del producto.
     */
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    /**
     * Relación con el proveedor asociado.
     * Utiliza una clave foránea que referencia el ID del proveedor.
     */
    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    /**
     * Precio de compra del producto proporcionado por el proveedor.
     * Es obligatorio y no puede ser nulo.
     */
    @Column(name = "precio_compra", nullable = false)
    private double precioCompra;

    /**
     * Constructor vacío requerido por JPA.
     */
    public ProductoProveedor() {
    }

    /**
     * Constructor que inicializa la relación Producto-Proveedor con los datos proporcionados.
     *
     * @param producto Producto asociado.
     * @param proveedor Proveedor asociado.
     * @param precioCompra Precio de compra del producto.
     */
    public ProductoProveedor(Producto producto, Proveedor proveedor, double precioCompra) {
        this.producto = producto;
        this.proveedor = proveedor;
        this.precioCompra = precioCompra;
    }

    /**
     * Obtiene el identificador único de la relación Producto-Proveedor.
     *
     * @return ID de la relación.
     */
    public int getIdProductoProveedor() {
        return idProductoProveedor;
    }

    /**
     * Establece el identificador único de la relación Producto-Proveedor.
     *
     * @param idProductoProveedor ID de la relación.
     */
    public void setIdProductoProveedor(int idProductoProveedor) {
        this.idProductoProveedor = idProductoProveedor;
    }

    /**
     * Obtiene el producto asociado a esta relación.
     *
     * @return Producto asociado.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado a esta relación.
     *
     * @param producto Producto asociado.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtiene el proveedor asociado a esta relación.
     *
     * @return Proveedor asociado.
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * Establece el proveedor asociado a esta relación.
     *
     * @param proveedor Proveedor asociado.
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
