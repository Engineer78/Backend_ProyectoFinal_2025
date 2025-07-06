package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.ProveedorDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Interfaz de servicio para operaciones relacionadas con proveedores.
 */
public interface ProveedorService {

    /**
     * Lista todos los proveedores registrados.
     *
     * @return Lista de ProveedorDTO
     */
    List<ProveedorDTO> listarProveedores();

    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id ID del proveedor
     * @return ResponseEntity con el proveedor o 404 si no existe
     */
    ResponseEntity<ProveedorDTO> obtenerProveedor(int id);

    /**
     * Busca un proveedor por su NIT.
     *
     * @param nitProveedor NIT del proveedor
     * @return ResponseEntity con el proveedor o 404 si no existe
     */
    ResponseEntity<ProveedorDTO> buscarPorNit(String nitProveedor);

    /**
     * Busca un proveedor por su nombre.
     *
     * @param nombreProveedor Nombre del proveedor
     * @return ResponseEntity con el proveedor o 404 si no existe
     */
    ResponseEntity<ProveedorDTO> buscarPorNombre(String nombreProveedor);

    /**
     * Crea un nuevo proveedor.
     *
     * @param dto Datos del proveedor
     * @return Proveedor creado
     */
    ResponseEntity<Object> crearProveedor(ProveedorDTO dto);

    /**
     * Actualiza un proveedor existente.
     *
     * @param id ID del proveedor a actualizar
     * @param dto Datos nuevos del proveedor
     * @return Proveedor actualizado
     */
    ResponseEntity<ProveedorDTO> actualizarProveedor(int id, ProveedorDTO dto);

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id ID del proveedor
     * @return ResponseEntity con estado de la operación
     */
    ResponseEntity<Void> eliminarProveedor(int id);
}