package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador para gestionar las operaciones relacionadas con los movimientos de inventario.
 */
@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MovimientoInventarioController {

    /**
     * Servicio de manejo de operaciones relacionadas con los movimientos de inventario.
     * Proporciona métodos para listar movimientos, listar por entidad afectada,
     * y registrar nuevos movimientos en el sistema.
     */
    @Autowired
    private MovimientoInventarioService movimientoService;

    /**
     * Servicio utilizado para el registro de movimientos en el sistema de inventario.
     * Proporciona funcionalidades para registrar operaciones como creación, actualización,
     * consulta o eliminación de movimientos asociados a diferentes entidades del inventario.
     */
    @Autowired
    private RegistroMovimientoService registroMovimientoService;


    /**
     * Registrar un nuevo movimiento (crear, actualizar, eliminar, consultar).
     */
    @PostMapping
    public void registrarMovimiento(@RequestBody MovimientoInventarioDTO dto) {
        registroMovimientoService.registrarMovimiento(dto);
    }

    /**
     * Listar todos los movimientos registrados.
     */
    @GetMapping
    public List<MovimientoInventarioDTO> listarTodos() {
        return movimientoService.listarMovimientos();
    }

    /**
     * Listar movimientos filtrados por entidad afectada.
     * Ejemplo: /api/movimientos/entidad/Producto
     */
    @GetMapping("/entidad/{nombre}")
    public List<MovimientoInventarioDTO> listarPorEntidad(@PathVariable String nombre) {
        return movimientoService.listarPorEntidad(nombre);
    }
}
