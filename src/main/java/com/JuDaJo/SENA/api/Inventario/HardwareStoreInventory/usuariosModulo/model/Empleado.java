package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    /*
     * Número de Identificación del empleado.
     */
    @Column(name = "numero_documento")
    @NotNull(message = "El número de identificación no puede ser nulo.")
    private String numeroDocumento;

    /*
    * Nombre del empleado.
     */
    @Column(name = "nombres")
    @NotBlank(message = "El nombre del empleado no puede exceder los 30 caracteres")
    private String nombres;

    /*
    * Primer apellido del empleado.
     */
    @Column(name = "primer_apellido")
    @NotBlank(message = "El apellido paterno del empleado no puede exceder los 15 caracteres")
    private String apellidoPaterno;

    /*
    * Segundo apellido del empleado.
     */
    @Column(name = "segundo_apellido")
    @NotBlank(message = "El apellido materno del empleado no puede exceder los 15 caracteres")
    private String apellidoMaterno;

    


}
