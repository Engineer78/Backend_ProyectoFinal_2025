package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<Rol> findByNombreRolContainingIgnoreCase(String nombreRol);

    /**
     * Realiza una búsqueda flexible de roles cuyo nombre contenga el texto especificado,
     * ignorando mayúsculas, minúsculas y espacios en blanco alrededor del nombre.
     *
     * @param nombre El texto a buscar en el nombre de los roles.
     * @return Una lista de roles cuyos nombres coincidan parcialmente con el texto dado.
     */
    @Query("SELECT r FROM Rol r WHERE LOWER(TRIM(r.nombreRol)) LIKE LOWER(CONCAT('%', TRIM(:nombre), '%'))")
    List<Rol> buscarPorNombreFlexible(@Param("nombre") String nombre);
}
