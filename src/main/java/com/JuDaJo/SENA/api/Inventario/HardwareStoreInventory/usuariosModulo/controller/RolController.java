package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

// Indica que esta clase es un controlador REST
@RestController
@RequestMapping("/api/roles") // Ruta base para todas las peticiones relacionadas con roles
public class RolController {

    private final RolService rolService;

    // Inyección del servicio RolService a través del constructor
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }
}
