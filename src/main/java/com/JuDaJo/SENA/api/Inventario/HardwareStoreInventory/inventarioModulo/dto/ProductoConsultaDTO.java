package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Producto;

public class ProductoConsultaDTO {

    // Atributos de la clase ProductoConsultaDTO
    // Estos atributos son los que se van a mostrar en la consulta de productos
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidad; // Existencias
    private Double valorUnitarioProducto;
    private Double valorTotalProducto;
    private String proveedor; // Nombre del proveedor
    private String nitProveedor; // NIT del proveedor
    private String imagen; // Base64 de la imagen

    /**
     * Constructor por defecto para la clase ProductoConsultaDTO.
     * Inicializa una nueva instancia de ProductoConsultaDTO sin establecer ningún valor.
     * Este constructor puede ser utilizado para crear una instancia vacía del DTO
     * y posteriormente poblar sus atributos a través de los métodos set.
     */
    public ProductoConsultaDTO() {
    }

    /**
     * Constructor que inicializa un objeto ProductoConsultaDTO utilizando una instancia de Producto.
     * Mapea las propiedades del objeto Producto dado a los campos correspondientes de este DTO.
     *
     * @param producto la entidad Producto de la cual se mapearán los datos. Contiene
     *                 detalles como ID, nombre, cantidad, valor unitario, información del proveedor,
     *                 e imagen. Si la información del proveedor no está disponible, los campos
     *                 relacionados con el proveedor se establecerán como null.
     */
    public ProductoConsultaDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.nombreProducto = producto.getNombreProducto();
        this.cantidad = producto.getCantidad();
        this.valorUnitarioProducto = producto.getValorUnitarioProducto();
        this.valorTotalProducto = producto.getValorUnitarioProducto() * producto.getCantidad();
        this.proveedor = producto.getProveedor() != null ? producto.getProveedor().getNombreProveedor() : null;
        this.nitProveedor = producto.getProveedor() != null ? producto.getProveedor().getNitProveedor() : null;
        this.imagen = producto.getImagen();
    }

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitarioProducto() {
        return valorUnitarioProducto;
    }

    public void setValorUnitarioProducto(Double valorUnitarioProducto) {
        this.valorUnitarioProducto = valorUnitarioProducto;
    }

    public Double getValorTotalProducto() {
        return valorTotalProducto;
    }

    public void setValorTotalProducto(Double valorTotalProducto) {
        this.valorTotalProducto = valorTotalProducto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
