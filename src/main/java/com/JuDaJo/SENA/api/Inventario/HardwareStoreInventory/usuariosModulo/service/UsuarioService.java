package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO encontrarPorId(int id);
    List<UsuarioDTO> listarTodos();
    UsuarioDTO crear(UsuarioDTO usuarioDTO);
    UsuarioDTO actualizar(int id, UsuarioDTO usuarioDTO);
    void eliminar(int id);
}
