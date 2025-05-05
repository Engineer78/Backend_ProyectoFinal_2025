package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

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
                usuario.getContrasenia(),
                nombreRol
        );
    }

    /**
     * Crea un nuevo empleado en la base de datos.
     * @param dto Objeto EmpleadoDTO con los datos del empleado a buscar.
     * @return
     */
    @Override
    public EmpleadoDTO crear(EmpleadoDTO dto) {
        // Verifica si el nombre de usuario ya existe
        if (usuarioRepository.existsByNombreUsuario(dto.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Obtiene el rol desde la base de datos
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Crea el usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(dto.getNombreUsuario());
        nuevoUsuario.setContrasenia(dto.getContrasena());
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
                usuarioGuardado.getContrasenia(),
                rol.getNombreRol()
        );
    }

    /**
     * Busca un empleado por número de documento.
     * @param numeroDocumento Objeto EmpleadoDTO con los datos del empleado a buscar.
     * @return
     */
    @Override
    public EmpleadoDTO buscarEmpleadoPorDocumento(String numeroDocumento) {
        Empleado empleado = empleadoRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = empleado.getUsuario();
        int idRol = usuario.getRol() != null ? usuario.getRol().getIdRol() : 0;
        String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombreRol() : null;

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
                usuario.getContrasenia(),
                nombreRol
        );
    }
    /**
     * Actualiza los datos de un empleado existente por ID.
     * @param idEmpleado
     * @param dto
     * @return
     */
    @Override
    public EmpleadoDTO actualizarEmpleado(int idEmpleado, EmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = empleado.getUsuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasenia(dto.getContrasena());
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
                usuario.getContrasenia(),
                nombreRol
        );
    }

    /**
     * Actualiza los datos existente de un empleado por número de documento.
     * @param numeroDocumento
     * @param dto
     * @return
     */
    @Override
    public EmpleadoDTO actualizarEmpleadoPorDocumento(String numeroDocumento, EmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = empleado.getUsuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasenia(dto.getContrasena());
        usuarioRepository.save(usuario);

        empleado.setNombres(dto.getNombres());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        empleado.setApellidoMaterno(dto.getApellidoMaterno());
        empleado.setTelefonoMovil(dto.getTelefonoMovil());
        empleado.setDireccionResidencia(dto.getDireccionResidencia());
        empleado.setContactoEmergencia(dto.getContactoEmergencia());
        empleado.setTelefonoContacto(dto.getTelefonoContacto());

        Empleado actualizado = empleadoRepository.save(empleado);
        Rol rol = usuario.getRol();

        return new EmpleadoDTO(
                actualizado.getIdEmpleado(),
                usuario.getIdUsuario(),
                rol != null ? rol.getIdRol() : 0,
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
                usuario.getContrasenia(),
                rol != null ? rol.getNombreRol() : null
        );
    }

    /**
     * Lista todos los empleados existentes.
     * @return
     */
    @Override
    public List<EmpleadoDTO> listarEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados.stream().map(emp -> {
            Usuario usuario = emp.getUsuario();
            int idRol = usuario.getRol() != null ? usuario.getRol().getIdRol() : 0;
            String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombreRol() : null;

            return new EmpleadoDTO(
                    emp.getIdEmpleado(),
                    usuario.getIdUsuario(),
                    idRol,
                    emp.getNumeroDocumento(),
                    emp.getTipoDocumento() != null ? emp.getTipoDocumento().getIdTipoDocumento() : 0,
                    emp.getTipoDocumento() != null ? emp.getTipoDocumento().getNombre() : null,
                    emp.getNombres(),
                    emp.getApellidoPaterno(),
                    emp.getApellidoMaterno(),
                    emp.getTelefonoMovil(),
                    emp.getDireccionResidencia(),
                    emp.getContactoEmergencia(),
                    emp.getTelefonoContacto(),
                    usuario.getNombreUsuario(),
                    usuario.getContrasenia(),
                    nombreRol
            );
        }).collect(Collectors.toList());
    }

    /**
     * Elimina un empleado existente.
     * @param idEmpleado
     */
    @Override
    public void eliminarEmpleado(int idEmpleado) {
        if (!empleadoRepository.existsById(idEmpleado)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        empleadoRepository.deleteById(idEmpleado);
    }
}
