package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Interfaz que define los servicios disponibles para gestionar productos.
 * Contiene métodos para crear, actualizar, consultar, buscar y eliminar productos del inventario.
 */
public interface ProductoService {

    /**
     * Crea un nuevo producto en el sistema con proveedor y categoría asociados.
     *
     * @param dto DTO con los datos del producto a crear.
     * @return ResponseEntity con el DTO del producto creado o mensaje de error.
     */
    ResponseEntity<?> crearProducto(ProductoCreationDTO dto);

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param idProducto ID del producto a actualizar.
     * @param dto DTO con los nuevos datos del producto.
     * @return ResponseEntity con el DTO del producto actualizado o mensaje de error.
     */
    ResponseEntity<?> actualizarProducto(Integer idProducto, ProductoUpdateDTO dto);

    /**
     * Obtiene la lista completa de productos del inventario.
     *
     * @return Lista de ProductoDTO.
     */
    List<ProductoDTO> listarTodos();

    /**
     * Busca un producto por su ID.
     *
     * @param idProducto ID del producto.
     * @return ProductoDTO si se encuentra, o null.
     */
    ProductoDTO buscarPorId(Integer idProducto);

    /**
     * Busca un producto por su código.
     *
     * @param codigoProducto Código del producto.
     * @return ProductoDTO si se encuentra, o null.
     */
    ProductoDTO buscarPorCodigo(Integer codigoProducto);

    /**
     * Busca un producto por su nombre exacto.
     *
     * @param nombreProducto Nombre del producto.
     * @return ProductoConsultaDTO si se encuentra, o null.
     */
    ProductoConsultaDTO buscarPorNombre(String nombreProducto);

    /**
     * Realiza una búsqueda filtrada de productos con múltiples parámetros.
     *
     * @param codigoProducto Código del producto.
     * @param nombreProducto Nombre del producto.
     * @param nombreCategoria Nombre de la categoría.
     * @param nitProveedor NIT del proveedor.
     * @param nombreProveedor Nombre del proveedor.
     * @param cantidad Cantidad disponible.
     * @param valorUnitarioProducto Valor unitario.
     * @param valorTotalProducto Valor total.
     * @return Lista de ProductoDTO que cumplen con los filtros.
     */
    List<ProductoDTO> buscarProductos(Integer codigoProducto, String nombreProducto, String nombreCategoria,
                                      String nitProveedor, String nombreProveedor, Integer cantidad,
                                      Double valorUnitarioProducto, Double valorTotalProducto,
                                      boolean registrar);

    /**
     * Realiza una búsqueda avanzada desde el modal.
     *
     * @param nitProveedor NIT del proveedor.
     * @param nombreProveedor Nombre del proveedor.
     * @param cantidad Cantidad del producto.
     * @param valorUnitarioProducto Valor unitario.
     * @param valorTotalProducto Valor total.
     * @return Lista de productos filtrados.
     */
    List<ProductoDTO> buscarProductosAvanzados(String nitProveedor, String nombreProveedor, String cantidad,
                                               String valorUnitarioProducto, String valorTotalProducto,
                                               boolean registrar);

    /**
     * Elimina un producto del inventario junto con sus relaciones proveedor.
     *
     * @param idProducto ID del producto a eliminar.
     * @return ResponseEntity sin contenido si se elimina correctamente, o error si falla.
     */
    ResponseEntity<Void> eliminarProducto(Integer idProducto);
}