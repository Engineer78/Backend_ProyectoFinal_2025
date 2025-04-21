package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



/**
 * Esta clase representa un empleado dentro de usuarios.
 */
@Entity
public class Empleado {

    /**
     * Identificador único del empleado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleado;

    /**
     * Número de Identificación del empleado.
     */
    @Column(name = "numero_documento")
    @NotNull(message = "El número de identificación no puede ser nulo.")
    private String numeroDocumento;

    /**
    * Nombre del empleado.
     */
    @Column(name = "nombres")
    @NotBlank(message = "El nombre del empleado no puede exceder los 30 caracteres")
    private String nombres;

    /**
    * Primer apellido del empleado.
     */
    @Column(name = "primer_apellido")
    @NotBlank(message = "El apellido paterno del empleado no puede exceder los 15 caracteres")
    private String apellidoPaterno;

    /**
    * Segundo apellido del empleado.
     */
    @Column(name = "segundo_apellido")
    @NotBlank(message = "El apellido materno del empleado no puede exceder los 15 caracteres")
    private String apellidoMaterno;

    /**
    * Teléfono del empleado.
     */
    @Column(name = "telefono_movil")
    @NotBlank(message = "El teléfono del empleado no puede exceder los 10 digitos")
    private String telefonoMovil;

    /**
     * Drireccion del empleado.
     */
    @Column(name = "direccion_residencia")
    @NotBlank(message = "La dirección de residencia del empleado puede contener caracteres, números, letras y no debe exceder los 45 caracteres")
    private String direccionResidencia;

    /**
     * Contacto emergencia del empleado.
     */
    @Column(name = "contacto_emergencia")
    @NotBlank(message = "El nombre del contacto de emergencia del empleado no debe exceder los 45 carácteres")
    private String contactoEmergencia;

    /**
     * Teléfono del contacto de emergencia del empleado.
     */
    @Column(name ="telefono_contacto")
    @NotBlank(message = "telefono_contacto")
    private String telefonoContacto;

    /**
     * Relación a la entidad Usuario.
     */
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "id_usurio", referencedColumnName = "idUsuario", nullable = false)
    //private Usuario usuario;

    /**
     * Constructor vacío requerido por JPA.
    */
    public Empleado() {
    }

     /**
     * Constructor con argumentos para la clase Empleado.
     */
    public Empleado(String numeroDocumento, String nombres, String apellidoPaterno, String apellidoMaterno, String telefonoMovil, String direccionResidencia, String contactoEmergencia, String telefonoContacto, Usuario usuario) {
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefonoMovil = telefonoMovil;
        this.direccionResidencia = direccionResidencia;
        this.contactoEmergencia = contactoEmergencia;
        this.telefonoContacto = telefonoContacto;
    }
}
