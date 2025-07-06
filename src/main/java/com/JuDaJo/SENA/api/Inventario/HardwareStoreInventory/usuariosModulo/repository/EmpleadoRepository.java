package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * Se utiliza para buscar un empleado por su nombre de usuario necesario para JWT y login.
     * @param nombreUsuario
     * @return
     */
    @Query("SELECT e FROM Empleado e WHERE e.usuario.nombreUsuario = :nombreUsuario")
    Empleado buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

}