package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Representa un producto dentro del inventario.
 * Contiene información del producto, como código, nombre, cantidad, valor unitario y su relación con la categoría y proveedores.
 */
@Entity
public class Producto {

    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;

    /**
     * Código único del producto.
     */
    @Column(name = "codigo_producto")
    @NotNull(message = "El código del producto no puede ser nulo")
    @Min(value = 1, message = "El código de producto debe ser mayor o igual a 1")
    @Max(value = 99999, message = "El código de producto debe ser menor o igual a 99999")
    private Integer codigoProducto;

    /**
     * Nombre del producto.
     */
    @Column(name = "nombre_producto")
    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    private String nombreProducto;

    /**
     * Cantidad disponible del producto.
     */
    @Column(name = "cantidad")
    @Min(value = 1, message = "La cantidad no puede ser negativa")
    @Max(value = 99999, message = "La cantidad no puede ser mayor a 99999")
    private int cantidad;

    /**
     * Valor unitario del producto.
     */
    @Column(name = "valor_unitario_producto")
    @Min(value = 0, message = "El valor unitario no puede ser negativo")
    private double valorUnitarioProducto;

    /**
     * Valor Total del producto.
     */
    @Column(name = "valor_total_producto")
    @Min(value = 0, message = "El valor total no puede ser negativa")
    private double valorTotalProducto; // Nuevo campo para el valor total del producto

    /**
     * Relación con la categoría a la que pertenece el producto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "La categoría no puede ser nula")
    private Categoria categoria;

    /**
     * Lista de relaciones entre el producto y sus proveedores.
     * Se utiliza para modelar la relación entre un producto y los proveedores que lo suministran.
     */
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoProveedor> productoProveedores = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    @NotNull(message = "El proveedor no puede ser nulo")
    private Proveedor proveedor;

    /**
     * Imagen del producto en formato Base64.
     */
    @Lob // Indica que es un campo de gran tamaño (Large Object)
    @Column(name = "imagen", columnDefinition = "LONGTEXT") // Se define como TEXT en la base de datos
    private String imagen;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Producto() {
    }

    //Getters y Setters
    /**
     * Obtiene el identificador del producto.
     * @return idProducto Identificador único.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     * @param idProducto Identificador único.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el código del producto.
     * @return código del producto.
     */
    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Establece el código del producto.
     * @param codigoProducto Código único del producto.
     */
    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * @return nombre del producto.
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Establece el nombre del producto.
     * @param nombreProducto Nombre del producto.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene la cantidad disponible del producto.
     * @return cantidad disponible.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible del producto.
     * @param cantidad Cantidad disponible.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el valor unitario del producto.
     * @return valor unitario del producto.
     */
    public double getValorUnitarioProducto() {
        return valorUnitarioProducto;
    }

    /**
     * Establece el valor unitario del producto.
     * @param valorUnitarioProducto Valor unitario del producto.
     */
    public void setValorUnitarioProducto(double valorUnitarioProducto) {
        this.valorUnitarioProducto = valorUnitarioProducto;
    }

    /**
     * Obtiene el valor total del producto.
     * @return valor total del producto.
     */
    // Getter para valorTotalProducto
    public double getValorTotalProducto() {
        return valorTotalProducto;
    }

    /**
     * Establece el valor total del producto.
     * @param valorTotalProducto valor total del producto.
     */
    // Setter para valorTotalProducto
    public void setValorTotalProducto(double valorTotalProducto) {
        this.valorTotalProducto = valorTotalProducto;
    }

    /**
     * Obtiene la categoría a la que pertenece el producto.
     * @return categoría del producto.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría a la que pertenece el producto.
     * @param categoria Categoría del producto.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    /**
     * Obtiene la lista de relaciones entre el producto y los proveedores.
     * @return lista de relaciones Producto-Proveedor.
     */
    public List<ProductoProveedor> getProductoProveedores() {

        return productoProveedores;
    }
    /**
     * Establece la lista de relaciones entre el producto y los proveedores.
     * @param productoProveedores Lista de relaciones Producto-Proveedor.
     */
    public void setProductoProveedores(List<ProductoProveedor> productoProveedores) {
        this.productoProveedores = productoProveedores;
    }
    /**
     * Agrega una relación entre este producto y un proveedor.
     * @param productoProveedor Relación Producto-Proveedor.
     */
    public void agregarProductoProveedor(ProductoProveedor productoProveedor) {
        productoProveedores.add(productoProveedor);
        productoProveedor.setProducto(this); // Establece la relación bidireccional
    }
    /**
     * Elimina una relación entre este producto y un proveedor.
     * @param productoProveedor Relación Producto-Proveedor a eliminar.
     */
    public void eliminarProductoProveedor(ProductoProveedor productoProveedor) {
        productoProveedores.remove(productoProveedor);
        productoProveedor.setProducto(null); // Limpia la relación bidireccional
    }
    /**
     * Calcula el valor total del producto (cantidad x valor unitario).
     */
    private void calcularValorTotal() {
        this.valorTotalProducto = this.cantidad * this.valorUnitarioProducto;
    }

    /**
     * Obtiene la imagen del producto en formato Base64.
     * @return imagen en formato Base64.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del producto en formato Base64.
     * @param imagen Cadena en Base64 que representa la imagen.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Compara este producto con otro objeto.
     * La comparación se realiza por el ID del producto.
     * @param o Objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProducto == producto.idProducto;
    }

    /**
     * Genera un código hash para este producto basado en su ID.
     * @return código hash del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProducto);
    }
}
