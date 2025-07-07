package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PerfilDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PerfilService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para los perfiles. Contiene la lógica de negocio
 * para crear, obtener, actualizar y eliminar perfiles.
 */
@Service
public class PerfilServiceImpl implements PerfilService {

    /**
     * Inyectar dependencia de PerfilRepository
     * Esto permitirá que PerfilServiceImpl acceda a las operaciones de base de datos necesarias para manejar los perfiles
     */
    private final PerfilRepository perfilRepository;

    // Inyectar dependencia de MovimientoInventarioService
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    // Inyectar dependencia de UsuarioRepository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public PerfilServiceImpl(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @Autowired
    private RegistroMovimientoService registroMovimientoService;

    /**
     * Crea un nuevo perfil en la base de datos a partir de un objeto PerfilDTO.
     * @param perfilDTO Objeto con los datos del nuevo perfil.
     * @return El perfil creado como DTO, incluyendo su ID generado.
     */
    @Override
    @Transactional
    public PerfilDTO crearPerfil(PerfilDTO perfilDTO) {
        boolean existeNombre = perfilRepository.findAll().stream()
                .anyMatch(p -> p.getNombrePerfil().equalsIgnoreCase(perfilDTO.getNombrePerfil()));

        if (existeNombre) {
            throw new DuplicadoException("Ya existe un perfil con ese nombre.");
        }

        boolean existeDescripcion = perfilRepository.findAll().stream()
                .anyMatch(p -> p.getDescripcion().equalsIgnoreCase(perfilDTO.getDescripcion()));

        if (existeDescripcion) {
            throw new DuplicadoException("Ya existe un perfil con esa descripción.");
        }

        Perfil perfil = new Perfil();
        perfil.setNombrePerfil(perfilDTO.getNombrePerfil());
        perfil.setDescripcion(perfilDTO.getDescripcion());

        Perfil perfilGuardado = perfilRepository.save(perfil);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        String detalle = "Se creó el perfil: " + perfilGuardado.getNombrePerfil();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("Perfil");
        movimiento.setIdEntidadAfectada(String.valueOf(perfilGuardado.getIdPerfil()));
        movimiento.setNombreEntidadAfectada(perfilGuardado.getNombrePerfil());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(perfilGuardado);
    }

    /**
     * Convierte una entidad Perfil a un objeto PerfilDTO.
     * @param perfil Entidad Perfil desde la base de datos.
     * @return Objeto PerfilDTO con los mismos datos.
     */
    private PerfilDTO toDTO(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO();
        dto.setIdPerfil(perfil.getIdPerfil());
        dto.setNombrePerfil(perfil.getNombrePerfil());
        dto.setDescripcion(perfil.getDescripcion());
        return dto;
    }

    /**
     * Obtiene una lista de todos los perfiles existentes como DTOs.
     * @return Lista de objetos PerfilDTO.
     */
    @Override
    public List<PerfilDTO> listarPerfiles() {
        return perfilRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un perfil por su ID.
     *
     * @param id ID del perfil a buscar.
     * @return PerfilDTO correspondiente al ID.
     */
    @Override
    public PerfilDTO obtenerPerfilPorId(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Perfil no encontrado con ID: " + id));

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Perfil");
        movimiento.setIdEntidadAfectada(String.valueOf(perfil.getIdPerfil()));
        movimiento.setNombreEntidadAfectada(perfil.getNombrePerfil());
        movimiento.setDetalleMovimiento("Se consultó el perfil con ID: " + id);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(perfil);
    }

    /**
     * Actualiza los datos de un perfil existente.
     * @param id        ID del perfil a actualizar.
     * @param perfilDTO Objeto con los nuevos datos del perfil.
     * @return PerfilDTO actualizado.
     */
    @Override
    @Transactional
    public PerfilDTO actualizarPerfil(Integer id, PerfilDTO perfilDTO) {
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Perfil no encontrado con ID: " + id));

        boolean nombreDuplicado = perfilRepository.findAll().stream()
                .anyMatch(p -> !p.getIdPerfil().equals(id) && p.getNombrePerfil().equalsIgnoreCase(perfilDTO.getNombrePerfil()));
        if (nombreDuplicado) {
            throw new DuplicadoException("Ya existe otro perfil con ese nombre.");
        }

        boolean descripcionDuplicada = perfilRepository.findAll().stream()
                .anyMatch(p -> !p.getIdPerfil().equals(id) && p.getDescripcion().equalsIgnoreCase(perfilDTO.getDescripcion()));
        if (descripcionDuplicada) {
            throw new DuplicadoException("Ya existe otro perfil con esa descripción.");
        }

        String nombreAnterior = perfilExistente.getNombrePerfil();
        String descripcionAnterior = perfilExistente.getDescripcion();

        perfilExistente.setNombrePerfil(perfilDTO.getNombrePerfil());
        perfilExistente.setDescripcion(perfilDTO.getDescripcion());

        Perfil perfilActualizado = perfilRepository.save(perfilExistente);

        // 🔐 Usuario  logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        StringBuilder detalle = new StringBuilder("Se actualizó el perfil: " + perfilActualizado.getNombrePerfil());
        if (!nombreAnterior.equals(perfilActualizado.getNombrePerfil())) {
            detalle.append(" | Nombre: ").append(nombreAnterior).append(" → ").append(perfilActualizado.getNombrePerfil());
        }
        if (!descripcionAnterior.equals(perfilActualizado.getDescripcion())) {
            detalle.append(" | Descripción: ").append(descripcionAnterior).append(" → ").append(perfilActualizado.getDescripcion());
        }

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Perfil");
        movimiento.setIdEntidadAfectada(String.valueOf(perfilActualizado.getIdPerfil()));
        movimiento.setNombreEntidadAfectada(perfilActualizado.getNombrePerfil());
        movimiento.setDetalleMovimiento(detalle.toString());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(perfilActualizado);
    }

    /**
     * Elimina un perfil de la base de datos.
     * @param id ID del perfil a eliminar.
     */
    @Override
    @Transactional
    public boolean eliminarPerfil(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Perfil con ID " + id + " no encontrado"));

        String nombrePerfil = perfil.getNombrePerfil();

        perfilRepository.delete(perfil);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        String detalle = "Se eliminó el perfil: " + nombrePerfil + " con ID " + id;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("Perfil");
        movimiento.setIdEntidadAfectada(String.valueOf(id));
        movimiento.setNombreEntidadAfectada(nombrePerfil);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return true;
    }
}
