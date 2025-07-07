package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Clase que representa el tipo de documento en el sistema.
 * Está mapeada a la tabla "tipo_documento" en la base de datos.
 *
 * La clase incluye información sobre el tipo de documento, como su código y nombre,
 * y define una relación bidireccional con la entidad Empleado.
 */
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

    /**
     * Se crean los getters y setters para los atributos de la clase.
     * @return
     */
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
