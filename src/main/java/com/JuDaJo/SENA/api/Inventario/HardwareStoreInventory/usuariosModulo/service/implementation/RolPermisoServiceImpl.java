package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolPermisoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Permiso;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.RolPermiso;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PermisoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolPermisoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolPermisoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    @Autowired
    private RolPermisoRepository rolPermisoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroMovimientoService registroMovimientoService;

    @Override
    public RolPermisoDTO crearRolPermiso(RolPermisoDTO dto) {
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado"));

        Permiso permiso = permisoRepository.findById(dto.getIdPermiso())
                .orElseThrow(() -> new RecursoNoEncontradoException("Permiso no encontrado"));

        RolPermiso relacion = new RolPermiso();
        relacion.setRol(rol);
        relacion.setPermiso(permiso);

        RolPermiso guardado = rolPermisoRepository.save(relacion);

        registrarMovimiento("CREAR", guardado.getIdRolPermiso(), "Rol-Permiso", "Asociado permiso '"
                + permiso.getNombrePermiso() + "' al rol '" + rol.getNombreRol() + "'");

        return toDTO(guardado);
    }

    @Override
    public List<RolPermisoDTO> listarRelaciones() {
        List<RolPermisoDTO> lista = rolPermisoRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());

        registrarMovimiento("CONSULTAR", 0, "Rol-Permiso", "Se consultaron todas las relaciones rol-permiso");

        return lista;
    }

    @Override
    public List<RolPermisoDTO> listarPorRol(int idRol) {
        List<RolPermisoDTO> lista = rolPermisoRepository.findByRol_IdRol(idRol)
                .stream().map(this::toDTO).collect(Collectors.toList());

        registrarMovimiento("CONSULTAR", idRol, "Rol-Permiso", "Se consultaron permisos asociados al rol ID " + idRol);

        return lista;
    }

    @Override
    public void eliminarRolPermiso(int idRolPermiso) {
        RolPermiso relacion = rolPermisoRepository.findById(idRolPermiso)
                .orElseThrow(() -> new RecursoNoEncontradoException("Relación Rol-Permiso no encontrada"));

        rolPermisoRepository.delete(relacion);

        registrarMovimiento("ELIMINAR", idRolPermiso, "Rol-Permiso", "Se eliminó la relación entre el rol '"
                + relacion.getRol().getNombreRol() + "' y el permiso '"
                + relacion.getPermiso().getNombrePermiso() + "'");
    }

    @Override
    public boolean existeRolPermiso(Integer idRol, Integer idPermiso) {
        return rolPermisoRepository.existsByRolIdRolAndPermisoIdPermiso(idRol, idPermiso);
    }

    @Override
    public void eliminarRolPermisoPorRolYPermiso(Integer idRol, Integer idPermiso) {
        RolPermiso relacion = rolPermisoRepository
                .findAll()
                .stream()
                .filter(rp -> rp.getRol().getIdRol() == idRol && rp.getPermiso().getIdPermiso() == idPermiso)
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("Relación no encontrada"));

        rolPermisoRepository.delete(relacion);

        registrarMovimiento("ELIMINAR", relacion.getIdRolPermiso(), "Rol-Permiso",
                "Se eliminó relación por ID de Rol (" + idRol + ") y Permiso (" + idPermiso + ")");
    }

    @Override
    @Transactional
    public void actualizarPermisosPorRol(int idRol, List<Integer> idsPermisos) {
        // 1. Validar si el rol existe
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado"));

        // 2. Eliminar todos los permisos actuales del rol
        rolPermisoRepository.deleteByRolId(idRol);

        // 3. Agregar los permisos seleccionados
        for (Integer idPermiso : idsPermisos) {
            Permiso permiso = permisoRepository.findById(idPermiso)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Permiso no encontrado: " + idPermiso));

            RolPermiso nuevoRolPermiso = new RolPermiso();
            nuevoRolPermiso.setRol(rol);
            nuevoRolPermiso.setPermiso(permiso);

            rolPermisoRepository.save(nuevoRolPermiso);
        }
    }


    private RolPermisoDTO toDTO(RolPermiso entidad) {
        return new RolPermisoDTO(
                entidad.getIdRolPermiso(),
                entidad.getRol().getIdRol(),
                entidad.getRol().getNombreRol(),
                entidad.getPermiso().getIdPermiso(),
                entidad.getPermiso().getNombrePermiso()
        );
    }

    private void registrarMovimiento(String tipo, int id, String entidad, String detalle) {
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Empleado responsable = usuarioRepository.findByNombreUsuario(usuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"))
                .getEmpleado();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.valueOf(tipo));
        movimiento.setEntidadAfectada(entidad);
        movimiento.setIdEntidadAfectada(String.valueOf(id));
        movimiento.setNombreEntidadAfectada(entidad);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(responsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(responsable.getNombres() + " " + responsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);
    }
}

