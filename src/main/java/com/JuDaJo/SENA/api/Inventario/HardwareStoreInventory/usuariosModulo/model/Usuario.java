package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * La anotación @Entity indica que la clase Usuario representa una entidad persistente,
 * es decir, una tabla dentro de la base de datos gestionada por JPA (Jakarta Persistence API).
 * Esta clase será utilizada para mapear los registros de la tabla correspondiente a usuarios.
 */
@Entity
public class Usuario {

    /**
     * Identificador Unico de Usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    /**
     * Nombre del usuario
     */
    @Column(name = "nombre_usuario")
    @NotNull(message = "El nombre del usuario no puede ser nulo")
    private String nombreUsuario;

    /**
     * Contraseña asginada al usuario
     */
    @Column(name = "contraseña_usuario")
    @NotNull(message = "La contraseña del usuario no puede ser nula")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    private String contrasena;


    /**
     * Relación entre Usuario y Rol
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", referencedColumnName = "idRol", nullable = false)
    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;

    /**
     * Representa la relación uno a uno entre la entidad Usuario y la entidad Empleado.
     * Esto indica que cada Usuario tiene asociado un único Empleado.
     * Está mapeado por el atributo "usuario" en la entidad Empleado.
     * La anotación @OneToOne especifica que existe una relación 1:1 entre ambas entidades.
     * El fetch se define como FetchType.LAZY, lo que significa que la asociación
     * se cargará de forma diferida, es decir, cuando se acceda explícitamente al atributo.
     */
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Empleado empleado;

    /**
     * Constructor Vacío requerido por el JPA
     */
    public Usuario() {
    }

    /**
     * Constructor con argumentos para facilitar la creación de instancias
     */
    public Usuario(String nombreUsuario, String contrasena, Rol rol, Empleado empleado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.empleado = empleado;
    }

    // Métodos Getters and Setters

    /**
     * Obtiene el id del usuario.
     *
     * @return idUsuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el id
     *
     * @param idUsuario id del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario
     *
     * @param nombreUsuario Nombre del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña
     *
     * @return contraseña.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña
     *
     * @param contrasenia contraseña del usuario.
     */
    public void setContrasena(String contrasenia) {
        this.contrasena = contrasenia;
    }

    /**
     * Obtiene el rol
     *
     * @return rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol
     *
     * @param rol rol del usuario.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el empleado asociado al usuario.
     *
     * @return el empleado asociado.
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * Establece el empleado asociado al usuario.
     *
     * @param empleado empleado que se desea asociar al usuario.
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    /**
     * Compara usuarios por ID para garantizar unicidad lógica.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario;
    }

    /**
     * Genera hash basado en el ID del usuario.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

}
