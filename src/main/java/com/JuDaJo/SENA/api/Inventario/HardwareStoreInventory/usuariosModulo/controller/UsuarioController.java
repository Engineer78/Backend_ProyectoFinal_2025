package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.controller;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.RestController;

/*
* Controlador Rest para gestionar la creación de usuarios en la entidad Empleado
* Proporcionará la funcionalidad: CrearUsuarios
* */
@RestController
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    /*
    * Constructor para inicializar los repositorios necesarios
    * @param usuarioRepository
    * @param rolRepository
    * */

    public UsuarioController(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }
}
