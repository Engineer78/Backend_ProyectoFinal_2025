package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.RolPermisoDTO;

import java.util.List;

/**
 * Interfaz para el servicio de gestión de relaciones entre Roles y Permisos.
 */
public interface RolPermisoService {

    /**
     * Crea una nueva relación entre un rol y un permiso.
     *
     * @param dto Objeto con los IDs de rol y permiso.
     * @return La relación creada.
     */
    RolPermisoDTO crearRolPermiso(RolPermisoDTO dto);

    /**
     * Lista todas las relaciones entre roles y permisos.
     *
     * @return Lista de relaciones en formato DTO.
     */
    List<RolPermisoDTO> listarRelaciones();

    /**
     * Elimina una relación específica entre un rol y un permiso.
     *
     * @param idRolPermiso ID de la relación a eliminar.
     */
    void eliminarRolPermiso(int idRolPermiso);

    /**
     * Lista todos los permisos asociados a un rol específico.
     *
     * @param idRol ID del rol a consultar.
     * @return Lista de relaciones correspondientes al rol.
     */
    List<RolPermisoDTO> listarPorRol(int idRol);

    /**
     * Verifica si existe una relación específica entre un rol y un permiso
     * en el sistema, basado en sus respectivos IDs.
     *
     * @param idRol ID del rol a verificar.
     * @param idPermiso ID del permiso a verificar.
     * @return {@code true} si la relación existe, {@code false} en caso contrario.
     */
    boolean existeRolPermiso(Integer idRol, Integer idPermiso);

    /**
     * Elimina una relación entre un rol y un permiso específicos en el sistema,
     * identificados por sus respectivos IDs.
     *
     * @param idRol ID del rol cuya relación con el permiso se eliminará.
     * @param idPermiso ID del permiso cuya relación con el rol se eliminará.
     */
    void eliminarRolPermisoPorRolYPermiso(Integer idRol, Integer idPermiso);

    /**
     * Actualiza la lista de permisos asociados a un rol específico.
     * Si un permiso no está incluido en la lista proporcionada, se eliminará del rol.
     *
     * @param idRol El identificador único del rol cuyos permisos serán actualizados.
     * @param idsPermisos La lista de identificadores únicos de los permisos que se asociarán al rol.
     */
    void actualizarPermisosPorRol(int idRol, List<Integer> idsPermisos);
}
