package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

    /**
     * Inyectar dependencias de RolRepository y PerfilRepository.
     * Esto permitirá que RolServiceImpl acceda a las operaciones de base de datos necesarias para manejar los roles y su relación con los perfiles
     */
    private final RolRepository rolRepository;
    private final PerfilRepository perfilRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository, PerfilRepository perfilRepository) {
        this.rolRepository = rolRepository;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Implementar el método crearRol() dentro de RolServiceImpl usando el DTO RolDTO.
     * Buscar Esto permite encontrar el Perfil por el ID recibido en rolDTO.
     * Crear una instancia de Rol.
     * Asignar valores desde el DTO.
     * Asociar el Perfil.
     * Guardar el Rol.
     * Retornar un DTO de respuesta si es necesario.
     */
    @Override
    public RolDTO crearRol(RolDTO rolDTO) {
        // Buscar el perfil al que se va a asociar el rol
        Optional<Perfil> perfilOptional = perfilRepository.findById(rolDTO.getIdPerfil());
        if (!perfilOptional.isPresent()) {
            throw new RuntimeException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil());
        }

        Perfil perfil = perfilOptional.get();

        // Crear entidad Rol
        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setPerfil(perfil);

        // Guardar rol en la base de datos
        Rol rolGuardado = rolRepository.save(rol);

        // Devolver DTO de respuesta
        return new RolDTO(
                rolGuardado.getIdRol(),
                rolGuardado.getNombreRol(),
                rolGuardado.getDescripcion(),
                rolGuardado.getPerfil().getIdPerfil()
        );
}
