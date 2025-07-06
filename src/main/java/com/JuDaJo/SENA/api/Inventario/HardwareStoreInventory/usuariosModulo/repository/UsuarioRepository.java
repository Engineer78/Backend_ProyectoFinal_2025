package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Se utiliza para saber si el empleado existe con el nombre de usuario en la base de datos..
     * @param nombreUsuario
     * @return
     */
    boolean existsByNombreUsuario(String nombreUsuario);

    /**
     * Se utiliza para buscar un usuario por su nombre de usuario necesario para JWT y login.
     * @param nombreUsuario
     * @return
     */
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol JOIN FETCH u.empleado WHERE u.nombreUsuario = :nombreUsuario")
    Optional<Usuario> findByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

}
