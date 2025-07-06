package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad TipoDocumento.
 * Este repositorio proporciona operaciones CRUD básicas para la entidad TipoDocumento
 * utilizando Spring Data JPA.
 */
@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
}
