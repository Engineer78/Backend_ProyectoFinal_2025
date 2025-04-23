package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PerfilService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Indica que esta clase es un controlador REST
@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    private PerfilService perfilService;

}
