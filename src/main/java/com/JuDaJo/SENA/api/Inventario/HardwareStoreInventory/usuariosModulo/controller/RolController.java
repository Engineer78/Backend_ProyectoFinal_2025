package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Indica que esta clase es un controlador REST
@RestController
@RequestMapping("/api/roles") // Ruta base para todas las peticiones relacionadas con roles
public class RolController {

    private final RolService rolService;

    // Inyección del servicio RolService a través del constructor
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // ================================
    // Crear un nuevo rol
    // ================================
    @PostMapping
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolDTO rolDTO) {
        RolDTO nuevoRol = rolService.crearRol(rolDTO);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    // ================================
    // Listar todos los roles
    // ================================
    @GetMapping
    public ResponseEntity<List<RolDTO>> listarRoles() {
        List<RolDTO> roles = rolService.listarRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    // ================================
    // Obtener un rol por su ID
    // ================================
    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerRolPorId(@PathVariable Integer id) {
        RolDTO rol = rolService.obtenerRolPorId(id);
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    // ================================
    // Actualizar un rol existente
    // ================================
    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizarRol(@PathVariable Integer id, @RequestBody RolDTO rolDTO) {
        RolDTO rolActualizado = rolService.actualizarRol(id, rolDTO);
        return new ResponseEntity<>(rolActualizado, HttpStatus.OK);
    }

    // ================================
    // Eliminar un rol por su ID
    // ================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 sin contenido
    }
}
