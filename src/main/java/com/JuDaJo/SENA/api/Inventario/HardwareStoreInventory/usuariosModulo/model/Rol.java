package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un rol dentro del sistema.
 */
@Entity
public class Rol {

    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    /**
     * Nombre del rol.
     */
    @Column(name = "nombre_rol")
    @NotBlank(message = "El nombre del rol no puede estar en blanco")
    @Size(max = 45, message = "El nombre del rol no puede exceder los 45 caracteres")
    private String nombreRol;

    /**
     * Descripción del rol.
     */
    @Column(name = "descripcion")
    @Size(max = 150, message = "La descripción del rol no puede exceder los 150 caracteres")
    private String descripcion;

    /**
     * Perfil asociado a este rol.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Rol() {
    }

    /**
     * Constructor con argumentos para la clase rol.
     */
    public Rol(String nombreRol, String descripcion, Perfil perfil) {
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.perfil = perfil;
    }

    /**
     * Getter para el identificador del rol.
     * 
     * @return Identificador del rol.
     */
    public Integer getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * Setter para el identificador del rol.
     * 
     * @param idRol Identificador del rol.
     */
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
