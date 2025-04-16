package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer> {
    // Ajustando los nombres según Producto.idProducto
    Optional<ProductoProveedor>
    findByProductoIdProductoAndProveedorIdProveedor(Integer idProducto, Integer idProveedor);
}
