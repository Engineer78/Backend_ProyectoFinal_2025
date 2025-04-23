package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

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
     * Actualiza los datos de un empleado existente.
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
        usuario.setContrasena(dto.getContraseña());
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
                    emp.getNombres(),
                    emp.getApellidoPaterno(),
                    emp.getApellidoMaterno(),
                    emp.getTelefonoMovil(),
                    emp.getDireccionResidencia(),
                    emp.getContactoEmergencia(),
                    emp.getTelefonoContacto(),
                    usuario.getNombreUsuario(),
                    usuario.getContrasena(),
                    nombreRol
            );
        }).collect(Collectors.toList());
    }
}
