package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PerfilDTO;

/**
 * Interfaz para el servicio de perfiles. Define las operaciones básicas
 * relacionadas con la gestión de perfiles en el sistema.
 */
public interface PerfilService {

    /**
     * Crea un nuevo perfil en el sistema.
     *
     * @param perfilDTO El objeto con los datos del perfil a crear.
     * @return El PerfilDTO creado, con su ID asignado.
     */
    PerfilDTO crearPerfil(PerfilDTO perfilDTO);

    /**
     * Obtiene un perfil por su ID.
     *
     * @param id El ID del perfil a obtener.
     * @return El PerfilDTO correspondiente al ID, o null si no se encuentra.
     */
    PerfilDTO obtenerPerfilPorId(Long id);
}
