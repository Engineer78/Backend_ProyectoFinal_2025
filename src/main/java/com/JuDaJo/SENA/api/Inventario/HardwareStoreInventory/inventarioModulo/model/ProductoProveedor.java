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

    /**
     * Obtiene el precio de compra del producto proporcionado por el proveedor.
     *
     * @return Precio de compra.
     */
    public double getPrecioCompra() {
        return precioCompra;
    }

    /**
     * Establece el precio de compra del producto proporcionado por el proveedor.
     *
     * @param precioCompra Precio de compra.
     */
    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    /**
     * Método llamado automáticamente antes de eliminar la relación.
     * Se utiliza para eliminar esta relación de las listas de `producto` y `proveedor` correspondientes,
     * manteniendo la consistencia de los datos.
     */
    @PreRemove
    private void removeRelationships() {
        if (producto != null && producto.getProductoProveedores() != null) {
            producto.getProductoProveedores().removeIf(pp -> pp.getIdProductoProveedor() == this.idProductoProveedor);
        }
        if (proveedor != null && proveedor.getProductoProveedores() != null) {
            proveedor.getProductoProveedores().removeIf(pp -> pp.getIdProductoProveedor() == this.idProductoProveedor);
        }
    }

    /**
     * Representación en forma de cadena de la relación Producto-Proveedor.
     *
     * @return Cadena con los datos de la relación.
     */
    @Override
    public String toString() {
        return "ProductoProveedor{" +
                "idProductoProveedor=" + idProductoProveedor +
                ", producto=" + producto +
                ", proveedor=" + proveedor +
                ", precioCompra=" + precioCompra +
                '}';
    }

    /**
     * Compara esta relación con otro objeto para determinar si son iguales.
     * La comparación se realiza en función de los objetos `producto` y `proveedor`.
     *
     * @param o Objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoProveedor that = (ProductoProveedor) o;
        return producto.equals(that.producto) && proveedor.equals(that.proveedor);
    }

    /**
     * Calcula el código hash de la relación basado en los objetos `producto` y `proveedor`.
     *
     * @return Código hash de la relación.
     */
    @Override
    public int hashCode() {
        return Objects.hash(producto, proveedor);
    }
}
