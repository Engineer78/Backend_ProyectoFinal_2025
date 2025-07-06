package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import java.time.LocalDateTime;

/**
 * DTO que representa un movimiento registrado en el sistema.
 */
public class MovimientoInventarioDTO {

    private Integer idMovimiento;
    private LocalDateTime fechaHoraMovimiento;
    private TipoMovimiento tipoMovimiento;
    private String entidadAfectada;
    private String idEntidadAfectada;
    private String nombreEntidadAfectada;
    private String detalleMovimiento;
    // Datos del empleado responsable (opcional: nombre completo y/o ID)
    private Integer idEmpleadoResponsable;
    private String nombreEmpleadoResponsable;

    /**
     * Constructor por defecto para la clase MovimientoInventarioDTO.
     * Inicializa una nueva instancia de esta clase sin establecer valores
     * iniciales para sus atributos. Este constructor es útil para la creación
     * de objetos que serán posteriormente configurados a través de sus métodos
     * de acceso (setters).
     */
    public MovimientoInventarioDTO() {
    }

    /**
     * Constructor de la clase MovimientoInventarioDTO.
     * Permite inicializar una nueva instancia con los valores especificados.
     *
     * @param idMovimiento Identificador único del movimiento de inventario.
     * @param fechaHoraMovimiento Fecha y hora en la que se realizó el movimiento.
     * @param tipoMovimiento Tipo del movimiento realizado, especificado con el enum TipoMovimiento.
     * @param entidadAfectada Nombre de la entidad afectada por el movimiento.
     * @param idEntidadAfectada Identificador de la entidad afectada.
     * @param nombreEntidadAfectada Nombre descriptivo de la entidad afectada.
     * @param detalleMovimiento Descripción detallada del movimiento realizado.
     * @param idEmpleadoResponsable Identificador del empleado que realizó o es responsable del movimiento.
     * @param nombreEmpleadoResponsable Nombre del empleado responsable del movimiento.
     */
    public MovimientoInventarioDTO(Integer idMovimiento, LocalDateTime fechaHoraMovimiento,
                                   TipoMovimiento tipoMovimiento, String entidadAfectada,
                                   String idEntidadAfectada, String nombreEntidadAfectada,
                                   String detalleMovimiento, Integer idEmpleadoResponsable,
                                   String nombreEmpleadoResponsable) {
        this.idMovimiento = idMovimiento;
        this.fechaHoraMovimiento = fechaHoraMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.entidadAfectada = entidadAfectada;
        this.nombreEntidadAfectada = nombreEntidadAfectada;
        this.detalleMovimiento = detalleMovimiento;
        this.idEntidadAfectada = idEntidadAfectada;
        this.idEmpleadoResponsable = idEmpleadoResponsable;
        this.nombreEmpleadoResponsable = nombreEmpleadoResponsable;
    }

    // Getters y Setters
    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
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

    public int getIdEmpleadoResponsable() {
        return idEmpleadoResponsable;
    }

    public void setIdEmpleadoResponsable(int idEmpleadoResponsable) {
        this.idEmpleadoResponsable = idEmpleadoResponsable;
    }

    public String getNombreEmpleadoResponsable() {
        return nombreEmpleadoResponsable;
    }

    public void setNombreEmpleadoResponsable(String nombreEmpleadoResponsable) {
        this.nombreEmpleadoResponsable = nombreEmpleadoResponsable;
    }
}
