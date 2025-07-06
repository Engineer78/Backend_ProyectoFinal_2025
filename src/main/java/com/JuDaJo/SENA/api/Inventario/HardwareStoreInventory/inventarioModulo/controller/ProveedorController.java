package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.ProveedorDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar proveedores.
 * Toda la lógica es delegada al servicio ProveedorService.
 */
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    /**
     * Obtener todos los proveedores.
     */
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> listarProveedores() {
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    /**
     * Obtener un proveedor por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtenerProveedor(@PathVariable int id) {
        return proveedorService.obtenerProveedor(id);
    }

    /**
     * Buscar proveedor por NIT.
     */
    @GetMapping("/nit/{nitProveedor}")
    public ResponseEntity<ProveedorDTO> buscarPorNit(@PathVariable String nitProveedor) {
        return proveedorService.buscarPorNit(nitProveedor);
    }

    /**
     * Buscar proveedor por nombre.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProveedorDTO> buscarPorNombre(@PathVariable String nombre) {
        return proveedorService.buscarPorNombre(nombre);
    }

    /**
     * Crear un nuevo proveedor.
     */
    @PostMapping
    public ResponseEntity<Object> crearProveedor(@Valid @RequestBody ProveedorDTO dto) {
        return proveedorService.crearProveedor(dto);
    }

    /**
     * Actualizar proveedor existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> actualizarProveedor(@PathVariable int id, @Valid @RequestBody ProveedorDTO dto) {
        return proveedorService.actualizarProveedor(id, dto);
    }

    /**
     * Eliminar proveedor por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable int id) {
        return proveedorService.eliminarProveedor(id);
    }
}

