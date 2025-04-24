package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.EmpleadoDTO;
import java.util.List;

public interface EmpleadoService {

    /**
     * Métodos principales de la clase EmpleadoService.
     * Definen el contrato de la lógica de negocio para trabajar con empleados.
     * @param dto Objeto EmpleadoDTO con los datos del empleado a buscar.
     */
    EmpleadoDTO crear(EmpleadoDTO dto);
    EmpleadoDTO buscarPorId(int idEmpleado);
    EmpleadoDTO buscarEmpleadoPorDocumento(String numeroDocumento);
    EmpleadoDTO actualizarEmpleado(int idEmpleado, EmpleadoDTO dto);
    void eliminarEmpleado(int idEmpleado);
    List<EmpleadoDTO> listarEmpleados();
}
