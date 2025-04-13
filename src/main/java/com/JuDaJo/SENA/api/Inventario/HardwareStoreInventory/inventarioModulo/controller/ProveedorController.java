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

    // Obtener un proveedor por nombre
    // Se utiliza para buscar un proveedor específico por su nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProveedorDTO> getProveedorPorNombre(@PathVariable String nombre) {
        Optional<Proveedor> proveedor = proveedorRepository.findByNombreProveedor(nombre);

        // Convertir la entidad Proveedor a ProveedorDTO
        return proveedor.map(prov -> {
            ProveedorDTO proveedorDTO = new ProveedorDTO(
                    prov.getIdProveedor(),
                    prov.getNombreProveedor(),
                    prov.getNitProveedor(),
                    prov.getTelefonoProveedor(),
                    prov.getDireccionProveedor()
            );
            return ResponseEntity.ok(proveedorDTO); // Devuelve solo los datos relevantes
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Si no existe, 404
    }

    // Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<ProveedorDTO> crearProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombreProveedor(proveedorDTO.getNombreProveedor());
        proveedor.setNitProveedor(proveedorDTO.getNitProveedor());
        proveedor.setTelefonoProveedor(proveedorDTO.getTelefonoProveedor());
        proveedor.setDireccionProveedor(proveedorDTO.getDireccionProveedor());

        Proveedor nuevoProveedor = proveedorRepository.save(proveedor);

        ProveedorDTO nuevoProveedorDTO = new ProveedorDTO(
                nuevoProveedor.getIdProveedor(),
                nuevoProveedor.getNombreProveedor(),
                nuevoProveedor.getNitProveedor(),
                nuevoProveedor.getTelefonoProveedor(),
                nuevoProveedor.getDireccionProveedor());

        return new ResponseEntity<>(nuevoProveedorDTO, HttpStatus.CREATED);
    }

    // Actualizar un proveedor existente
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> actualizarProveedor(@PathVariable int id, @Valid @RequestBody ProveedorDTO proveedorDTO) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombreProveedor(proveedorDTO.getNombreProveedor());
                    proveedor.setNitProveedor(proveedorDTO.getNitProveedor());
                    proveedor.setTelefonoProveedor(proveedorDTO.getTelefonoProveedor());
                    proveedor.setDireccionProveedor(proveedorDTO.getDireccionProveedor());

                    Proveedor proveedorActualizado = proveedorRepository.save(proveedor);
                    return ResponseEntity.ok(new ProveedorDTO(
                            proveedorActualizado.getIdProveedor(),
                            proveedorActualizado.getNombreProveedor(),
                            proveedorActualizado.getNitProveedor(),
                            proveedorActualizado.getTelefonoProveedor(),
                            proveedorActualizado.getDireccionProveedor()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
