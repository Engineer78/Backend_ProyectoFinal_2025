package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PerfilDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PerfilService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones relacionadas con la entidad Perfil.
 * Proporciona una interfaz para listar, obtener, crear, actualizar y eliminar perfiles
 * en el sistema a través de diferentes endpoints.
 */
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

    // ================================
    // Obtener perfil por ID
    // ================================
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfilById(@PathVariable Integer id) {
        PerfilDTO perfil = perfilService.obtenerPerfilPorId(id);
        if (perfil == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perfil, HttpStatus.OK);
    }

    // ================================
    // Crear perfil
    // ================================
    @PostMapping()
    @Transactional
    public ResponseEntity<PerfilDTO> createPerfil(@RequestBody PerfilDTO perfilDTO) {
        PerfilDTO createdPerfil = perfilService.crearPerfil(perfilDTO);
        return new ResponseEntity<>(createdPerfil, HttpStatus.CREATED);
    }

    // ================================
    // Actualizar perfil
    // ================================
    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> updatePerfil(@PathVariable Integer id, @RequestBody PerfilDTO perfilDTO) {
        PerfilDTO updatedPerfil = perfilService.actualizarPerfil(id, perfilDTO);
        if (updatedPerfil == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPerfil, HttpStatus.OK);
    }

    // ================================
    // Eliminar perfil
    // ================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Integer id) {
        boolean isDeleted = perfilService.eliminarPerfil(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
