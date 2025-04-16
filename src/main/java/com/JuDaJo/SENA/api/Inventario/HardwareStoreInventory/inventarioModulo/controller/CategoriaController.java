package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.CategoriaDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Categoria;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde tu frontend
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> categoriaDTOs = categorias.stream()
                .map(categoria -> new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOs);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable Integer id) {
        return categoriaRepository.findById(id)
                .map(categoria -> new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para buscar una categoría por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDTO> getCategoriaPorNombre(@PathVariable String nombre) {
        Optional<Categoria> categoria = categoriaRepository.findByNombreCategoria(nombre);

        // Convertir la entidad Categoria a CategoriaDTO si se encuentra
        return categoria.map(cat -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO(cat.getIdCategoria(), cat.getNombreCategoria());
            return ResponseEntity.ok(categoriaDTO); // Devuelve el DTO
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Si no existe, 404
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<?> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            // Mapea el DTO a la entidad
            Categoria categoria = new Categoria();
            categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());

            // Guarda la categoría en el repositorio
            Categoria nuevaCategoria = categoriaRepository.save(categoria);

            // Crea un nuevo DTO para devolver la respuesta
            CategoriaDTO nuevaCategoriaDTO = new CategoriaDTO(nuevaCategoria.getIdCategoria(), nuevaCategoria.getNombreCategoria());

            // Retorna la respuesta con HTTP 201
            return new ResponseEntity<>(nuevaCategoriaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            // Maneja excepciones y retornamos error
            return new ResponseEntity<>("Error al crear la categoría: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
                    categoriaRepository.save(categoria);
                    return ResponseEntity.ok(new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
