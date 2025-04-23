package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Rol.
 * Este repositorio proporciona operaciones CRUD básicas para la entidad Rol
 * utilizando Spring Data JPA.
 */
@Repository
public interface RolRepository  extends JpaRepository<Rol, Integer> {

    /**
     * Busca un rol por su ID.
     *
     * @param idRol El ID del rol.
     * @return Un Optional que contiene el rol encontrado, o vacío si no existe.
     */
    Optional<Rol> findByIdRol(Integer idRol);

    /**
     * Lista los roles que pertenecen a un perfil específico.
     *
     * @param idPerfil El ID del perfil.
     * @return Lista de roles que pertenecen al perfil.
     */
    List<Rol> findByPerfil_IdPerfil(Integer idPerfil);

    /**
     * Busca un rol por su ID.
     *
     * @param nombreRol El ID del rol.
     * @return Un Optional que contiene el rol encontrado, o vacío si no existe.
     */
    List<Rol> findByNombreContainingIgnoreCase(String nombreRol);
}
