package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UsuarioService {
    /*
    * Metodos Abstractos
    * */
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
