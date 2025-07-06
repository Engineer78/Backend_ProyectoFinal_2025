package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Producto;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO (Data Transfer Object) para la entidad Producto.
 * Este DTO se utiliza para transferir datos de la entidad Producto entre la capa de servicio y la capa de presentación.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductoDTO {
    private int idProducto;

    @NotNull(message = "El código del producto no puede ser nulo")
    @Min(value = 1, message = "El código del producto debe ser mayor o igual a 1")
    @Max(value = 99999, message = "El código del producto debe ser menor o igual a 99999")
    private Integer codigoProducto;

    private String codigoProductoFormateado;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 100, message = "El nombre del producto no puede exceder los 100 caracteres")
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

    /**
     * Constructor por defecto de la clase ProductoDTO.
     * Crea una nueva instancia de ProductoDTO sin inicializar ninguna propiedad.
     * Este constructor es necesario para la serialización/deserialización de objetos.
     */
    public ProductoDTO() {}

    /**
     * Constructor que crea un objeto de tipo ProductoDTO a partir de un objeto Producto.
     * Inicializa todas las propiedades del ProductoDTO basándose en los valores del Producto.
     *
     * @param producto Objeto de tipo Producto del cual se obtendrán los valores a asignar
     *                 en las propiedades de este ProductoDTO. No debe ser nulo.
     */
    public ProductoDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.codigoProducto = producto.getCodigoProducto();
        this.codigoProductoFormateado = String.format("%05d", producto.getCodigoProducto());
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

    // Getters y Setters para cada campo
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorUnitarioProducto() {
        return valorUnitarioProducto;
    }

    public void setValorUnitarioProducto(double valorUnitarioProducto) {
        this.valorUnitarioProducto = valorUnitarioProducto;
    }

    public double getValorTotalProducto() {
        return valorTotalProducto;
    }

    public void setValorTotalProducto(double valorTotalProducto) {
        this.valorTotalProducto = valorTotalProducto;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
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

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public List<Integer> getProductoProveedores() {
        return productoProveedores;
    }

    public void setProductoProveedores(List<Integer> productoProveedores) {
        this.productoProveedores = productoProveedores;
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

    // Nuevo campo para mostrar el código formateado
    public String getCodigoProductoFormateado() {
        return String.format("%05d", this.codigoProducto);
    }
}
