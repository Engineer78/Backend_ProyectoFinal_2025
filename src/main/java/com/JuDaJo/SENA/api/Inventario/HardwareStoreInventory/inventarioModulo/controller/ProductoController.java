package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.ProductoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controlador REST para gestionar los productos en el inventario.
 * Proporciona funcionalidades como crear, actualizar, eliminar y consultar productos.
 */
@RestController
@RequestMapping("/api/productos")

public class ProductoController {

    private final ProductoService productoService;

    /**
     * Constructor para inicializar los repositorios necesarios.
     */
    public ProductoController(
            ProductoService productoService
    ) {
        this.productoService = productoService;

    }

    /**
     * Lista todos los productos del sistema.
     */
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID y registra el movimiento.
     */
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Integer idProducto) {
        ProductoDTO producto = productoService.buscarPorId(idProducto);
        return ResponseEntity.ok(producto);
    }

    /**
     * Obtiene un producto por su código y registra el movimiento.
     */
    @GetMapping("/codigo/{codigoProducto}")
    public ResponseEntity<ProductoDTO> getProductoByCodigo(@PathVariable Integer codigoProducto) {
        ProductoDTO producto = productoService.buscarPorCodigo(codigoProducto);
        return ResponseEntity.ok(producto);
    }

    /**
     * Obtiene un producto por su nombre exacto y registra el movimiento.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProductoConsultaDTO> getProductoPorNombre(@PathVariable String nombre) {
        ProductoConsultaDTO producto = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(producto);
    }

    /**
     * Realiza una búsqueda básica con filtros y registra el movimiento.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(
            @RequestParam(required = false) Integer codigoProducto,
            @RequestParam(required = false) String nombreProducto,
            @RequestParam(required = false) String nombreCategoria,
            @RequestParam(required = false) String nitProveedor,
            @RequestParam(required = false) String nombreProveedor,
            @RequestParam(required = false) Integer cantidad,
            @RequestParam(required = false) Double valorUnitarioProducto,
            @RequestParam(required = false) Double valorTotalProducto,
            @RequestParam(defaultValue = "true") boolean registrar
    ) {
        List<ProductoDTO> resultados = productoService.buscarProductos(
                codigoProducto, nombreProducto, nombreCategoria,
                nitProveedor, nombreProveedor, cantidad,
                valorUnitarioProducto, valorTotalProducto,
                registrar
        );
        return ResponseEntity.ok(resultados);
    }

    /**
     * Realiza una búsqueda avanzada desde el modal y registra el movimiento.
     */
    @GetMapping("/busqueda-avanzada")
    public ResponseEntity<List<ProductoDTO>> buscarProductosAvanzados(
            @RequestParam(required = false) String nitProveedor,
            @RequestParam(required = false) String nombreProveedor,
            @RequestParam(required = false) String cantidad,
            @RequestParam(required = false) String valorUnitarioProducto,
            @RequestParam(required = false) String valorTotalProducto,
            @RequestParam(defaultValue = "true") boolean registrar
    ) {
        List<ProductoDTO> resultados = productoService.buscarProductosAvanzados(
                nitProveedor, nombreProveedor, cantidad,
                valorUnitarioProducto, valorTotalProducto,registrar
        );
        return ResponseEntity.ok(resultados);
    }

    /**
     * Crea un nuevo producto y registra el movimiento.
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> crearProducto(@Valid @RequestBody ProductoCreationDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return productoService.crearProducto(dto);
    }

    /**
     * Actualiza un producto y registra el movimiento.
     */
    @PutMapping("/{idProducto}")
    @Transactional
    public ResponseEntity<?> updateProducto(
            @PathVariable Integer idProducto,
            @Valid @RequestBody ProductoUpdateDTO productoActualizado,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationErrorDTO errorDTO = new ValidationErrorDTO(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(errorDTO);
        }

        return productoService.actualizarProducto(idProducto, productoActualizado);
    }

    /**
     * Elimina un producto y registra el movimiento.
     */
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer idProducto) {
        return productoService.eliminarProducto(idProducto);
    }
}
