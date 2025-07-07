package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PermisoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Permiso;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PermisoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermisoServiceImpl implements PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public PermisoDTO crearPermiso(PermisoDTO dto) {
        Permiso permiso = new Permiso(
                null,  // El ID se generará automáticamente
                dto.getNombrePermiso(),
                dto.getDescripcion()
        );
        permiso.setNombrePermiso(dto.getNombrePermiso());
        permiso.setDescripcion(dto.getDescripcion());

        Permiso guardado = permisoRepository.save(permiso);
        return toDTO(guardado);
    }

    @Override
    public List<PermisoDTO> listarPermisos() {
        return permisoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermisoDTO obtenerPermisoPorId(Integer id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Permiso no encontrado con ID: " + id));
        return toDTO(permiso);
    }

    @Override
    public PermisoDTO buscarPermisoPorNombre(String nombre) {
        Permiso permiso = permisoRepository.findByNombrePermiso(nombre)
                .orElseThrow(() -> new RecursoNoEncontradoException("Permiso no encontrado con nombre: " + nombre));
        return toDTO(permiso);
    }

    @Override
    public PermisoDTO actualizarPermiso(Integer id, PermisoDTO dto) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Permiso no encontrado con ID: " + id));

        permiso.setNombrePermiso(dto.getNombrePermiso());
        permiso.setDescripcion(dto.getDescripcion());
        Permiso actualizado = permisoRepository.save(permiso);

        return toDTO(actualizado);
    }

    @Override
    public void eliminarPermiso(Integer id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Permiso no encontrado con ID: " + id));
        permisoRepository.delete(permiso);
    }

    private PermisoDTO toDTO(Permiso permiso) {
        return new PermisoDTO(
                permiso.getIdPermiso(),
                permiso.getNombrePermiso(),
                permiso.getDescripcion()
        );
    }

    @Override
    public boolean existePorNombre(String nombrePermiso) {
        return permisoRepository.findByNombrePermiso(nombrePermiso).isPresent();
    }
}

