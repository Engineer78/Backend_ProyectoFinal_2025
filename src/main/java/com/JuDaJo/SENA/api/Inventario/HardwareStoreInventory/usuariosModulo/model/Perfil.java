package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un perfil dentro de usuarios.
 */
@Entity
public class Perfil {

    /**
     * Identificador único del perfil.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPerfil;

    /**
     * Nombre del perfil.
     */
    @NotBlank(message = "El nombre del perfil no puede estar en blanco")
    @Size(max = 15, message = "El nombre del perfil no puede exceder los 15 caracteres")
    private String nombrePerfil;

    /**
     * Descripción del perfil.
     */
    @NotBlank(message = "La descripción del perfil no puede estar en blanco")
    @Size(max = 150, message = "La descripción del perfil no puede exceder los 150 caracteres")
    private String descripcion;


}
