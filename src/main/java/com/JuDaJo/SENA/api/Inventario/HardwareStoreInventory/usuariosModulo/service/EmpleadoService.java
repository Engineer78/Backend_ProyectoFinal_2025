package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.EmpleadoDTO;
import java.util.List;

public interface EmpleadoService {

    /**
     * Métodos principales de la clase EmpleadoService.
     * Definen el contrato de la lógica de negocio para trabajar con empleados.
     * @Param crearEmpleado en la base de datos.
     * @List<EmpleadoDTO> devuelve una lista con todos los empleados existentes en la base de datos.
     * @Param buscar un empleado por su número Documento.
     * @Param actualizarEmpleado existente en la base de datos.
     * @Param eliminarEmpleado elimina un empleado existente en la base de datos por su id.
     */
    EmpleadoDTO crearEmpleado(EmpleadoDTO dto);
    EmpleadoDTO buscarEmpleadoPorDocumento(String numeroDocumento);
    EmpleadoDTO actualizarEmpleado(int idEmpleado, EmpleadoDTO dto);
    void elimnarEmpledo(int idEmpleado);
    List<EmpleadoDTO> listarEmpleados();

}
