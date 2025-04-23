package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.UsuarioService;

import java.util.List;

public class UserServiceImpl implements UsuarioService {
    @Override
    public UsuarioDTO encontrarPorId(int id) {
        return null;
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return List.of();
    }

    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        return null;
    }

    @Override
    public UsuarioDTO actualizar(int id, UsuarioDTO usuarioDTO) {
        return null;
    }

    @Override
    public void eliminar(int id) {

    }
}
