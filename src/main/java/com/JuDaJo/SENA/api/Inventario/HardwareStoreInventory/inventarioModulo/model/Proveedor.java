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
    /**
     * Lista de relaciones entre el proveedor y los productos que suministra.
     */
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoProveedor> productoProveedores = new ArrayList<>();
    /**
     * Constructor vacío requerido por JPA.
     */
    public Proveedor() {
    }
    /**
     * Constructor con parámetros para inicializar un proveedor.
     *
     * @param nombreProveedor  Nombre del proveedor.
     * @param nitProveedor     NIT del proveedor.
     * @param telefonoProveedor Teléfono del proveedor.
     * @param direccionProveedor Dirección del proveedor.
     */
    public Proveedor(String nombreProveedor, String nitProveedor, String telefonoProveedor, String direccionProveedor) {
        this.nombreProveedor = nombreProveedor;
        this.nitProveedor = nitProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
    }

    // Getters y Setters
    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
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

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public List<ProductoProveedor> getProductoProveedores() {
        return productoProveedores;
    }

    public void setProductoProveedores(List<ProductoProveedor> productoProveedores) {
        this.productoProveedores = productoProveedores;
    }
    /**
     * Compara este proveedor con otro objeto para determinar si son iguales.
     * La comparación se realiza en función del identificador único `idProveedor`.
     * Si los objetos tienen la misma clase y el mismo `idProveedor`, se consideran iguales.
     *
     * @param o Objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica si los dos objetos son el mismo.
        if (o == null || getClass() != o.getClass()) return false; // Verifica que no sea nulo y que las clases coincidan.
        Proveedor proveedor = (Proveedor) o; // Convierte el objeto a tipo Proveedor.
        return idProveedor == proveedor.idProveedor; // Compara los identificadores únicos.
    }
    /**
     * Calcula el código hash del proveedor basado en su identificador único `idProveedor`.
     * Esto es útil para almacenar y recuperar objetos en estructuras de datos como HashMap o HashSet.
     *
     * @return Código hash del proveedor.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idProveedor); // Genera el código hash basado en el identificador único.
    }

}
