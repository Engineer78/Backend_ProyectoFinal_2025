package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/*
 * La anotación @Entity indica que la clase Usuario representa una entidad persistente,
 * es decir, una tabla dentro de la base de datos gestionada por JPA (Jakarta Persistence API).
 * Esta clase será utilizada para mapear los registros de la tabla correspondiente a usuarios.
 */
@Entity
public class Usuario {
    /*
    * Identificador Unico de Usuario
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    /*
    * Nombre del usuario
    * */

    @Column(name = "nombre_usuario")
    @NotNull(message = "El nombre del usuario no puede ser nulo")
    private String nombreUsuario;
    /*
    * Contraseña asginada al usuario
    * */
    @Column(name="contraseña_usuario")
    @NotNull(message = "La contraseña del usuario no puede ser nula")
    private String contraseña;
    /*
    * Relación entre Usuario y Rol
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id_rol")
    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;
    /*
    * Constructor Vacío requerido por el JPA
    * */
    public Usuario() {}





}
