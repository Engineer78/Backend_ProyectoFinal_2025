package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Permiso.
 */
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    /**
     * Busca un permiso por su nombre exacto.
     *
     * @param nombrePermiso Nombre del permiso.
     * @return Un Optional con el permiso si existe.
     */
    Optional<Permiso> findByNombrePermiso(String nombrePermiso);

    /**
     * Realiza una búsqueda de permisos cuyo nombre contenga el texto especificado,
     * ignorando mayúsculas, minúsculas y espacios en blanco alrededor del nombre.
     *
     * @param nombre El texto a buscar en el nombre de los permisos.
     * @return Una lista de permisos cuyos nombres coincidan parcialmente con el texto dado.
     */
    @Query("SELECT p FROM Permiso p WHERE LOWER(TRIM(p.nombrePermiso)) LIKE LOWER(CONCAT('%', TRIM(:nombre), '%'))")
    List<Permiso> buscarPorNombreParcial(@Param("nombre") String nombre);

}

