package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.EmpleadoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador REST para gestionar los empleados.
 */
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    /**
     * Se inyecta el servicio de EmpleadoService.
     * @see EmpleadoService
     */
    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Lista todos los empleados registrados.
     */
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    /**
     * Busca un empleado por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(empleadoService.buscarPorId(id));
    }

    /**
     * Busca un empleado por su número de documento.
     */
    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<EmpleadoDTO> buscarPorDocumento(@PathVariable String numeroDocumento) {
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorDocumento(numeroDocumento));
    }

}
