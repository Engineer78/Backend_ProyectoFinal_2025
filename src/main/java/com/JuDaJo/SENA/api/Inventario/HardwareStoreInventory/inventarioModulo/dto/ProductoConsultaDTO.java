package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Producto;git

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

    // Constructor basado en la entidad Producto
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
