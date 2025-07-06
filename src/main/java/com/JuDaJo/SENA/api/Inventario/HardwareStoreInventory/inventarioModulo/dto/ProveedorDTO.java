package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para gestionar la información de un proveedor.
 */
public class ProveedorDTO {
    private int idProveedor;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombreProveedor;

    @NotBlank(message = "El NIT del proveedor es obligatorio")
    @Size(max = 20, message = "El NIT no puede exceder los 20 caracteres")
    private String nitProveedor;

    @Size(min = 10, max = 10, message = "El teléfono debe tener exactamente 10 dígitos")
    @Pattern(
            regexp = "^3\\d{9}$",
            message = "El número de teléfono debe ser un celular válido de 10 dígitos que empiece por 3"
    )
    private String telefonoProveedor;

    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String direccionProveedor;

    /**
     * Constructor por defecto de la clase ProveedorDTO.
     * Crea una nueva instancia de ProveedorDTO sin inicializar sus propiedades.
     * Este constructor es importante para la serialización o deserialización de objetos.
     */
    public ProveedorDTO() {}

    /**
     * Constructor que inicializa una instancia de la clase ProveedorDTO con los valores proporcionados.
     *
     * @param idProveedor Identificador único del proveedor.
     * @param nombreProveedor Nombre del proveedor. No puede ser nulo o vacío y tiene un límite de 100 caracteres.
     * @param nitProveedor Número de Identificación Tributaria (NIT) del proveedor. No puede ser nulo o vacío y tiene un límite de 20 caracteres.
     * @param telefonoProveedor Número de teléfono del proveedor. Debe contener exactamente 10 dígitos y comenzar con el número 3.
     * @param direccionProveedor Dirección del proveedor. Tiene un límite máximo de 255 caracteres.
     */
    public ProveedorDTO(int idProveedor, String nombreProveedor, String nitProveedor, String telefonoProveedor, String direccionProveedor) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nitProveedor = nitProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
    }

    // Getters y Setters
    public int getIdProveedor() {
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
}

