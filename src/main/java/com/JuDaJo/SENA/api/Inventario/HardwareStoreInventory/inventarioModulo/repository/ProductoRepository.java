package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Producto que extiende JpaRepository.
 * Proporciona métodos de consulta personalizados y funcionalidad estándar
 * para la interacción con la base de datos relacionada con productos.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Busca un producto por su código exacto.
     */
    Optional<Producto> findByCodigoProducto(Integer codigoProducto);

    /**
     * Busca un producto por su nombre exacto.
     */
    Optional<Producto> findByNombreProducto(String nombreProducto);

    /**
     * Filtros principales de búsqueda desde tabla (búsqueda básica).
     */
    @Query("SELECT p FROM Producto p " +
            "LEFT JOIN p.proveedor prov " +
            "WHERE (:codigoProducto IS NULL OR p.codigoProducto = :codigoProducto) " +
            "AND (:nombreProducto IS NULL OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :nombreProducto, '%'))) " +
            "AND (:nombreCategoria IS NULL OR LOWER(p.categoria.nombreCategoria) LIKE LOWER(CONCAT('%', :nombreCategoria, '%'))) " +
            "AND (:nitProveedor IS NULL OR prov.nitProveedor = :nitProveedor) " +
            "AND (:nombreProveedor IS NULL OR LOWER(prov.nombreProveedor) LIKE LOWER(CONCAT('%', :nombreProveedor, '%'))) " +
            "AND (:cantidad IS NULL OR p.cantidad = :cantidad) " +
            "AND (:valorUnitarioProducto IS NULL OR p.valorUnitarioProducto = :valorUnitarioProducto) " +
            "AND (:valorTotalProducto IS NULL OR p.valorTotalProducto = :valorTotalProducto)")
    List<Producto> buscarProductosConFiltros(
            @Param("codigoProducto") Integer codigoProducto,
            @Param("nombreProducto") String nombreProducto,
            @Param("nombreCategoria") String nombreCategoria,
            @Param("nitProveedor") String nitProveedor,
            @Param("nombreProveedor") String nombreProveedor,
            @Param("cantidad") Integer cantidad,
            @Param("valorUnitarioProducto") Double valorUnitarioProducto,
            @Param("valorTotalProducto") Double valorTotalProducto
    );

    /**
     * Consulta optimizada para el modal de búsqueda avanzada (campos string).
     */
    @Query("SELECT p FROM Producto p " +
            "LEFT JOIN p.proveedor prov " +
            "WHERE (:nitProveedor IS NULL OR prov.nitProveedor LIKE %:nitProveedor%) " +
            "AND (:nombreProveedor IS NULL OR LOWER(prov.nombreProveedor) LIKE LOWER(CONCAT('%', :nombreProveedor, '%'))) " +
            "AND (:cantidad IS NULL OR str(p.cantidad) LIKE %:cantidad%) " +
            "AND (:valorUnitarioProducto IS NULL OR str(p.valorUnitarioProducto) LIKE %:valorUnitarioProducto%) " +
            "AND (:valorTotalProducto IS NULL OR str(p.valorTotalProducto) LIKE %:valorTotalProducto%)")
    List<Producto> buscarProductosAvanzados(
            @Param("nitProveedor") String nitProveedor,
            @Param("nombreProveedor") String nombreProveedor,
            @Param("cantidad") String cantidad,
            @Param("valorUnitarioProducto") String valorUnitarioProducto,
            @Param("valorTotalProducto") String valorTotalProducto
    );

    /**
     * Consulta que obtiene el proveedor junto con el producto por ID.
     */
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.proveedor WHERE p.idProducto = :idProducto")
    Optional<Producto> findByIdWithProveedor(@Param("idProducto") Integer idProducto);

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.proveedor WHERE p.codigoProducto = :codigoProducto")
    Optional<Producto> findByCodigoWithProveedor(@Param("codigoProducto") Integer codigoProducto);

}