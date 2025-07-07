package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.RolPermiso;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad {@link RolPermiso}.
 * Este repositorio proporciona operaciones básicas de persistencia relacionadas con
 * la relación entre roles y permisos dentro del sistema.
 */
@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Integer> {

    /**
     * Obtiene una lista de entidades {@code RolPermiso} asociadas a un rol específico.
     *
     * @param idRol El identificador único del rol.
     * @return Una lista de objetos {@code RolPermiso} asociados al rol especificado.
     */
    List<RolPermiso> findByRol_IdRol(Integer idRol);

    /**
     * Devuelve una lista de entidades {@code RolPermiso} asociadas a un permiso específico.
     *
     * @param idPermiso El identificador único del permiso.
     * @return Una lista de objetos {@code RolPermiso} relacionados con el permiso especificado.
     */
    List<RolPermiso> findByPermisoIdPermiso(Integer idPermiso);

    /**
     * Verifica si existe una asociación entre un rol específico y un permiso.
     *
     * @param idRol El identificador único del rol.
     * @param idPermiso El identificador único del permiso.
     * @return {@code true} si existe la asociación especificada, {@code false} en caso contrario.
     */
    boolean existsByRolIdRolAndPermisoIdPermiso(Integer idRol, Integer idPermiso);

    @Transactional
    @Modifying
    @Query("DELETE FROM RolPermiso rp WHERE rp.rol.idRol = :idRol")
    void deleteByRolId(@Param("idRol") Integer idRol);

}