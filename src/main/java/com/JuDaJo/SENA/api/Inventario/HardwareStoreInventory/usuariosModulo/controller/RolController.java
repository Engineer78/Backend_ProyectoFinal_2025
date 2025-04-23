package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<RolDTO>> listarRoles() {
        List<RolDTO> roles = rolService.listarRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
