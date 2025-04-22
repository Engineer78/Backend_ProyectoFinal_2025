package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Perfil.
 * Este repositorio proporciona operaciones CRUD básicas para la entidad Perfil
 * utilizando Spring Data JPA.
 */
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    /**
     * Busca un perfil por su ID.
     *
     * @param idPerfil El ID del perfil.
     * @return Un Optional que contiene el perfil encontrado, o un Optional vacío si no se encuentra.
     */
    Optional<Perfil> findByIdPerfil(Integer idPerfil);

    /**
     * Busca un perfil por su nombre.
     *
     * @param nombrePerfil El nombre del perfil.
     * @return Un Optional que contiene el perfil encontrado, o un Optional vacío si no se encuentra.
     */
    Optional<Perfil> findByNombrePerfil(String nombrePerfil);
}
