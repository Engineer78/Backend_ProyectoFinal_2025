package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service;

public interface EmpleadoService {

    /**
     * Métodos principales de la clase EmpleadoService.
     * Definen el contrato de la lógica de negocio para trabajar con empleados.
     * @Param EmpleadoDTO crea un nuevo empleado en la base de datos.
     * @List<EmpleadoDTO> listarEmpleado() devuelve una lista con todos los empleados existentes en la base de datos.
     * @Param EmpleadoDTO idEmpleado) buscar un empleado por su ID.
     * @Param EmpleadoDTO actualizarEmpleado(int idEmpleado) actualiza un empleado existente en la base de datos.
     * @Param eliminarEmpleado(int idEmpleado) elimina un empleado existente en la base de datos.
     */
    EmpleadoDTO crearEmpleado(EmpleadoDTO empleadoDTO);
    List<EmpleadoDTO> listarEmpleados();
    EmpleadoDTO buscarPorId(int idEmpleado);
    EmpleadoDTO actualizarEmpleado(int idEmpleado, EmpleadoDTO empleadoDTO);
    void eliminarEmpleado(int idEmpleado);

}
