package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.*;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.ProductoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Implementación de la interfaz ProductoService.
 * Gestiona la lógica de negocio relacionada con productos, proveedores y categorías.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    // Repositorios necesarios para las operaciones con productos, proveedores y categorías
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoProveedorRepository productoProveedorRepository;

    // Repositorios necesarios para las operaciones con movimientos de inventario
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroMovimientoService registroMovimientoService;


    /**
     * Construye una instancia de ProductoServiceImpl con las dependencias de repositorio especificadas.
     *
     * @param productoRepository el repositorio utilizado para operaciones relacionadas con entidades Producto
     * @param categoriaRepository el repositorio utilizado para operaciones relacionadas con entidades Categoria
     * @param proveedorRepository el repositorio utilizado para operaciones relacionadas con entidades Proveedor
     * @param productoProveedorRepository el repositorio utilizado para operaciones relacionadas con entidades ProductoProveedor
     */
    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository,
                               ProveedorRepository proveedorRepository,
                               ProductoProveedorRepository productoProveedorRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoProveedorRepository = productoProveedorRepository;
    }

    /**
     * Crea un producto nuevo junto con su relación con proveedor y categoría.
     */
    @Override
    @Transactional
    public ResponseEntity<?> crearProducto(ProductoCreationDTO dto) {
        // 🚫 Validación personalizada: evitar códigos duplicados
        boolean existeCodigo = productoRepository.findByCodigoProducto(dto.getProducto().getCodigoProducto()).isPresent();
        if (productoRepository.findByCodigoProducto(dto.getProducto().getCodigoProducto()).isPresent()) {
            throw new DuplicadoException("Ya existe un producto con ese código");
        }
        // Buscar o crear categoría
        Categoria categoria = categoriaRepository.findByNombreCategoria(dto.getNombreCategoria())
                .orElseGet(() -> categoriaRepository.save(new Categoria(dto.getNombreCategoria())));

        // Buscar o crear proveedor
        Proveedor proveedor = proveedorRepository.findByNitProveedor(dto.getNitProveedor())
                .orElseGet(() -> proveedorRepository.save(new Proveedor(
                        dto.getNombreProveedor(),
                        dto.getNitProveedor(),
                        dto.getTelefonoProveedor(),
                        dto.getDireccionProveedor()
                )));

        // Crear y configurar el producto
        Producto producto = new Producto();
        producto.setCodigoProducto(dto.getProducto().getCodigoProducto());
        producto.setNombreProducto(dto.getProducto().getNombreProducto());
        producto.setCantidad(dto.getProducto().getCantidad());
        producto.setValorUnitarioProducto(dto.getProducto().getValorUnitarioProducto());
        producto.setValorTotalProducto(dto.getProducto().getCantidad() * dto.getProducto().getValorUnitarioProducto());
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);
        producto.setImagen(dto.getProducto().getImagen());

        producto = productoRepository.save(producto);

        // Crear relación producto-proveedor
        ProductoProveedor pp = new ProductoProveedor(producto, proveedor, producto.getValorUnitarioProducto());
        productoProveedorRepository.save(pp);

        // Obtener usuario autenticado desde el token
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar detalle del movimiento
        String detalle = "Se creó el producto: " + producto.getNombreProducto()
                + " | Cantidad: " + producto.getCantidad()
                + " | Valor unitario: $" + producto.getValorUnitarioProducto();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setIdEntidadAfectada(String.valueOf(producto.getIdProducto()));
        movimiento.setNombreEntidadAfectada(producto.getNombreProducto());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return ResponseEntity.status(201).body(new ProductoDTO(producto));
    }

    /**
     * Actualiza un producto existente junto con proveedor y categoría.
     */
    @Override
    @Transactional
    public ResponseEntity<?> actualizarProducto(Integer idProducto, ProductoUpdateDTO dto) {
        Producto producto = productoRepository.findByIdWithProveedor(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // 🔁 Clonar datos antes de cualquier cambio
        String categoriaAnterior = producto.getCategoria().getNombreCategoria();
        Proveedor proveedorAnterior = new Proveedor();
        proveedorAnterior.setNombreProveedor(producto.getProveedor().getNombreProveedor());
        proveedorAnterior.setNitProveedor(producto.getProveedor().getNitProveedor());
        proveedorAnterior.setDireccionProveedor(producto.getProveedor().getDireccionProveedor());
        proveedorAnterior.setTelefonoProveedor(producto.getProveedor().getTelefonoProveedor());
        int cantidadAnterior = producto.getCantidad();
        double valorUnitarioAnterior = producto.getValorUnitarioProducto();
        double valorTotalAnterior = producto.getValorTotalProducto();
        String imagenAnterior = producto.getImagen();

        // Validación de duplicado
        Optional<Producto> productoConMismoCodigo = productoRepository.findByCodigoProducto(dto.getCodigoProducto());
        if (productoConMismoCodigo.isPresent() && !Objects.equals(productoConMismoCodigo.get().getIdProducto(), idProducto)) {
            throw new DuplicadoException("Ya existe otro producto con ese código");
        }

        // Actualizar datos del producto
        producto.setCodigoProducto(dto.getCodigoProducto());
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setCantidad(dto.getCantidad());
        producto.setValorUnitarioProducto(dto.getValorUnitarioProducto());
        producto.setValorTotalProducto(dto.getCantidad() * dto.getValorUnitarioProducto());
        producto.setImagen(dto.getImagen());

        Categoria categoria = categoriaRepository.findByNombreCategoria(dto.getNombreCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        producto.setCategoria(categoria);

        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        proveedor.setNombreProveedor(dto.getNombreProveedor());
        proveedor.setNitProveedor(dto.getNitProveedor());
        proveedor.setDireccionProveedor(dto.getDireccionProveedor());
        proveedor.setTelefonoProveedor(dto.getTelefonoProveedor());
        proveedorRepository.save(proveedor);

        producto.setProveedor(proveedor);

        ProductoProveedor pp = productoProveedorRepository
                .findByProductoIdProductoAndProveedorIdProveedor(producto.getIdProducto(), proveedor.getIdProveedor())
                .orElse(null);

        if (pp == null) {
            pp = new ProductoProveedor(producto, proveedor, dto.getValorUnitarioProducto());
        } else {
            pp.setPrecioCompra(dto.getValorUnitarioProducto());
        }
        productoProveedorRepository.save(pp);

        productoRepository.save(producto);

        // Usuario y empleado responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        if (empleadoResponsable == null) {
            throw new RuntimeException("El usuario autenticado no tiene un empleado asignado.");
        }

        // 📋 Armar detalle de la trazabilidad
        StringBuilder detalle = new StringBuilder("Se actualizó el producto: " + producto.getNombreProducto());

        if (!categoriaAnterior.equals(producto.getCategoria().getNombreCategoria())) {
            detalle.append(" | Categoría: ").append(categoriaAnterior)
                    .append(" → ").append(producto.getCategoria().getNombreCategoria());
        }
        if (!proveedorAnterior.getNombreProveedor().equals(proveedor.getNombreProveedor())) {
            detalle.append(" | Proveedor: ").append(proveedorAnterior.getNombreProveedor())
                    .append(" → ").append(proveedor.getNombreProveedor());
        }
        if (!proveedorAnterior.getNitProveedor().equals(proveedor.getNitProveedor())) {
            detalle.append(" | NIT: ").append(proveedorAnterior.getNitProveedor())
                    .append(" → ").append(proveedor.getNitProveedor());
        }
        if (!proveedorAnterior.getDireccionProveedor().equals(proveedor.getDireccionProveedor())) {
            detalle.append(" | Dirección: ").append(proveedorAnterior.getDireccionProveedor())
                    .append(" → ").append(proveedor.getDireccionProveedor());
        }
        if (!proveedorAnterior.getTelefonoProveedor().equals(proveedor.getTelefonoProveedor())) {
            detalle.append(" | Teléfono: ").append(proveedorAnterior.getTelefonoProveedor())
                    .append(" → ").append(proveedor.getTelefonoProveedor());
        }
        if (cantidadAnterior != producto.getCantidad()) {
            detalle.append(" | Cantidad: ").append(cantidadAnterior)
                    .append(" → ").append(producto.getCantidad());
        }
        if (valorUnitarioAnterior != producto.getValorUnitarioProducto()) {
            detalle.append(" | Valor unitario: $").append(valorUnitarioAnterior)
                    .append(" → $").append(producto.getValorUnitarioProducto());
        }
        if (valorTotalAnterior != producto.getValorTotalProducto()) {
            detalle.append(" | Valor total: $").append(valorTotalAnterior)
                    .append(" → $").append(producto.getValorTotalProducto());
        }
        if (!Objects.equals(imagenAnterior, producto.getImagen())) {
            detalle.append(" | Imagen actualizada");
        }

        // 📦 Registrar movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setIdEntidadAfectada(String.valueOf(producto.getIdProducto()));
        movimiento.setNombreEntidadAfectada(producto.getNombreProducto());
        movimiento.setDetalleMovimiento(detalle.toString());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return ResponseEntity.ok(new ProductoDTO(producto));
    }

    /**
     * Lista todos los productos del sistema.
     */
    @Override
    public List<ProductoDTO> listarTodos() {
        // Obtener usuario autenticado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setDetalleMovimiento("Se listaron todos los productos del sistema");
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return productoRepository.findAll().stream().map(ProductoDTO::new).collect(Collectors.toList());
    }

    /**
     * Busca un producto por su ID.
     */
    @Override
    public ProductoDTO buscarPorId(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        // Obtener usuario autenticado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // DEBUG
        System.out.println("🟢 Se va a registrar consulta por ID con empleado ID: " + empleadoResponsable.getIdEmpleado());

        // Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setIdEntidadAfectada(String.valueOf(producto.getIdProducto()));
        movimiento.setNombreEntidadAfectada(producto.getNombreProducto());
        movimiento.setDetalleMovimiento("Se consultó el producto por ID: " + idProducto);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        // DEBUG
        System.out.println("📤 Enviando a registroMovimientoService...");
        registroMovimientoService.registrarMovimiento(movimiento);

        return new ProductoDTO(producto);
    }

    /**
     * Busca un producto por su Código.
     */
    @Override
    public ProductoDTO buscarPorCodigo(Integer codigoProducto) {
        Producto producto = productoRepository.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado por código"));

        // Obtener usuario autenticado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setIdEntidadAfectada(String.valueOf(producto.getIdProducto()));
        movimiento.setNombreEntidadAfectada(producto.getNombreProducto());
        movimiento.setDetalleMovimiento("Se consultó el producto por código: " + codigoProducto);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new ProductoDTO(producto);
    }

    /**
     * Busca un producto por su nombre exacto.
     */
    @Override
    public ProductoConsultaDTO buscarPorNombre(String nombreProducto) {
        Producto producto = productoRepository.findByNombreProducto(nombreProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado por nombre"));

        // Obtener usuario autenticado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setIdEntidadAfectada(String.valueOf(producto.getIdProducto()));
        movimiento.setNombreEntidadAfectada(producto.getNombreProducto());
        movimiento.setDetalleMovimiento("Se consultó el producto por nombre: " + nombreProducto);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new ProductoConsultaDTO(producto);
    }

    /**
     * Realiza una búsqueda de productos usando filtros básicos.
     */
    @Override
    public List<ProductoDTO> buscarProductos(
            Integer codigoProducto,
            String nombreProducto,
            String nombreCategoria,
            String nitProveedor,
            String nombreProveedor,
            Integer cantidad,
            Double valorUnitarioProducto,
            Double valorTotalProducto,
            boolean registrar
    ) {
        List<ProductoDTO> resultados = productoRepository.buscarProductosConFiltros(
                        codigoProducto, nombreProducto, nombreCategoria,
                        nitProveedor, nombreProveedor, cantidad,
                        valorUnitarioProducto, valorTotalProducto)
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());

        if (registrar) {
            // Obtener usuario autenticado desde el JWT
            String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
            Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

            String detalle = "Búsqueda básica de productos con filtros: "
                    + (codigoProducto != null ? "Código: " + codigoProducto + " | " : "")
                    + (nombreProducto != null ? "Nombre: " + nombreProducto + " | " : "")
                    + (nombreCategoria != null ? "Categoría: " + nombreCategoria + " | " : "")
                    + (nitProveedor != null ? "NIT: " + nitProveedor + " | " : "")
                    + (nombreProveedor != null ? "Proveedor: " + nombreProveedor + " | " : "")
                    + (cantidad != null ? "Cantidad: " + cantidad + " | " : "")
                    + (valorUnitarioProducto != null ? "Valor unitario: " + valorUnitarioProducto + " | " : "")
                    + (valorTotalProducto != null ? "Valor total: " + valorTotalProducto : "");

            MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
            movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
            movimiento.setEntidadAfectada("Producto");
            movimiento.setDetalleMovimiento(detalle);
            movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
            movimiento.setNombreEmpleadoResponsable(
                    empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
            );

            registroMovimientoService.registrarMovimiento(movimiento);
        }

        return resultados;
    }


    /**
     * Realiza una búsqueda avanzada desde el modal.
     */
    @Override
    public List<ProductoDTO> buscarProductosAvanzados(
            String nitProveedor,
            String nombreProveedor,
            String cantidad,
            String valorUnitarioProducto,
            String valorTotalProducto,
            boolean registrar // 👈 nuevo parámetro
    ) {
        // 1. Buscar productos
        List<ProductoDTO> resultados = productoRepository.buscarProductosAvanzados(
                        nitProveedor, nombreProveedor, cantidad,
                        valorUnitarioProducto, valorTotalProducto)
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());

        // 2. Registrar movimiento solo si es necesario
        if (registrar) {
            String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
            Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

            StringBuilder detalle = new StringBuilder("Búsqueda avanzada de productos desde el modal: ");

            if (nitProveedor != null && !nitProveedor.isBlank()) {
                detalle.append("NIT: ").append(nitProveedor).append(" | ");
            }
            if (nombreProveedor != null && !nombreProveedor.isBlank()) {
                detalle.append("Proveedor: ").append(nombreProveedor).append(" | ");
            }
            if (cantidad != null && !cantidad.isBlank()) {
                detalle.append("Cantidad: ").append(cantidad).append(" | ");
            }
            if (valorUnitarioProducto != null && !valorUnitarioProducto.isBlank()) {
                detalle.append("Valor unitario: ").append(valorUnitarioProducto).append(" | ");
            }
            if (valorTotalProducto != null && !valorTotalProducto.isBlank()) {
                detalle.append("Valor total: ").append(valorTotalProducto);
            }

            MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
            movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
            movimiento.setEntidadAfectada("Producto");
            movimiento.setDetalleMovimiento(detalle.toString());
            movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
            movimiento.setNombreEmpleadoResponsable(
                    empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
            );

            registroMovimientoService.registrarMovimiento(movimiento);
        }

        return resultados;
    }


    /**
     * Elimina un producto y sus relaciones con proveedores.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> eliminarProducto(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        // Guardar datos antes de eliminar
        String nombreProducto = producto.getNombreProducto();
        String idProductoStr = String.valueOf(producto.getIdProducto());

        // 🔐 Obtener usuario autenticado
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        if (empleadoResponsable == null) {
            System.out.println("🚨 El usuario autenticado no tiene asociado un empleado. Movimiento NO registrado.");
            throw new RuntimeException("El usuario autenticado no tiene un empleado asignado.");
        }

        // 🧾 Movimiento antes de eliminar
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("Producto");
        movimiento.setIdEntidadAfectada(idProductoStr);
        movimiento.setNombreEntidadAfectada(nombreProducto);
        movimiento.setDetalleMovimiento("Se eliminó el producto " + nombreProducto + " con ID " + idProductoStr);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        System.out.println("🟡 [DEBUG] Tipo: " + movimiento.getTipoMovimiento()
                + " | Entidad: " + movimiento.getEntidadAfectada()
                + " - " + movimiento.getNombreEntidadAfectada());

        registroMovimientoService.registrarMovimiento(movimiento);

        // ❌ Ahora se limpian relaciones y se elimina
        if (producto.getProductoProveedores() != null) {
            new ArrayList<>(producto.getProductoProveedores()).forEach(pp -> {
                if (pp.getProveedor() != null) {
                    pp.getProveedor().getProductoProveedores().remove(pp);
                }
                producto.eliminarProductoProveedor(pp);
                productoProveedorRepository.delete(pp);
            });
        }

        productoRepository.delete(producto);
        return ResponseEntity.noContent().build();
    }
}