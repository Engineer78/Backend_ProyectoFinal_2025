package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad MovimientoInventario.
 * Extiende la interface JpaRepository para proporcionar métodos CRUD
 * y personalizables para la manipulación de datos relacionados con
 * los movimientos de inventario.
 *
 * Permite realizar operaciones básicas como guardar, actualizar, buscar
 * y eliminar registros de movimientos en el sistema de inventario, así
 * como implementar métodos adicionales específicos según los requisitos
 * de negocio.
 *
 * MovimientoInventario representa un registro de acciones llevadas a cabo
 * en diferentes entidades dentro del inventario.
 *
 * @see MovimientoInventario
 * @see JpaRepository
 */
@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer> {
    // Agregar métodos personalizados si se necesita buscar por tipo, entidad, etc.
}