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
}
