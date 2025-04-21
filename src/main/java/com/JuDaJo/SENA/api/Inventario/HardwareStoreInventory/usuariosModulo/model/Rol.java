package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un rol dentro del sistema.
 */
@Getter
@Setter
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
    @NotBlank(message = "El nombre del rol no puede estar en blanco")
    @Size(max = 45, message = "El nombre del rol no puede exceder los 45 caracteres")
    private String nombreRol;

}
