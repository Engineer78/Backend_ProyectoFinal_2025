package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioChangePasswordDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;


public interface UsuarioService {

    UsuarioDTO crear(UsuarioDTO usuarioDTO);

    void cambiarContrasena(String nombreUsuario, UsuarioChangePasswordDTO dto);

}
