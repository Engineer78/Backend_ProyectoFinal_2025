package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.CategoriaDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.CategoriaService;
import java.util.List;

/**
 * Controlador REST para gestionar las categorías.
 * Delega toda la lógica en el servicio CategoriaService.
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Obtener todas las categorías.
     */
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    /**
     * Obtener una categoría por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable Integer id) {
        return categoriaService.obtenerCategoria(id);
    }

    /**
     * Buscar categoría por nombre exacto.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDTO> buscarPorNombre(@PathVariable String nombre) {
        return categoriaService.buscarPorNombre(nombre);
    }

    /**
     * Crear nueva categoría.
     */
    @PostMapping
    public ResponseEntity<?> crearCategoria(@Valid @RequestBody CategoriaDTO dto) {
        return categoriaService.crearCategoria(dto);
    }

    /**
     * Actualizar categoría existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO dto) {
        return categoriaService.actualizarCategoria(id, dto);
    }

    /**
     * Eliminar una categoría por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        return categoriaService.eliminarCategoria(id);
    }
}
