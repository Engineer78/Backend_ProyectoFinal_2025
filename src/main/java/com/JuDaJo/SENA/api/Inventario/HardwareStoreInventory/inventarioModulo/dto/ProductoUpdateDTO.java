package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min; // Esta importación también es de Jakarta

public class ProductoUpdateDTO {

    private int idProducto;

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

    // Getters y setters
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

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
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
}
