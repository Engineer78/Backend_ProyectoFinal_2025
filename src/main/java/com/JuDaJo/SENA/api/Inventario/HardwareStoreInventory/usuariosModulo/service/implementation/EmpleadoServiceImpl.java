package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.TipoDocumento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.TipoDocumentoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.EmpleadoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.EmpleadoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.EmpleadoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    /**
     * Se inyecta la dependencia para el repositorio de EmpleadoRepository.
     */
    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Se inyecta la dependecia para el servicio de UsuarioRepository..
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Se inyecta la dependencia para el repositorio de RolRepository.
     */
    @Autowired
    private RolRepository rolRepository;

    /**
     * Se inyecta la dependencia para el repositorio de TipoDocumentoRepository.
     */
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    /**
     * Se inyecta la dependencia para el servicio de PasswordEncoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Se inyecta la dependencia para el movimiento de inventario.
     */
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    /**
     * Se inyecta la dependencia para el registro de movimiento.
     */
    @Autowired
    private RegistroMovimientoService registroMovimientoService;


    /**
     * Busca un empleado por su ID.
     * @param idEmpleado El identificador único del empleado a buscar
     * @return EmpleadoDTO objeto con los datos del empleado encontrado
     * @throws RuntimeException si el empleado no es encontrado
     */
    @Override
    public EmpleadoDTO buscarPorId(int idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = empleado.getUsuario();

        /**
         * Se crea la variable idRol y nombreRol para guardar el id y nombre del rol del usuario.
         */
        int idRol = usuario.getRol() != null ? usuario.getRol().getIdRol() : 0;
        String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombreRol() : null;

        // Obtener usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // // Armar mensaje de detalle
        String detalle = "Se consultó el empleado con ID interno " + empleado.getIdEmpleado();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Empleado");
        movimiento.setIdEntidadAfectada(String.valueOf(empleado.getIdEmpleado()));
        movimiento.setNombreEntidadAfectada(empleado.getNombres() + " " + empleado.getApellidoPaterno());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new EmpleadoDTO(
                empleado.getIdEmpleado(),
                usuario.getIdUsuario(),
                idRol,
                empleado.getNumeroDocumento(),
                empleado.getTipoDocumento() != null ? empleado.getTipoDocumento().getIdTipoDocumento() : 0,
                empleado.getTipoDocumento() != null ? empleado.getTipoDocumento().getNombre() : null,
                empleado.getNombres(),
                empleado.getApellidoPaterno(),
                empleado.getApellidoMaterno(),
                empleado.getTelefonoMovil(),
                empleado.getDireccionResidencia(),
                empleado.getContactoEmergencia(),
                empleado.getTelefonoContacto(),
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                nombreRol
        );
    }

    /**
     * Crea un nuevo empleado en la base de datos.
     * @param dto Objeto EmpleadoDTO con los datos del empleado a buscar.
     */
    @Override
    public EmpleadoDTO crear(EmpleadoDTO dto) {
        // 🧐 Validación: verificar si ya existe un número de documento
        if (empleadoRepository.findByNumeroDocumento(dto.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("Ya existe un empleado con ese número de documento");
        }

        // 🧐 Validación: nombre de usuario duplicado (ya existente)
        if (usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Obtiene el rol desde la base de datos
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Crea el usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(dto.getNombreUsuario());
        // 🔐 Encriptar contraseña con BCrypt antes de guardar
        String passwordEncriptada = passwordEncoder.encode(dto.getContrasena());
        nuevoUsuario.setContrasena(passwordEncriptada);
        nuevoUsuario.setRol(rol);
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);


        // Obtiene el tipo de documento desde la base de datos
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(dto.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        // Crea el empleado y lo asocia al usuario
        Empleado empleado = new Empleado();
        empleado.setNumeroDocumento(dto.getNumeroDocumento());
        empleado.setTipoDocumento(tipoDocumento);
        empleado.setNombres(dto.getNombres());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        empleado.setApellidoMaterno(dto.getApellidoMaterno());
        empleado.setTelefonoMovil(dto.getTelefonoMovil());
        empleado.setDireccionResidencia(dto.getDireccionResidencia());
        empleado.setContactoEmergencia(dto.getContactoEmergencia());
        empleado.setTelefonoContacto(dto.getTelefonoContacto());
        empleado.setUsuario(usuarioGuardado);

        Empleado guardado = empleadoRepository.save(empleado);

        // 🔽 Aquí se registra el movimiento del inventario,
        // Obteniendo el usuario logueado desde el token
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Arma el DTO de trazabilidad
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("Empleado");
        movimiento.setIdEntidadAfectada(String.valueOf(guardado.getIdEmpleado()));
        movimiento.setNombreEntidadAfectada(guardado.getNombres() + " " + guardado.getApellidoPaterno());
        movimiento.setDetalleMovimiento("Se creó el empleado con documento " + guardado.getNumeroDocumento());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new EmpleadoDTO(
                guardado.getIdEmpleado(),
                usuarioGuardado.getIdUsuario(),
                rol.getIdRol(),
                guardado.getNumeroDocumento(),
                tipoDocumento.getIdTipoDocumento(),
                tipoDocumento.getNombre(),
                guardado.getNombres(),
                guardado.getApellidoPaterno(),
                guardado.getApellidoMaterno(),
                guardado.getTelefonoMovil(),
                guardado.getDireccionResidencia(),
                guardado.getContactoEmergencia(),
                guardado.getTelefonoContacto(),
                usuarioGuardado.getNombreUsuario(),
                null, // 🔐 No retornar la contraseña en la respuesta
                rol.getNombreRol()
        );
    }

    /**
     * Busca un empleado por número de documento.
     * @param numeroDocumento Objeto EmpleadoDTO con los datos del empleado a buscar.
     */
    @Override
    public EmpleadoDTO buscarEmpleadoPorDocumento(String numeroDocumento) {
        Empleado empleado = empleadoRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con documento: " + numeroDocumento));

        Usuario usuario = empleado.getUsuario();
        int idRol = usuario.getRol() != null ? usuario.getRol().getIdRol() : 0;
        String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombreRol() : null;

        // Obtener usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // // Armar mensaje de detalle
        String detalle = "Se consultó el empleado con documento " + empleado.getNumeroDocumento();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Empleado");
        movimiento.setIdEntidadAfectada(String.valueOf(empleado.getIdEmpleado()));
        movimiento.setNombreEntidadAfectada(empleado.getNombres() + " " + empleado.getApellidoPaterno());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new EmpleadoDTO(
                empleado.getIdEmpleado(),
                usuario.getIdUsuario(),
                idRol,
                empleado.getNumeroDocumento(),
                empleado.getTipoDocumento() != null ? empleado.getTipoDocumento().getIdTipoDocumento() : 0,
                empleado.getTipoDocumento() != null ? empleado.getTipoDocumento().getNombre() : null,
                empleado.getNombres(),
                empleado.getApellidoPaterno(),
                empleado.getApellidoMaterno(),
                empleado.getTelefonoMovil(),
                empleado.getDireccionResidencia(),
                empleado.getContactoEmergencia(),
                empleado.getTelefonoContacto(),
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                nombreRol
        );
    }

    /**
     * Actualiza los datos de un empleado existente por ID.
     * @param: idEmpleado
     * @param: dto
     */
    @Override
    public EmpleadoDTO actualizarEmpleado(int idEmpleado, EmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con ID: " + idEmpleado));

        // 🧐 Verificar si ya existe otro empleado con ese número de documento
        empleadoRepository.findByNumeroDocumento(dto.getNumeroDocumento()).ifPresent(otro -> {
            if (otro.getIdEmpleado() != empleado.getIdEmpleado()) {
                throw new DuplicadoException("Ya existe otro empleado con ese número de documento.");
            }
        });

        // 🧐 Verificar si ya existe otro usuario con ese nombre de usuario
        usuarioRepository.findAll().forEach(otroUsuario -> {
            if (otroUsuario.getNombreUsuario().equals(dto.getNombreUsuario()) &&
                    otroUsuario.getIdUsuario() != empleado.getUsuario().getIdUsuario()) {
                throw new DuplicadoException("Ya existe otro usuario con ese nombre de usuario.");
            }
        });

        // 🧐 Validar si el número de documento ya pertenece a otro empleado
        empleadoRepository.findByNumeroDocumento(dto.getNumeroDocumento())
                .ifPresent(otro -> {
                    if (otro.getIdEmpleado() != idEmpleado) {
                        throw new RuntimeException("El número de documento ya está en uso por otro empleado");
                    }
                });

        Usuario usuario = empleado.getUsuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        // 🔐 Encriptar contraseña con BCrypt antes de guardar
        String passwordEncriptada = passwordEncoder.encode(dto.getContrasena());
        usuario.setContrasena(passwordEncriptada);
        usuarioRepository.save(usuario);

        empleado.setNumeroDocumento(dto.getNumeroDocumento());
        empleado.setNombres(dto.getNombres());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        empleado.setApellidoMaterno(dto.getApellidoMaterno());
        empleado.setTelefonoMovil(dto.getTelefonoMovil());
        empleado.setDireccionResidencia(dto.getDireccionResidencia());
        empleado.setContactoEmergencia(dto.getContactoEmergencia());
        empleado.setTelefonoContacto(dto.getTelefonoContacto());

        Empleado actualizado = empleadoRepository.save(empleado);

        int idRol = usuario.getRol() != null ? usuario.getRol().getIdRol() : 0;
        String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombreRol() : null;

        // Obtener el usuario autenticado actual desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar mensaje de detalle
        String detalle = "Se actualizó el empleado con ID interno " + actualizado.getIdEmpleado();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Empleado");
        movimiento.setIdEntidadAfectada(String.valueOf(actualizado.getIdEmpleado()));
        movimiento.setNombreEntidadAfectada(actualizado.getNombres() + " " + actualizado.getApellidoPaterno());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new EmpleadoDTO(
                actualizado.getIdEmpleado(),
                usuario.getIdUsuario(),
                idRol,
                actualizado.getNumeroDocumento(),
                actualizado.getTipoDocumento() != null ? actualizado.getTipoDocumento().getIdTipoDocumento() : 0,
                actualizado.getTipoDocumento() != null ? actualizado.getTipoDocumento().getNombre() : null,
                actualizado.getNombres(),
                actualizado.getApellidoPaterno(),
                actualizado.getApellidoMaterno(),
                actualizado.getTelefonoMovil(),
                actualizado.getDireccionResidencia(),
                actualizado.getContactoEmergencia(),
                actualizado.getTelefonoContacto(),
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                nombreRol
        );
    }

    /**
     * Actualiza los datos existente de un empleado por número de documento.
     * @param: numeroDocumento
     * @param: dto
     */
    @Override
    public EmpleadoDTO actualizarEmpleadoPorDocumento(String numeroDocumento, EmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con documento: " + numeroDocumento));

        // Validar si otro empleado ya tiene ese número de documento
        empleadoRepository.findByNumeroDocumento(dto.getNumeroDocumento()).ifPresent(otro -> {
            if (otro.getIdEmpleado() != empleado.getIdEmpleado()) {
                throw new DuplicadoException("Ya existe otro empleado con ese número de documento.");
            }
        });

        // Validar si otro usuario ya tiene ese nombre de usuario
        usuarioRepository.findAll().forEach(otroUsuario -> {
            if (otroUsuario.getNombreUsuario().equals(dto.getNombreUsuario()) &&
                    otroUsuario.getIdUsuario() != empleado.getUsuario().getIdUsuario()) {
                throw new DuplicadoException("Ya existe otro usuario con ese nombre.");
            }
        });

        Usuario usuario = empleado.getUsuario();

        // Validar si cambió el nombre de usuario
        if (!usuario.getNombreUsuario().equals(dto.getNombreUsuario())) {
            if (usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
                throw new DuplicadoException("El nombre de usuario ya está en uso por otro empleado.");
            }
            usuario.setNombreUsuario(dto.getNombreUsuario());
        }

        // 🔐 Actualizar contraseña solo si fue ingresada
        if (dto.getContrasena() != null && !dto.getContrasena().trim().isEmpty()) {
            String passwordEncriptada = passwordEncoder.encode(dto.getContrasena());
            usuario.setContrasena(passwordEncriptada);
        }

        // ✅ Actualizar rol
        Rol nuevoRol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + dto.getIdRol()));
        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);

        // ✅ Actualizar tipo de documento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(dto.getIdTipoDocumento())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento no encontrado con ID: " + dto.getIdTipoDocumento()));
        empleado.setTipoDocumento(tipoDocumento);

        // Actualizar campos del empleado
        empleado.setNumeroDocumento(dto.getNumeroDocumento());
        empleado.setNombres(dto.getNombres());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        empleado.setApellidoMaterno(dto.getApellidoMaterno());
        empleado.setTelefonoMovil(dto.getTelefonoMovil());
        empleado.setDireccionResidencia(dto.getDireccionResidencia());
        empleado.setContactoEmergencia(dto.getContactoEmergencia());
        empleado.setTelefonoContacto(dto.getTelefonoContacto());

        Empleado actualizado = empleadoRepository.save(empleado);
        Rol rol = usuario.getRol(); // ya está actualizado

        // Obtener el usuario autenticado actual desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar mensaje de detalle
        String detalle = "Se actualizó el empleado con número de documento " + actualizado.getNumeroDocumento();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Empleado");
        movimiento.setIdEntidadAfectada(String.valueOf(actualizado.getIdEmpleado()));
        movimiento.setNombreEntidadAfectada(actualizado.getNombres() + " " + actualizado.getApellidoPaterno());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return new EmpleadoDTO(
                actualizado.getIdEmpleado(),
                usuario.getIdUsuario(),
                rol != null ? rol.getIdRol() : 0,
                actualizado.getNumeroDocumento(),
                tipoDocumento != null ? tipoDocumento.getIdTipoDocumento() : 0,
                tipoDocumento != null ? tipoDocumento.getNombre() : null,
                actualizado.getNombres(),
                actualizado.getApellidoPaterno(),
                actualizado.getApellidoMaterno(),
                actualizado.getTelefonoMovil(),
                actualizado.getDireccionResidencia(),
                actualizado.getContactoEmergencia(),
                actualizado.getTelefonoContacto(),
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                rol != null ? rol.getNombreRol() : null
        );
    }

    /**
     * Lista todos los empleados existentes.
     */
    @Override
    public List<EmpleadoDTO> listarEmpleados() {
        return empleadoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Elimina un empleado existente, no sin antes capturar la información.
     * @param: idEmpleado
     */
    @Override
    public void eliminarEmpleado(int idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado"));

        // Guardar datos antes de eliminar
        String nombreCompleto = empleado.getNombres() + " " + empleado.getApellidoPaterno();
        String numeroDocumento = empleado.getNumeroDocumento();

        empleadoRepository.deleteById(idEmpleado);

        // Obtener usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // Armar detalle de movimiento
        String detalle = "Se eliminó el empleado " + nombreCompleto + " con documento " + numeroDocumento;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("Empleado");
        movimiento.setIdEntidadAfectada(String.valueOf(idEmpleado));
        movimiento.setNombreEntidadAfectada(nombreCompleto);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);
    }

    private EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setIdEmpleado(empleado.getIdEmpleado());
        dto.setNumeroDocumento(empleado.getNumeroDocumento());
        dto.setNombres(empleado.getNombres());
        dto.setApellidoPaterno(empleado.getApellidoPaterno());
        dto.setApellidoMaterno(empleado.getApellidoMaterno());
        dto.setTelefonoMovil(empleado.getTelefonoMovil());
        dto.setDireccionResidencia(empleado.getDireccionResidencia());
        dto.setContactoEmergencia(empleado.getContactoEmergencia());
        dto.setTelefonoContacto(empleado.getTelefonoContacto());

        // Asignación de relaciones si las tienes
        if (empleado.getUsuario() != null) {
            dto.setIdUsuario(empleado.getUsuario().getIdUsuario());
            dto.setNombreUsuario(empleado.getUsuario().getNombreUsuario());
            if (empleado.getUsuario().getRol() != null) {
                dto.setIdRol(empleado.getUsuario().getRol().getIdRol());
                dto.setNombreRol(empleado.getUsuario().getRol().getNombreRol());
            }
        }

        if (empleado.getTipoDocumento() != null) {
            dto.setIdTipoDocumento(empleado.getTipoDocumento().getIdTipoDocumento());
            dto.setNombreTipoDocumento(empleado.getTipoDocumento().getNombre());
        }

        return dto;
    }


}
