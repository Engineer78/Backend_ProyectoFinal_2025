package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.RolRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.RolService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Rol;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.PerfilRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Perfil;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
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

    /**
     * Implementar el método obtenerTodos() dentro de RolServiceImpl.
     * Obtiene todos los Rol desde el repositorio.
     * Convierte cada uno a un RolDTO.
     * Devuelve la lista de DTOs.
     * Aquí usamos un bucle for para crear un nuevo RolDTO por cada Rol, incluyendo la relación con el Perfil.
     */
    public List<RolDTO> obtenerTodos() {
        List<Rol> roles = rolRepository.findAll();
        List<RolDTO> rolesDTO = new ArrayList<>();

        for (Rol rol : roles) {
            RolDTO dto = new RolDTO(
                    rol.getIdRol(),
                    rol.getNombreRol(),
                    rol.getDescripcion(),
                    rol.getPerfil() != null ? rol.getPerfil().getIdPerfil() : null
            );
            rolesDTO.add(dto);
        }

        return rolesDTO;
    }

    /**
     * Implementar el método el método buscarPorId() en RolServiceImpl.
     * Busca un Rol por su ID usando RolRepository..
     * Si no lo encuentra, lanza una excepción (NoSuchElementException, por ahora).
     * Si lo encuentra, lo convierte en RolDTO y lo retorna.
     */
    public RolDTO buscarPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rol no encontrado con id: " + id));

        return new RolDTO(
                rol.getIdRol(),
                rol.getNombreRol(),
                rol.getDescripcion(),
                rol.getPerfil() != null ? rol.getPerfil().getIdPerfil() : null
        );
    }

    /**
     * Implementar el método guardarRol(RolDTO rolDTO) en RolServiceImpl.
     * Buscará el perfil correspondiente al idPerfil (si existe).
     * Creará una nueva entidad Rol a partir de los datos del RolDTO.
     * Asociará el Perfil a ese nuevo rol.
     * Guardará el rol en la base de datos.
     * Devolverá el DTO con los datos del rol guardado.
     */
    public RolDTO guardarRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());

        if (rolDTO.getIdPerfil() != null) {
            Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                    .orElseThrow(() -> new NoSuchElementException("Perfil no encontrado con id: " + rolDTO.getIdPerfil()));
            rol.setPerfil(perfil);
        }

        Rol rolGuardado = rolRepository.save(rol);

        return new RolDTO(
                rolGuardado.getIdRol(),
                rolGuardado.getNombreRol(),
                rolGuardado.getDescripcion(),
                rolGuardado.getPerfil() != null ? rolGuardado.getPerfil().getIdPerfil() : null
        );
    }

    /**
     * Implementar el método actualizarRol(Integer id, RolDTO rolDTO) en RolServiceImpl.
     * Buscar el rol existente. Se valida que el id exista en la base de datos, si no, lanza excepción con mensaje claro.
     * Actualizar datos simples. Se actualiza el nombre y la descripción.
     * Actualizar la relación con Perfil. Si idPerfil viene en el DTO, busca el perfil asociado y lo actualiza.
     * Guardar. Se guardan los cambios en la base de datos usando rolRepository.save().
     * Devolver DTO. Devuelve el rol actualizado como un objeto RolDTO.
     */
    public RolDTO actualizarRol(Integer id, RolDTO rolDTO) {
        // 1. Buscar el rol por ID
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rol no encontrado con ID: " + id));

        // 2. Actualizar campos del rol
        rolExistente.setNombreRol(rolDTO.getNombreRol());
        rolExistente.setDescripcion(rolDTO.getDescripcion());

        // 3. Actualizar la asociación con el perfil, si es necesario
        if (rolDTO.getIdPerfil() != null) {
            Perfil perfil = perfilRepository.findById(rolDTO.getIdPerfil())
                    .orElseThrow(() -> new NoSuchElementException("Perfil no encontrado con ID: " + rolDTO.getIdPerfil()));
            rolExistente.setPerfil(perfil);
        }

        // 4. Guardar los cambios
        Rol rolActualizado = rolRepository.save(rolExistente);

        // 5. Devolver el resultado como DTO
        return new RolDTO(
                rolActualizado.getIdRol(),
                rolActualizado.getNombreRol(),
                rolActualizado.getPerfil() != null ? rolActualizado.getPerfil().getIdPerfil() : null
        );
    }

    /**
     * Implementar el método obtenerRolPorId(Integer id) en RolServiceImpl.
     * Este método permite obtener un rol específico a partir de su ID, devolviendo su representación como DTO.
     * Es útil para mostrar los datos en formularios de edición, vistas detalladas, etc.
     * Validación: Se lanza una excepción clara si no se encuentra el rol.
     * Conversión a DTO: Se prepara el RolDTO para enviar solo los datos necesarios al frontend.
     * Prevención de NullPointerException: Se valida si el perfil existe antes de obtener su ID.
     */
    public RolDTO obtenerRolPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rol no encontrado con ID: " + id));

        return new RolDTO(
                rol.getIdRol(),
                rol.getNombreRol(),
                rol.getDescripcion(),
                rol.getPerfil() != null ? rol.getPerfil().getIdPerfil() : null
        );
    }

    /**
     * Implementar el método obtenerTodosLosRoles() en RolServiceImpl.
     * Este método lista todos los roles existentes en la base de datos, devolviendo una lista de RolDTO.
     * Es útil para mostrar una tabla o listado general en el frontend.
     * findAll(): Consulta todos los registros de la tabla rol.
     * stream().map(): Convierte cada entidad Rol a un objeto RolDTO.
     * Conversión segura: Maneja null para evitar errores si no hay un perfil asociado.
     */
    public List<RolDTO> obtenerTodosLosRoles() {
        List<Rol> roles = rolRepository.findAll();

        return roles.stream()
                .map(rol -> new RolDTO(
                        rol.getIdRol(),
                        rol.getNombreRol(),
                        rol.getDescripcion(),
                        rol.getPerfil() != null ? rol.getPerfil().getIdPerfil() : null
                ))
                .collect(Collectors.toList());
    }

    /**
     * Implementar el método eliminarRol(Integer id) en RolServiceImpl.
     * Este método permite eliminar un rol por su ID, y es importante asegurarse de que:
     * El rol exista antes de intentar eliminarlo.
     * Si no existe, se lanza una excepción adecuada.
     * Usa findById(id) para buscar el rol en la base de datos.
     * Si no lo encuentra, lanza una EntityNotFoundException.
     * Si lo encuentra, lo elimina con delete().
     */
    public void eliminarRol(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol con ID " + id + " no encontrado"));

        rolRepository.delete(rol);
    }
}