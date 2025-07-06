package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.ProductoProveedorDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Interfaz de servicio para gestionar las relaciones producto-proveedor.
 */
public interface ProductoProveedorService {

    /**
     * Lista todas las relaciones producto-proveedor.
     * @return Lista de ProductoProveedorDTO
     */
    List<ProductoProveedorDTO> listarTodos();

    /**
     * Crea una nueva relación producto-proveedor.
     * @param dto Datos de la relación
     * @return Relación creada
     */
    ResponseEntity<ProductoProveedorDTO> crearRelacion(ProductoProveedorDTO dto);

    /**
     * Actualiza una relación existente.
     * @param id ID de la relación
     * @param dto Nuevos datos
     * @return Relación actualizada
     */
    ResponseEntity<ProductoProveedorDTO> actualizarRelacion(int id, ProductoProveedorDTO dto);

    /**
     * Elimina una relación producto-proveedor por su ID.
     * @param id ID de la relación
     * @return Estado de la operación
     */
    ResponseEntity<Void> eliminarRelacion(int id);
}
