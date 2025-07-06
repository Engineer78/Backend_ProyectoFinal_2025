package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 * Repositorio para la entidad Categoria.
 * * Este repositorio proporciona operaciones CRUD básicas para la entidad Categoria
 * utilizando Spring Data JPA.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    /**
     * Busca una entidad Categoria en función del nombre de la categoría proporcionado.
     *
     * @param nombreCategoria el nombre de la categoría que se desea buscar.
     * @return un objeto Optional que contiene la entidad Categoria si se encuentra,
     *         o está vacío si no existe ninguna coincidencia.
     */
    @Query("SELECT c FROM Categoria c WHERE c.nombreCategoria = :nombre")
    Optional<Categoria> findByNombreCategoria(@Param("nombre") String nombreCategoria);
}
