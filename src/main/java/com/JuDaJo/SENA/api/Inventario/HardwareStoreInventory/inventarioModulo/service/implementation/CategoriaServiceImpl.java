package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.CategoriaDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Categoria;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.CategoriaRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.CategoriaService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de categorías.
 * Se encarga de la lógica de negocio relacionada con la entidad Categoria.
 * Esta clase proporciona operaciones CRUD y búsquedas específicas para las categorías.
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    // Repositorio para acceder a las operaciones de base de datos de categorías
    private final CategoriaRepository categoriaRepository;

    // Inyecta repositorios necesarios para las operaciones con movimientos de inventario
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    // Inyecta repositorios necesarios para las operaciones con usuarios
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroMovimientoService registroMovimientoService;


    /**
     * Constructor que inicializa el servicio con el repositorio de categorías
     * @param categoriaRepository Repositorio de categorías inyectado por Spring
     */
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Obtiene una lista de todas las categorías existentes en el sistema
     * @return Lista de DTOs de categorías con su ID y nombre
     */
    @Override
    public List<CategoriaDTO> listarCategorias() {
        List<CategoriaDTO> lista = categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria()))
                .collect(Collectors.toList());

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Categoría");
        movimiento.setDetalleMovimiento("Se listaron todas las categorías del sistema");
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return lista;
    }

    /**
     * Busca y retorna una categoría específica por su ID
     * @param id ID de la categoría a buscar
     * @return ResponseEntity con el DTO de la categoría si se encuentra
     * @throws RecursoNoEncontradoException si la categoría no existe
     */
    @Override
    public ResponseEntity<CategoriaDTO> obtenerCategoria(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + id + " no encontrada"));

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Categoría");
        movimiento.setIdEntidadAfectada(String.valueOf(categoria.getIdCategoria()));
        movimiento.setNombreEntidadAfectada(categoria.getNombreCategoria());
        movimiento.setDetalleMovimiento("Se consultó la categoría por ID: " + id);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        CategoriaDTO dto = new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria());
        return ResponseEntity.ok(dto);
    }


    /**
     * Busca una categoría por su nombre exacto
     * @param nombre Nombre de la categoría a buscar
     * @return ResponseEntity con el DTO de la categoría si se encuentra
     * @throws RecursoNoEncontradoException si la categoría no existe
     */
    @Override
    public ResponseEntity<CategoriaDTO> buscarPorNombre(String nombre) {
        Categoria categoria = categoriaRepository.findByNombreCategoria(nombre)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con nombre '" + nombre + "' no encontrada"));

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Categoría");
        movimiento.setIdEntidadAfectada(String.valueOf(categoria.getIdCategoria()));
        movimiento.setNombreEntidadAfectada(categoria.getNombreCategoria());
        movimiento.setDetalleMovimiento("Se consultó la categoría por nombre: " + nombre);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        CategoriaDTO dto = new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria());
        return ResponseEntity.ok(dto);
    }

    /**
     * Crea una nueva categoría en el sistema
     * @param dto DTO con los datos de la nueva categoría
     * @return ResponseEntity con el DTO de la categoría creada
     * @throws DuplicadoException si ya existe una categoría con el mismo nombre
     */
    @Override
    public ResponseEntity<?> crearCategoria(CategoriaDTO dto) {
        // Verifica si ya existe una categoría con el mismo nombre
        boolean existe = categoriaRepository.findByNombreCategoria(dto.getNombreCategoria()).isPresent();
        if (existe) {
            throw new DuplicadoException("Ya existe una categoría con ese nombre");
        }

        // Crea y guarda la nueva categoría
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        Categoria nueva = categoriaRepository.save(categoria);

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Movimiento
        String detalle = "Se creó la categoría: " + nueva.getNombreCategoria();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("Categoria");
        movimiento.setIdEntidadAfectada(String.valueOf(nueva.getIdCategoria()));
        movimiento.setNombreEntidadAfectada(nueva.getNombreCategoria());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        CategoriaDTO respuesta = new CategoriaDTO(nueva.getIdCategoria(), nueva.getNombreCategoria());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    /**
     * Actualiza una categoría existente
     * @param id ID de la categoría a actualizar
     * @param dto DTO con los nuevos datos de la categoría
     * @return ResponseEntity con el DTO de la categoría actualizada
     * @throws RecursoNoEncontradoException si la categoría no existe
     */
    @Override
    public ResponseEntity<CategoriaDTO> actualizarCategoria(Integer id, CategoriaDTO dto) {
        // Busca la categoría existente
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + id + " no encontrada"));

        String nombreAnterior = categoria.getNombreCategoria();

        // Actualiza y guarda los cambios
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoriaRepository.save(categoria);

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Movimiento
        StringBuilder detalle = new StringBuilder("Se actualizó la categoría: " + categoria.getNombreCategoria());
        if (!nombreAnterior.equals(categoria.getNombreCategoria())) {
            detalle.append(" | Nombre: ").append(nombreAnterior).append(" → ").append(categoria.getNombreCategoria());
        }

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Categoría");
        movimiento.setIdEntidadAfectada(String.valueOf(categoria.getIdCategoria()));
        movimiento.setNombreEntidadAfectada(categoria.getNombreCategoria());
        movimiento.setDetalleMovimiento(detalle.toString());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        CategoriaDTO actualizado = new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombreCategoria());
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina una categoría del sistema
     * @param id ID de la categoría a eliminar
     * @return ResponseEntity sin contenido indicando éxito
     * @throws RecursoNoEncontradoException si la categoría no existe
     */
    @Override
    public ResponseEntity<Void> eliminarCategoria(Integer id) {
        // Verifica si la categoría existe antes de intentar eliminarla
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + id + " no encontrada para eliminar"));

        String nombreCategoria = categoria.getNombreCategoria(); // Guardar antes de eliminar

        categoriaRepository.deleteById(id);

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Movimiento
        String detalle = "Se eliminó la categoría: " + nombreCategoria + " con ID " + id;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("Categoría");
        movimiento.setIdEntidadAfectada(String.valueOf(id));
        movimiento.setNombreEntidadAfectada(nombreCategoria);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return ResponseEntity.noContent().build();
    }
}