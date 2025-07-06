package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementación del servicio de roles que gestiona todas las operaciones relacionadas con roles.
 * Proporciona funcionalidades CRUD y búsquedas específicas para la gestión de roles en el sistema.
 */
@Service
public class RolServiceImpl implements RolService {

    // Repositorio para gestionar las operaciones de persistencia de roles
    private final RolRepository rolRepository;

    // Repositorio para gestionar las operaciones de persistencia de perfiles
    private final PerfilRepository perfilRepository;

    // Inyectar dependencia de MovimientoInventarioService
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    // Inyectar dependencia de UsuarioRepository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroMovimientoService registroMovimientoService;

    /**
     * Constructor para la inicialización de los repositorios necesarios
     * @param rolRepository repositorio para manipular los roles
     * @param perfilRepository repositorio para manipular los perfiles
     */
    @Autowired
    public RolServiceImpl(RolRepository rolRepository, PerfilRepository perfilRepository) {
        this.rolRepository = rolRepository;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Crea un nuevo rol en el sistema con la información proporcionada
     * @param rolDTO datos del nuevo rol a crear
     * @return RolDTO con la información del rol creado
     */
    @Override
    public RolDTO crearRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());

        if (rolDTO.getIdPerfil() != null) {
            Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil()));
            rol.setPerfil(perfil);
        }

        boolean nombreExistente = rolRepository.findByNombreRolContainingIgnoreCase(rolDTO.getNombreRol())
                .stream()
                .anyMatch(r -> r.getNombreRol().equalsIgnoreCase(rolDTO.getNombreRol()));

        if (nombreExistente) {
            throw new DuplicadoException("Ya existe un rol con ese nombre.");
        }

        Rol rolGuardado = rolRepository.save(rol);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        String detalle = "Se creó el rol: " + rolGuardado.getNombreRol();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("Rol");
        movimiento.setIdEntidadAfectada(String.valueOf(rolGuardado.getIdRol()));
        movimiento.setNombreEntidadAfectada(rolGuardado.getNombreRol());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(rolGuardado);
    }

    /**
     * Recupera y lista todos los roles existentes en el sistema
     * @return Lista de todos los roles convertidos a DTO
     */
    @Override
    public List<RolDTO> listarRoles() {
        return rolRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca y lista roles que coincidan con el nombre especificado
     * @param nombre nombre o parte del nombre del rol a buscar
     * @return Lista de roles que coinciden con el criterio de búsqueda
     */
    @Override
    public List<RolDTO> listarRolesPorNombre(String nombre) {
        List<Rol> roles = rolRepository.buscarPorNombreFlexible(nombre.trim());

        List<RolDTO> lista = roles.stream().map(this::toDTO).collect(Collectors.toList());

        // Usuario logueado
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Rol");
        movimiento.setDetalleMovimiento("Se buscaron roles por nombre: " + nombre);

        // 🚨 Si se encuentra al menos un rol, se guarda el primero como entidad afectada
        if (!roles.isEmpty()) {
            Rol r = roles.get(0);
            movimiento.setIdEntidadAfectada(String.valueOf(r.getIdRol()));
            movimiento.setNombreEntidadAfectada(r.getNombreRol());
        }

        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);
        return lista;
    }

    /**
     * Obtiene un rol específico según su ID
     * @param id identificador único del rol a buscar
     * @return RolDTO con la información del rol encontrado
     */
    @Override
    public RolDTO obtenerRolPorId(Integer id) {
        Rol rol = rolRepository.findByIdRol(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + id));

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Rol");
        movimiento.setIdEntidadAfectada(String.valueOf(rol.getIdRol()));
        movimiento.setNombreEntidadAfectada(rol.getNombreRol());
        movimiento.setDetalleMovimiento("Se consultó el rol por ID: " + id);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(rol);
    }

    /**
     * Actualiza la información de un rol existente
     * @param id identificador del rol a actualizar
     * @param rolDTO nueva información para actualizar el rol
     * @return RolDTO con la información actualizada del rol
     */
    @Override
    public RolDTO actualizarRol(Integer id, RolDTO rolDTO) {
        Rol rolExistente = rolRepository.findByIdRol(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + id));

        List<Rol> existentes = rolRepository.findAll();
        for (Rol r : existentes) {
            if (!r.getIdRol().equals(id) &&
                    r.getNombreRol().equalsIgnoreCase(rolDTO.getNombreRol())) {
                throw new DuplicadoException("Ya existe otro rol con ese nombre.");
            }
        }

        String nombreAnterior = rolExistente.getNombreRol();
        String descripcionAnterior = rolExistente.getDescripcion();

        rolExistente.setNombreRol(rolDTO.getNombreRol());
        rolExistente.setDescripcion(rolDTO.getDescripcion());

        if (rolDTO.getIdPerfil() != null) {
            Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil()));
            rolExistente.setPerfil(perfil);
        }

        Rol rolActualizado = rolRepository.save(rolExistente);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        StringBuilder detalle = new StringBuilder("Se actualizó el rol: " + rolActualizado.getNombreRol());

        if (!nombreAnterior.equals(rolActualizado.getNombreRol())) {
            detalle.append(" | Nombre: ").append(nombreAnterior).append(" → ").append(rolActualizado.getNombreRol());
        }

        if (!descripcionAnterior.equals(rolActualizado.getDescripcion())) {
            detalle.append(" | Descripción: ").append(descripcionAnterior).append(" → ").append(rolActualizado.getDescripcion());
        }

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Rol");
        movimiento.setIdEntidadAfectada(String.valueOf(rolActualizado.getIdRol()));
        movimiento.setNombreEntidadAfectada(rolActualizado.getNombreRol());
        movimiento.setDetalleMovimiento(detalle.toString());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(rolActualizado);
    }

    /**
     * Elimina un rol del sistema por su ID
     * @param id identificador del rol a eliminar
     */
    @Override
    public void eliminarRol(Integer id) {
        Rol rol = rolRepository.findByIdRol(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol con ID " + id + " no encontrado"));

        String nombreRol = rol.getNombreRol();

        rolRepository.delete(rol);

        // 🔐 Usuario loeueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        String detalle = "Se eliminó el rol: " + nombreRol + " con ID " + id;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("Rol");
        movimiento.setIdEntidadAfectada(String.valueOf(id));
        movimiento.setNombreEntidadAfectada(nombreRol);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

    }

    /**
     * Convierte un objeto Rol a su representación DTO (Data Transfer Object).
     * @param rol Entidad Rol que se va a convertir
     * @return RolDTO Objeto DTO que contiene los datos del rol
     * @throws IllegalArgumentException si el parámetro rol es nulo
     */
    private RolDTO toDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());
        dto.setDescripcion(rol.getDescripcion());

        if (rol.getPerfil() != null) {
            dto.setIdPerfil(rol.getPerfil().getIdPerfil());
            dto.setNombrePerfil(rol.getPerfil().getNombrePerfil());
        }

        return dto;
    }
}
