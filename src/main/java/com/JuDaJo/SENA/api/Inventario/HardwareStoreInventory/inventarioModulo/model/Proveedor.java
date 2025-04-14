package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa un proveedor de productos.
 */
@Entity
public class Proveedor {
    /**
     * Identificador único del proveedor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    /**
     * Nombre del proveedor.
     */
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre del proveedor no puede exceder los 100 caracteres")
    @Column(name = "nombre_proveedor")
    private String nombreProveedor;

    /**
     * NIT del proveedor.
     */
    @NotBlank(message = "El NIT del proveedor es obligatorio")
    @Size(max = 20, message = "El NIT no puede exceder los 20 caracteres")
    @Column(name = "nit_proveedor")
    private String nitProveedor;

    /**
     * Teléfono del proveedor.
     */
    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    @Column(name = "telefono_proveedor")
    private String telefonoProveedor;

    /**
     * Dirección del proveedor.
     */
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    @Column(name = "direccion_proveedor")
    private String direccionProveedor;
}
