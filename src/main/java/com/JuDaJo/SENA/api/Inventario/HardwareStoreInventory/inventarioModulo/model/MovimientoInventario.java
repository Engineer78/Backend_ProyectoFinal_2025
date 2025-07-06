package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un movimiento registrado en el sistema de inventario
 * como parte del proceso de trazabilidad.
 */
@Entity
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovimiento;

    /**
     * Fecha y hora exacta en la que ocurrió el movimiento.
     */
    private LocalDateTime fechaHoraMovimiento;

    /**
     * Tipo de movimiento: CREAR, ACTUALIZAR, ELIMINAR, CONSULTAR
     */
    @Convert(converter = TipoMovimientoConverter.class)
    @Column(name = "tipo_movimiento", nullable = false, columnDefinition = "varchar(255)")
    private TipoMovimiento tipoMovimiento;

    /**
     * ID de la entidad afectada (para saber qué registro exacto se modificó).
     */
    private String idEntidadAfectada;

    /**
     * Nombre de la entidad cuyo registro fue afectado durante un movimiento
     * en el sistema de inventario. Representa el tipo de objeto impactado,
     * como Producto, Proveedor, Empleado, entre otros.
     */
    private String entidadAfectada;

    /**
     * Nombre descriptivo de la entidad cuyo registro fue afectado durante un movimiento
     * en el sistema de inventario. Proporciona un contexto adicional para identificar
     * el objeto específico impactado, como un Producto, un Proveedor, o un Empleado.
     */
    private String nombreEntidadAfectada;

    /**
     * Descripción detallada de lo que ocurrió en este movimiento.
     */
    @Column(length = 1000) // por si el detalle es largo
    private String detalleMovimiento;

    /**
     * Empleado que realizó la acción.
     */
    @ManyToOne
    @JoinColumn(name = "id_empleado_responsable", nullable = false)
    private Empleado empleadoResponsable;

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public LocalDateTime getFechaHoraMovimiento() {
        return fechaHoraMovimiento;
    }

    public void setFechaHoraMovimiento(LocalDateTime fechaHoraMovimiento) {
        this.fechaHoraMovimiento = fechaHoraMovimiento;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setEntidadAfectada(String entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }

    public String getIdEntidadAfectada() {
        return idEntidadAfectada;
    }

    public void setIdEntidadAfectada(String idEntidadAfectada) {
        this.idEntidadAfectada = idEntidadAfectada;
    }

    public String getNombreEntidadAfectada() {
        return nombreEntidadAfectada;
    }

    public void setNombreEntidadAfectada(String nombreEntidadAfectada) {
        this.nombreEntidadAfectada = nombreEntidadAfectada;
    }

    public String getDetalleMovimiento() {
        return detalleMovimiento;
    }

    public void setDetalleMovimiento(String detalleMovimiento) {
        this.detalleMovimiento = detalleMovimiento;
    }

    public Empleado getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void setEmpleadoResponsable(Empleado empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }
}
