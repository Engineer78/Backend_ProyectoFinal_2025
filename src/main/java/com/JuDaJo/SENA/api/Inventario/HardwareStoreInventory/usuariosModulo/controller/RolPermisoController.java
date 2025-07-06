package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolPermisoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsable de gestionar las relaciones entre roles y permisos en el sistema.
 * Proporciona los puntos de entrada de la API para la creación, actualización, eliminación y consulta
 * de relaciones entre roles y permisos.
 */
@RestController
@RequestMapping("/api/rol-permiso")
public class RolPermisoController {

    @Autowired
    private RolPermisoService rolPermisoService;

    // Crear una nueva relación Rol-Permiso
    @PostMapping
    public ResponseEntity<RolPermisoDTO> crear(@RequestBody RolPermisoDTO dto) {
        return ResponseEntity.ok(rolPermisoService.crearRolPermiso(dto));
    }

    // Listar todas las relaciones
    @GetMapping
    public ResponseEntity<List<RolPermisoDTO>> listar() {
        return ResponseEntity.ok(rolPermisoService.listarRelaciones());
    }

    // Listar relaciones por ID de Rol
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<RolPermisoDTO>> listarPorRol(@PathVariable int idRol) {
        return ResponseEntity.ok(rolPermisoService.listarPorRol(idRol));
    }

    // Listar relaciones si existen por ID de Rol y Permiso
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeRolPermiso(
            @RequestParam int idRol,
            @RequestParam int idPermiso
    ) {
        boolean existe = rolPermisoService.existeRolPermiso(idRol, idPermiso);
        return ResponseEntity.ok(existe);
    }

    // Actualizar los permisos asociados a un rol específico, reemplazando todos los existentes
    @PutMapping("/actualizar-permisos/{idRol}")
    public ResponseEntity<Void> actualizarPermisosRol(
            @PathVariable int idRol,
            @RequestBody List<Integer> idsPermisos
    ) {
        rolPermisoService.actualizarPermisosPorRol(idRol, idsPermisos);
        return ResponseEntity.ok().build();
    }

    // Eliminar relación
    @DeleteMapping("/{idRolPermiso}")
    public ResponseEntity<Void> eliminar(@PathVariable int idRolPermiso) {
        rolPermisoService.eliminarRolPermiso(idRolPermiso);
        return ResponseEntity.noContent().build();
    }

    // Eliminar una relación pasando rol y permiso (desde frontend)
    @DeleteMapping
    public ResponseEntity<Void> eliminarPorRolYPermiso(
            @RequestParam("idRol") int idRol,
            @RequestParam("idPermiso") int idPermiso
    ) {
        rolPermisoService.eliminarRolPermisoPorRolYPermiso(idRol, idPermiso);
        return ResponseEntity.noContent().build();
    }

}

