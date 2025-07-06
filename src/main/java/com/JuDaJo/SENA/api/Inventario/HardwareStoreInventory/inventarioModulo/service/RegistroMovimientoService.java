package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;

/**
 * Servicio para gestionar el registro de movimientos en el inventario.
 * Este servicio permite registrar cambios o movimientos realizados en el inventario
 * asociándolos con información específica como el tipo de movimiento, la entidad afectada
 * y el empleado responsable.
 */
public interface RegistroMovimientoService {

    /**
     * Registra un movimiento en el inventario. Este método es utilizado para almacenar
     * la información de un cambio relacionado con los productos o entidades del inventario,
     * especificando los detalles del movimiento, el tipo de operación realizada y la información
     * del responsable que efectuó dicho movimiento.
     *
     * @param dto Objeto de tipo MovimientoInventarioDTO que contiene los datos del movimiento
     *            a registrar, como el tipo de movimiento, entidad afectada, detalles del movimiento
     *            y datos del empleado responsable.
     */
    void registrarMovimiento(MovimientoInventarioDTO dto);
}
