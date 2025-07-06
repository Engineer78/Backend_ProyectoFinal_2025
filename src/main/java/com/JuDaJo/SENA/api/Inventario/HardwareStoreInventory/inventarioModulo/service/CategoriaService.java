package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.CategoriaDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Interfaz de servicio para operaciones relacionadas con categorías.
 */
public interface CategoriaService {

    /**
     * Obtiene la lista de todas las categorías disponibles.
     *
     * @return Lista de CategoriaDTO
     */
    List<CategoriaDTO> listarCategorias();

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id ID de la categoría
     * @return ResponseEntity con CategoriaDTO o 404 si no existe
     */
    ResponseEntity<CategoriaDTO> obtenerCategoria(Integer id);

    /**
     * Busca una categoría por su nombre exacto.
     *
     * @param nombre Nombre de la categoría
     * @return ResponseEntity con CategoriaDTO o 404 si no existe
     */
    ResponseEntity<CategoriaDTO> buscarPorNombre(String nombre);

    /**
     * Crea una nueva categoría en el sistema.
     *
     * @param dto DTO con los datos de la nueva categoría
     * @return ResponseEntity con la categoría creada o error
     */
    ResponseEntity<?> crearCategoria(CategoriaDTO dto);

    /**
     * Actualiza una categoría existente.
     *
     * @param id ID de la categoría a actualizar
     * @param dto DTO con los nuevos datos
     * @return ResponseEntity con la categoría actualizada o 404 si no existe
     */
    ResponseEntity<CategoriaDTO> actualizarCategoria(Integer id, CategoriaDTO dto);

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría
     * @return ResponseEntity con estado de la operación
     */
    ResponseEntity<Void> eliminarCategoria(Integer id);
}


