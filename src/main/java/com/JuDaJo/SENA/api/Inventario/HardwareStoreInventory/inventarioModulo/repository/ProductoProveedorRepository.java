package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de acceso a datos de la entidad ProductoProveedor.
 * Permite realizar operaciones CRUD y consultas específicas sobre la relación entre productos
 * y proveedores en la base de datos.
 */
@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer> {

    // Ajusta los nombres según Producto.idProducto
    Optional<ProductoProveedor>
    findByProductoIdProductoAndProveedorIdProveedor(Integer idProducto, Integer idProveedor);
}
