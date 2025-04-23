package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PerfilDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para los perfiles. Contiene la lógica de negocio
 * para crear, obtener, actualizar y eliminar perfiles.
 */
@Service
public class PerfilServiceImpl {

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

}
