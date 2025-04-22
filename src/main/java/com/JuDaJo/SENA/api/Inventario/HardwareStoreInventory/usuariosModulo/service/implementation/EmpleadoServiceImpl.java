package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;


import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.EmpleadoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    /**
     * Se inyecta la de dependencia para el repositorio de EmpleadoRepository.
     */
    @Autowired
    private EmpleadoRepository empleadoRepository;


}
