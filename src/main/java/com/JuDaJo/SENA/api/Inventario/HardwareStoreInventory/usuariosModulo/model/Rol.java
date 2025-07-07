package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

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
    @Size(max = 20, message = "El nombre del rol no puede exceder los 20 caracteres")
    private String nombreRol;

    /**
     * Descripción del rol.
     */
    @Column(name = "descripcion")
    @Size(max = 255, message = "La descripción del rol no puede exceder los 255 caracteres")
    private String descripcion;

    /**
     * Perfil asociado a este rol.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil", nullable = false)
    private Perfil perfil;

    /**
     * Lista de permisos asociados a este rol.
     * Representa una relación uno a muchos con la entidad {@link RolPermiso},
     * donde cada instancia de {@link RolPermiso} especifica los permisos vinculados
     * al rol correspondiente.
     *
     * Esta relación es mantenida utilizando el mapeo bidireccional con la clase
     * {@link RolPermiso}, enlazándose a través del atributo "rol" de dicha entidad.
     *
     * FetchType.EAGER asegura que los permisos asociados se carguen automáticamente
     * junto con el rol, mientras que CascadeType.ALL propaga todas las operaciones
     * persistenciales desde el rol hacia sus permisos asociados.
     */
    @OneToMany(mappedBy = "rol", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RolPermiso> rolPermisos;

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

    /**
     * Obtiene la lista de permisos asociados a este rol.
     *
     * @return Lista de objetos RolPermiso asociados al rol. Si no hay permisos asociados, retorna una lista vacía.
     */
    public List<RolPermiso> getRolPermisos() {
        return rolPermisos;
    }

    /**
     * Establece la lista de permisos asociados al rol.
     *
     * @param rolPermisos Lista de objetos RolPermiso que representan los permisos
     *                    que serán asociados a este rol. Puede ser una lista vacía
     *                    si no hay permisos asociados.
     */
    public void setRolPermisos(List<RolPermiso> rolPermisos) {
        this.rolPermisos = rolPermisos;
    }
}
