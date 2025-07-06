package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PermisoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PermisoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de permisos en el sistema.
 * Este controlador expone endpoints REST para realizar operaciones CRUD
 * sobre los permisos, tales como crear, consultar, actualizar y eliminar.
 */
@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    /**
     * Crea un nuevo permiso.
     */
    @PostMapping
    public ResponseEntity<PermisoDTO> crearPermiso(@Valid @RequestBody PermisoDTO dto) {
        return ResponseEntity.status(201).body(permisoService.crearPermiso(dto));
    }

    /**
     * Lista todos los permisos existentes.
     */
    @GetMapping
    public ResponseEntity<List<PermisoDTO>> listarPermisos() {
        return ResponseEntity.ok(permisoService.listarPermisos());
    }

    /**
     * Busca un permiso por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermisoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(permisoService.obtenerPermisoPorId(id));
    }

    /**
     * Busca permisos por nombre.
     */
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<PermisoDTO> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(permisoService.buscarPermisoPorNombre(nombre));
    }

    /**
     * Verifica la existencia de un permiso basado en su nombre.
     */
    @GetMapping("/existe/{nombre}")
    public ResponseEntity<Boolean> verificarExistencia(@PathVariable String nombre) {
        boolean existe = permisoService.existePorNombre(nombre);
        return ResponseEntity.ok(existe);
    }

    /**
     * Actualiza un permiso existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PermisoDTO> actualizarPermiso(
            @PathVariable Integer id,
            @Valid @RequestBody PermisoDTO dto
    ) {
        return ResponseEntity.ok(permisoService.actualizarPermiso(id, dto));
    }

    /**
     * Elimina un permiso por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable Integer id) {
        permisoService.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }
}
