package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolDTO;
import java.util.List;

public interface RolService {
    RolDTO crearRol(RolDTO rolDTO);
    List<RolDTO> listarRoles();
    List<RolDTO> listarRolesPorNombre(String nombre);
    RolDTO obtenerRolPorId(Integer id);
    RolDTO actualizarRol(Integer id, RolDTO rolDTO);
    void eliminarRol(Integer id);
}
