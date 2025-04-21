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
    @JoinColumn(name = "id_rol", referencedColumnName = "id_perfil", nullable = false)
    private Perfil perfil;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Rol() {
    }

}
