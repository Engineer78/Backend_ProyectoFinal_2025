package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Producto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository {
    /**
     * Busca un producto por su código exacto.
     *
     * @param codigoProducto El código del producto.
     * @return Un Optional que contiene el producto encontrado, o un Optional vacío si no se encuentra.
     */
    Optional<Producto> findByCodigoProducto(Integer codigoProducto);
    /**
     * Busca un producto por su nombre exacto.
     *
     * @param nombreProducto El nombre del producto.
     * @return Un Optional que contiene el producto encontrado, o un Optional vacío si no se encuentra.
     */
    Optional<Producto> findByNombreProducto(String nombreProducto);
    /**
     * Busca productos utilizando filtros opcionales.
     * Permite filtrar por código, categoría, nombre y cantidad.
     *
     * @param codigoProducto El código del producto (puede ser null).
     * @param nombreCategoria El nombre de la categoría (puede ser null).
     * @param nombreProducto Parte del nombre del producto (puede ser null).
     * @param cantidad Cantidad mínima del producto (puede ser null).
     * @return Una lista de productos que cumplen con los criterios de filtro.
     */
    @Query("SELECT p FROM Producto p " +
            "LEFT JOIN p.productoProveedores pp " +
            "LEFT JOIN pp.proveedor prov " +
            "WHERE (:codigoProducto IS NULL OR p.codigoProducto = :codigoProducto) " +
            "AND (:nombreProducto IS NULL OR LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%',:nombreProducto,'%'))) " +
            "AND (:nombreCategoria IS NULL OR LOWER(p.categoria.nombreCategoria) LIKE LOWER(CONCAT('%',:nombreCategoria,'%'))) " +
            "AND (:nitProveedor IS NULL OR prov.nitProveedor = :nitProveedor) " +
            "AND (:nombreProveedor IS NULL OR LOWER(prov.nombreProveedor) LIKE LOWER(CONCAT('%',:nombreProveedor,'%'))) " +
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

}
