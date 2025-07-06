package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import java.util.List;

/**
 * Servicio que define las operaciones principales relacionadas con los movimientos
 * de inventario en el sistema. Permite listar los movimientos de inventario, filtrar
 * por entidad afectada, y registrar nuevos movimientos con los detalles proporcionados.
 */
public interface MovimientoInventarioService {

    /**
     * Obtiene una lista completa de los movimientos de inventario registrados en el sistema.
     *
     * @return Lista de objetos MovimientoInventarioDTO que representan los movimientos de inventario.
     */
    List<MovimientoInventarioDTO> listarMovimientos();

    /**
     * Obtiene una lista de movimientos de inventario que están asociados con una entidad específica.
     *
     * @param entidadAfectada Nombre de la entidad que se desea filtrar en los movimientos de inventario.
     * @return Lista de objetos MovimientoInventarioDTO que representan los movimientos asociados con la entidad especificada.
     */
    List<MovimientoInventarioDTO> listarPorEntidad(String entidadAfectada);

    /**
     * Registra un nuevo movimiento en el sistema de inventario.
     *
     * @param token Token de autenticación del usuario que está registrando el movimiento.
     * @param entidadAfectada Nombre de la entidad que se ve afectada por el movimiento.
     * @param tipoMovimiento Tipo del movimiento que se está registrando (por ejemplo, entrada, salida).
     * @param idEntidadAfectada Identificador único de la entidad afectada por el movimiento.
     * @param detalle Detalle específico del movimiento registrado, que puede contener información adicional relevante.
     */
    void registrarMovimiento(String token, String entidadAfectada, String tipoMovimiento, String idEntidadAfectada, String detalle);

}