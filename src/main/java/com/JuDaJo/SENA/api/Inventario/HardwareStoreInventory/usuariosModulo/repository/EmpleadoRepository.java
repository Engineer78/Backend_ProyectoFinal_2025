package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    /**
     * usca un empleado por su número de documento.
     * @param numeroDocumento El número de documento del empleado a buscar
     * @return Un Optional que contiene el empleado si se encuentra, o vacío si no existe
     */
    Optional<Empleado> findByNumeroDocumento(String numeroDocumento);

    /**
     * Se utiliza para eliminar un empleado por su ID.
     * @param idEmpleado El identificador único del empleado a eliminar
     */
    void deleteByIdEmpleado(Integer idEmpleado);
}