package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Se utiliza para saber si el empleado existe con el nombre de usuario en la base de datos..
     * @param nombreUsuario
     * @return
     */
    boolean existsByNombreUsuario(String nombreUsuario);
}
