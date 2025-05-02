package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final PerfilRepository perfilRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository, PerfilRepository perfilRepository) {
        this.rolRepository = rolRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public RolDTO crearRol(RolDTO rolDTO) {
        Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil()));

        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setPerfil(perfil);

        Rol rolGuardado = rolRepository.save(rol);

        return toDTO(rolGuardado);
    }

    /**
     * Convierte una entidad Rol a un DTO RolDTO.
     *
     * @param rol la entidad Rol que se desea convertir
     * @return un RolDTO con los datos de la entidad
     */
    private RolDTO convertirARolDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());

        // Asegúrate de que rol.getPerfil() no sea null antes de acceder a sus datos
        if (rol.getPerfil() != null) {
            dto.setIdPerfil(rol.getPerfil().getIdPerfil());
            dto.setNombrePerfil(rol.getPerfil().getNombrePerfil());
        }

        return dto;
    }

    public List<RolDTO> listarRoles() {
        return rolRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<RolDTO> listarRolesPorNombre(String nombre) {
        List<Rol> roles = rolRepository.findByNombreRolContainingIgnoreCase(nombre);
        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolDTO obtenerRolPorId(Integer id) {
        Rol rol = rolRepository.findByIdRol(id)
                .orElseThrow(() -> new NoSuchElementException("Rol no encontrado con ID: " + id));
        return toDTO(rol);
    }

    public RolDTO guardarRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());

        if (rolDTO.getIdPerfil() != null) {
            Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                    .orElseThrow(() -> new NoSuchElementException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil()));
            rol.setPerfil(perfil);
        }

        Rol rolGuardado = rolRepository.save(rol);
        return toDTO(rolGuardado);
    }

    public RolDTO actualizarRol(Integer id, RolDTO rolDTO) {
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rol no encontrado con ID: " + id));

        rolExistente.setNombreRol(rolDTO.getNombreRol());
        rolExistente.setDescripcion(rolDTO.getDescripcion());

        if (rolDTO.getIdPerfil() != null) {
            Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                    .orElseThrow(() -> new NoSuchElementException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil()));
            rolExistente.setPerfil(perfil);
        }

        Rol rolActualizado = rolRepository.save(rolExistente);
        return toDTO(rolActualizado);
    }

    public void eliminarRol(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol con ID " + id + " no encontrado"));
        rolRepository.delete(rol);
    }

    /**
     * Convierte una entidad Rol en un RolDTO.
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
