package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Rol.
 * Este repositorio proporciona operaciones CRUD básicas para la entidad Rol
 * utilizando Spring Data JPA.
 */
@Repository
public interface RolRepository  extends JpaRepository<Rol, Integer> {
}
