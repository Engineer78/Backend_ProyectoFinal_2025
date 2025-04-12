package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Proveedor.
 * Este repositorio proporciona operaciones CRUD básicas para la entidad Proveedor
 * utilizando Spring Data JPA.
 */

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}
