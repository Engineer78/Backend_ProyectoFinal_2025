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

}
