package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tipo_documento")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;

    @NotBlank(message = "El código del tipo de documento no puede estar en blanco")
    @Size(max = 5, message = "El código no debe tener más de 5 caracteres")
    @Column(length = 5, nullable = false)
    private String codigo;

    @NotBlank(message = "El nombre del tipo de documento no puede estar en blanco")
    @Size(max = 50, message = "El nombre no debe tener más de 50 caracteres")
    @Column(length = 50, nullable = false)
    private String nombre;

    /**
     * Se crea la relación bidireccional entre el tipo de documento y los empleados que tienen dicho tipo de documento.
     */
    @OneToMany(mappedBy = "tipoDocumento", fetch = FetchType.LAZY)
    private List<Empleado> empleados;

}
