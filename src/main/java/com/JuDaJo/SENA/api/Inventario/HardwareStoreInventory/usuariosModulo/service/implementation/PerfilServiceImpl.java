package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PerfilDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    @Autowired
    public PerfilServiceImpl(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Crea un nuevo perfil en la base de datos a partir de un objeto PerfilDTO.
     *
     * @param perfilDTO Objeto con los datos del nuevo perfil.
     * @return El perfil creado como DTO, incluyendo su ID generado.
     */
    @Override
    public PerfilDTO crearPerfil(PerfilDTO perfilDTO) {
        Perfil perfil = new Perfil();
        perfil.setNombrePerfil(perfilDTO.getNombrePerfil());
        perfil.setDescripcion(perfilDTO.getDescripcion());

        Perfil perfilGuardado = perfilRepository.save(perfil);

        return toDTO(perfilGuardado);
    }

    /**
     * Convierte una entidad Perfil a un objeto PerfilDTO.
     *
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
     *
     * @return Lista de objetos PerfilDTO.
     */
    @Override
    public List<PerfilDTO> listarPerfiles() {
        return perfilRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un perfil por su ID.
     *
     * @param id ID del perfil a buscar.
     * @return PerfilDTO correspondiente al ID.
     * @throws NoSuchElementException si no se encuentra el perfil.
     */
    @Override
    public PerfilDTO obtenerPerfilPorId(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Perfil no encontrado con ID: " + id));
        return toDTO(perfil);
    }

}
