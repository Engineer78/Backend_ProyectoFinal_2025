package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PerfilDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PerfilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Indica que esta clase es un controlador REST
@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    // Inyección del servicio PerfilService a través del constructor
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    // ================================
    // Listar todos los perfiles
    // ================================
    @GetMapping("/buscar")
    public ResponseEntity<List<PerfilDTO>> listarPerfiles() {
        List<PerfilDTO> perfiles = perfilService.listarPerfiles();
        return new ResponseEntity<>(perfiles, HttpStatus.OK);
    }


}
