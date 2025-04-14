package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

}
