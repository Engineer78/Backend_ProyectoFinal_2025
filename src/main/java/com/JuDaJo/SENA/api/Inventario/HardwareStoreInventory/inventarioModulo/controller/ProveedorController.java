package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.dto.ProveedorDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Proveedor;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.repository.ProveedorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Controlador REST para gestionar proveedores
// Permite realizar operaciones CRUD sobre la entidad Proveedor
// @RestController indica que esta clase es un controlador REST y maneja las peticiones HTTP


@RestController
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde tu frontend
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorRepository proveedorRepository;

    public ProveedorController(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    // Obtener todos los proveedores
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> listarProveedores() {
        List<ProveedorDTO> proveedores = proveedorRepository.findAll().stream()
                .map(proveedor -> new ProveedorDTO(
                        proveedor.getIdProveedor(),
                        proveedor.getNombreProveedor(),
                        proveedor.getNitProveedor(),
                        proveedor.getTelefonoProveedor(),
                        proveedor.getDireccionProveedor()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(proveedores);
    }

    // Obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtenerProveedor(@PathVariable int id) {
        return proveedorRepository.findById(id)
                .map(proveedor -> new ProveedorDTO(
                        proveedor.getIdProveedor(),
                        proveedor.getNombreProveedor(),
                        proveedor.getNitProveedor(),
                        proveedor.getTelefonoProveedor(),
                        proveedor.getDireccionProveedor()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener un proveedor por NIT
    // Se utiliza para buscar un proveedor específico por su NIT
    @GetMapping("/nit/{nitProveedor}")
    public ResponseEntity<ProveedorDTO> buscarProveedorPorNit(@PathVariable String nitProveedor) {
        return proveedorRepository.findByNitProveedor(nitProveedor)
                .map(proveedor -> new ProveedorDTO(
                        proveedor.getIdProveedor(),
                        proveedor.getNombreProveedor(),
                        proveedor.getNitProveedor(),
                        proveedor.getTelefonoProveedor(),
                        proveedor.getDireccionProveedor()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
