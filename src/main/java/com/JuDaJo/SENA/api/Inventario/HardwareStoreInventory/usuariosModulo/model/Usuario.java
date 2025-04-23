package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    /**
    * Nombre del usuario
    * */
    @Column(name = "nombre_usuario")
    @NotNull(message = "El nombre del usuario no puede ser nulo")
    private String nombreUsuario;

    /**
    * Contraseña asginada al usuario
    * */
    @Column(name="contraseña_usuario")
    @NotNull(message = "La contraseña del usuario no puede ser nula")
    private String contrasenia;

    /**
    * Relación entre Usuario y Rol
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", referencedColumnName = "idRol", nullable = false)
    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;

    /**
    * Constructor Vacío requerido por el JPA
    * */
    public Usuario() {}

    /**
     * Constructor con argumentos para facilitar la creación de instancias
     */
    public Usuario(String nombreUsuario, String contrasenia, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    // Métodos Getters and Setters

    /**
     * Obtiene el id del usuario.
     * @return idUsuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el id
     * @param idUsuario id del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario
     * @param nombreUsuario Nombre del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña
     * @return contraseña.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece la contraseña
     * @param contrasenia contraseña del usuario.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene el rol
     * @return rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol
     * @param rol rol del usuario.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
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
