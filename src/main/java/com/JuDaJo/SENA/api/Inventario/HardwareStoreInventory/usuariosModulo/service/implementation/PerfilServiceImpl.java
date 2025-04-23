package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

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
     * Crea un nuevo perfil a partir de los datos proporcionados.
     *
     * @param perfilDTO El objeto con los datos del perfil a crear.
     * @return El PerfilDTO creado, con su ID asignado.
     */
    @Override
    public PerfilDTO crearPerfil(PerfilDTO perfilDTO) {
        // Convertir PerfilDTO a Perfil
        Perfil perfil = new Perfil();
        perfil.setNombre(perfilDTO.getNombre());
        perfil.setDescripcion(perfilDTO.getDescripcion());

        // Guardar el perfil en la base de datos
        Perfil perfilGuardado = perfilRepository.save(perfil);

        // Devolver el PerfilDTO con la información guardada (incluido el ID)
        return new PerfilDTO(perfilGuardado);
    }
}
