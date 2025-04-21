package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un perfil dentro de usuarios.
 */
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @Column(name = "nombre_perfil")
    @NotBlank(message = "El nombre del perfil no puede estar en blanco")
    @Size(max = 15, message = "El nombre del perfil no puede exceder los 15 caracteres")
    private String nombrePerfil;

    @Column(name = "descripcion")
    @NotBlank(message = "La descripción del perfil no puede estar en blanco")
    @Size(max = 150, message = "La descripción del perfil no puede exceder los 150 caracteres")
    private String descripcion;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rol> roles = new ArrayList<>();

    /**
     * Constructor vacío requerido por JPA.
     */
    public Perfil() {
    }

    /**
     * Constructor con parametros para inicializar un perfil.
     */
    public Perfil(String nombrePerfil, String descripcion) {
        this.nombrePerfil = nombrePerfil;
        this.descripcion = descripcion;
    }

    /**
     * Getter para el identificador del perfil.
     *
     * @return Identificador del perfil.
     */
    public Integer getIdPerfil() {
        return idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * Setter para el identificador del perfil.
     *
     * @param idPerfil Identificador del perfil.
     */
    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public void agregarRol(Rol rol) {
        roles.add(rol);
        rol.setPerfil(this);
    }

    public void eliminarRol(Rol rol) {
        roles.remove(rol);
        rol.setPerfil(null);
    }
}
