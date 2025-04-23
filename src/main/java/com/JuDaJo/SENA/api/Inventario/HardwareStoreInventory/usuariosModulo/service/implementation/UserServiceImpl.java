package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.UsuarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UsuarioService {
    /*
    * Inyecccion de dependencias
    * */
    private final UsuarioRepository usuarioRepository;
    public UserServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    /*
     * Metodos Abstractos
     * */
    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        /*
        * Conversion de DTO a Entidad
        * */
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombre());
        return null;
    }
}
