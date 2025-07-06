package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.PermisoDTO;

import java.util.List;

public interface PermisoService {

    PermisoDTO crearPermiso(PermisoDTO permisoDTO);

    List<PermisoDTO> listarPermisos();

    PermisoDTO obtenerPermisoPorId(Integer id);

    PermisoDTO buscarPermisoPorNombre(String nombre);

    PermisoDTO actualizarPermiso(Integer id, PermisoDTO permisoDTO);

    void eliminarPermiso(Integer id);

    boolean existePorNombre(String nombrePermiso);

}

