package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "permiso")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    @Column(name = "nombre_permiso", nullable = false, length = 100)
    private String nombrePermiso;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "permiso", cascade = CascadeType.ALL)
    private List<RolPermiso> roles;

    /**
     * Constructor por defecto de la clase Permiso.
     *
     * Este constructor es requerido por JPA y permite la creación de instancias
     * de la entidad Permiso sin inicializar sus atributos. Es útil en contextos
     * donde se requiere instanciar objetos de Permiso sin valores predeterminados
     * o para propósitos de deserialización.
     */
    public Permiso() {
    }

    /**
     * Constructor de la clase Permiso que permite inicializar sus atributos principales.
     *
     * @param idPermiso Identificador único del permiso.
     * @param nombrePermiso Nombre descriptivo del permiso.
     * @param descripcion Breve descripción asociada al permiso.
     */
    public Permiso(Integer idPermiso, String nombrePermiso, String descripcion) {
        this.idPermiso = idPermiso;
        this.nombrePermiso = nombrePermiso;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombrePermiso() {
        return nombrePermiso;
    }

    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<RolPermiso> getRoles() {
        return roles;
    }

    public void setRoles(List<RolPermiso> roles) {
        this.roles = roles;
    }
}







