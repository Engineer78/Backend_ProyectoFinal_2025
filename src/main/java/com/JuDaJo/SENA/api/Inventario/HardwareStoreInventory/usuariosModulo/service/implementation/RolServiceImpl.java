package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

    // Aquí se implementarán los métodos definidos en RolService
    private final RolRepository rolRepository;
    private final PerfilRepository perfilRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository, PerfilRepository perfilRepository) {
        this.rolRepository = rolRepository;
        this.perfilRepository = perfilRepository;
    }

    // Aquí se implementarán los métodos del servicio
}
