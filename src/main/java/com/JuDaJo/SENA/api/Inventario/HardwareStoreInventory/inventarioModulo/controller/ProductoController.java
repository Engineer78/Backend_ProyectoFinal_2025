package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.dto.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Categoria;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Producto;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.ProductoProveedor;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.model.Proveedor;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.repository.CategoriaRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.repository.ProductoProveedorRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.repository.ProductoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar los productos en el inventario.
 * Proporciona funcionalidades como crear, actualizar, eliminar y consultar productos.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/productos")

public class ProductoController {
    private final ProductoRepository productoRepository;
    private final ProductoProveedorRepository productoProveedorRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;
    /**
     * Constructor para inicializar los repositorios necesarios.
     *
     * @param productoRepository Repositorio para la gestión de productos.
     * @param productoProveedorRepository Repositorio para relaciones Producto-Proveedor.
     * @param categoriaRepository Repositorio para categorías.
     * @param proveedorRepository Repositorio para proveedores.
     */
    public ProductoController(
            ProductoRepository productoRepository,
            ProductoProveedorRepository productoProveedorRepository,
            CategoriaRepository categoriaRepository,
            ProveedorRepository proveedorRepository
    ) {
        this.productoRepository = productoRepository;
        this.productoProveedorRepository = productoProveedorRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
    }
    /**
     * Obtiene un producto específico por su ID.
     *
     * @param idProducto Identificador único del producto.
     * @return Producto en formato DTO si se encuentra, o código 404 si no existe.
     */
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Integer idProducto) {
        return productoRepository.findById(idProducto)
                .map(ProductoDTO::new) // Convierte el Producto en un ProductoDTO
                .map(ResponseEntity::ok) // Envuelve el DTO en una respuesta con código 200
                .orElse(ResponseEntity.notFound().build()); // Devuelve 404 si no se encuentra
    }

    /**
     * Obtiene un producto específico por su código.
     *
     * @param codigoProducto Código único del producto.
     * @return Producto en formato DTO si se encuentra, o código 404 si no existe.
     */
    @GetMapping("/codigo/{codigoProducto}")
    public ResponseEntity<ProductoDTO> getProductoByCodigo(@PathVariable Integer codigoProducto) {
        return productoRepository.findByCodigoProducto(codigoProducto) // Método del repositorio
                .map(ProductoDTO::new) // Convierte el Producto en un ProductoDTO
                .map(ResponseEntity::ok) // Envuelve el DTO en una respuesta con código 200
                .orElse(ResponseEntity.notFound().build()); // Devuelve 404 si no se encuentra
    }

    /**
     * Obtiene un producto específico por su nombre.lo  q no entiendo es q paso si eso estaba funcionando esa consdulta por nombre d eproducto funciona
     *
     * @param nombre El nombre del producto a buscar.
     * @return Producto en formato DTO si se encuentra, o código 404 si no existe.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProductoConsultaDTO> getProductoPorNombre(@PathVariable String nombre) {
        Optional<Producto> producto = productoRepository.findByNombreProducto(nombre);

        // Convertir la entidad Producto a ProductoConsultaDTO
        return producto.map(prod -> {
            ProductoConsultaDTO productoDTO = new ProductoConsultaDTO(prod);
            return ResponseEntity.ok(productoDTO); // Devuelve solo los datos relevantes
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Si no existe, 404
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(
            @RequestParam(required = false) Integer codigoProducto,
            @RequestParam(required = false) String nombreProducto,
            @RequestParam(required = false) String nombreCategoria,
            @RequestParam(required = false) String nitProveedor,
            @RequestParam(required = false) String nombreProveedor,
            @RequestParam(required = false) Integer cantidad,
            @RequestParam(required = false) Double valorUnitarioProducto,
            @RequestParam(required = false) Double valorTotalProducto
    ) {
        List<Producto> productos = productoRepository.buscarProductosConFiltros(
                codigoProducto,
                nombreProducto,
                nombreCategoria,
                nitProveedor,
                nombreProveedor,
                cantidad,
                valorUnitarioProducto,
                valorTotalProducto
        );

        List<ProductoDTO> productosDTO = productos.stream()
                .map(producto -> {
                    ProductoDTO dto = new ProductoDTO(producto);
                    if (producto.getProductoProveedores() != null && !producto.getProductoProveedores().isEmpty()) {
                        Proveedor proveedor = producto.getProductoProveedores().get(0).getProveedor();
                        if (proveedor != null) {
                            dto.setNombreProveedor(proveedor.getNombreProveedor());
                            dto.setNitProveedor(proveedor.getNitProveedor());
                        }
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(productosDTO);
    }
    /**
     * Crea un nuevo producto en el inventario.
     *
     * @param productCreationDTO DTO con los datos necesarios para crear el producto.
     * @param bindingResult Resultado de la validación de los datos enviados.
     * @return Producto creado en formato DTO o detalles del error.
     */
    @PostMapping
    @Transactional
    public ResponseEntity<?> addProducto(@Valid @RequestBody ProductoCreationDTO productCreationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Buscar o crear la categoría
            Categoria categoria = categoriaRepository.findByNombreCategoria(productCreationDTO.getNombreCategoria())
                    .orElseGet(() -> {
                        Categoria nuevaCategoria = new Categoria();
                        nuevaCategoria.setNombreCategoria(productCreationDTO.getNombreCategoria());
                        return categoriaRepository.save(nuevaCategoria);
                    });

            // Buscar o crear el proveedor
            Proveedor proveedor = proveedorRepository.findByNitProveedor(productCreationDTO.getNitProveedor())
                    .orElseGet(() -> {
                        Proveedor nuevoProveedor = new Proveedor();
                        nuevoProveedor.setNombreProveedor(productCreationDTO.getNombreProveedor());
                        nuevoProveedor.setNitProveedor(productCreationDTO.getNitProveedor());
                        nuevoProveedor.setDireccionProveedor(productCreationDTO.getDireccionProveedor());
                        nuevoProveedor.setTelefonoProveedor(productCreationDTO.getTelefonoProveedor());
                        return proveedorRepository.save(nuevoProveedor);
                    });

            // Crear el producto y asociarlo a la categoría y al proveedor
            Producto nuevoProducto = new Producto();
            nuevoProducto.setCodigoProducto(productCreationDTO.getProducto().getCodigoProducto());
            nuevoProducto.setNombreProducto(productCreationDTO.getProducto().getNombreProducto());
            nuevoProducto.setCantidad(productCreationDTO.getProducto().getCantidad());
            nuevoProducto.setValorUnitarioProducto(productCreationDTO.getProducto().getValorUnitarioProducto());
            nuevoProducto.setValorTotalProducto(productCreationDTO.getProducto().getValorTotalProducto());
            nuevoProducto.setCategoria(categoria);
            nuevoProducto.setProveedor(proveedor);
            nuevoProducto.setImagen(productCreationDTO.getProducto().getImagen());

            nuevoProducto = productoRepository.save(nuevoProducto);
            // Crear la relación ProductoProveedor
            ProductoProveedor productoProveedor = new ProductoProveedor();
            productoProveedor.setProducto(nuevoProducto); // Asocia el producto GUARDADO
            productoProveedor.setProveedor(proveedor);
            productoProveedor.setPrecioCompra(nuevoProducto.getValorUnitarioProducto());

            productoProveedorRepository.save(productoProveedor); // Guarda la relación ProductoProveedor


            return new ResponseEntity<>(new ProductoDTO(nuevoProducto), HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el producto: " + e.getMessage());
        }
    }
}
