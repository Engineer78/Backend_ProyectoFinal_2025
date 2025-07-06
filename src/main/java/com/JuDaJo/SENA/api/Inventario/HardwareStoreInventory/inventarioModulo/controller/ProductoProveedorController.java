package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.ProductoProveedorDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.ProductoProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las relaciones producto-proveedor.
 * Delegación total al servicio ProductoProveedorService.
 */
@RestController
@RequestMapping("/api/producto-proveedor")
public class ProductoProveedorController {

    private final ProductoProveedorService productoProveedorService;

    public ProductoProveedorController(ProductoProveedorService productoProveedorService) {
        this.productoProveedorService = productoProveedorService;
    }

    /**
     * Lista todas las relaciones producto-proveedor.
     */
    @GetMapping
    public ResponseEntity<List<ProductoProveedorDTO>> listarProductoProveedor() {
        return ResponseEntity.ok(productoProveedorService.listarTodos());
    }

    /**
     * Crea una nueva relación producto-proveedor.
     */
    @PostMapping
    public ResponseEntity<ProductoProveedorDTO> crearProductoProveedor(@Valid @RequestBody ProductoProveedorDTO dto) {
        return productoProveedorService.crearRelacion(dto);
    }

    /**
     * Actualiza una relación existente por ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoProveedorDTO> actualizarProductoProveedor(@PathVariable int id, @Valid @RequestBody ProductoProveedorDTO dto) {
        return productoProveedorService.actualizarRelacion(id, dto);
    }

    /**
     * Elimina una relación producto-proveedor por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProductoProveedor(@PathVariable int id) {
        return productoProveedorService.eliminarRelacion(id);
    }
}

