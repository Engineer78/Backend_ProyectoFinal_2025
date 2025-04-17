package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Proveedor;
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
    // No se necesitan métodos personalizados por ahora, ya que JpaRepository
    // proporciona los métodos básicos (save, findById, findAll, delete, etc.).

    /**
     * Busca un proveedor por su NIT.
     *
     * @param nitProveedor El NIT del proveedor.
     * @return Un Optional que contiene el proveedor encontrado, o un Optional vacío si no se encuentra.
     */
    Optional<Proveedor> findByNitProveedor(String nitProveedor);
    Optional<Proveedor> findByNombreProveedor(String nombreProveedor);
}
