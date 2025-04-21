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

    @NotBlank(message = "El nombre del perfil no puede estar en blanco")
    @Size(max = 15, message = "El nombre del perfil no puede exceder los 15 caracteres")
    private String nombrePerfil;

    @NotBlank(message = "La descripción del perfil no puede estar en blanco")
    @Size(max = 150, message = "La descripción del perfil no puede exceder los 150 caracteres")
    private String descripcion;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rol> roles = new ArrayList<>();

    public void agregarRol(Rol rol) {
        roles.add(rol);
        rol.setPerfil(this);
    }

    public void eliminarRol(Rol rol) {
        roles.remove(rol);
        rol.setPerfil(null);
    }
}
