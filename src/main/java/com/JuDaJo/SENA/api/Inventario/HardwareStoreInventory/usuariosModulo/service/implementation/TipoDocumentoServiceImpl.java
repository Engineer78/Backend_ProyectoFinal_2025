package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.DuplicadoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.dto.TipoDocumentoDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.TipoDocumento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.TipoDocumentoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    /**
     * Inyectar dependencia de TipoDocumentoRepository
     */
    private final TipoDocumentoRepository tipoDocumentoRepository;

    // Inyectar dependencia de MovimientoInventarioService
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    // Inyectar dependencia de UsuarioRepository
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroMovimientoService registroMovimientoService;

    /**
     * Se inyecta la dependencia TipoDocumentoRepository en el constructor de la clase.
     */
    @Autowired
    public TipoDocumentoServiceImpl(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    /**
     * Lista todos los tipos de documento existentes en el sistema.
     */
    @Override
    public List<TipoDocumentoDTO> listarTiposDocumento() {
        return tipoDocumentoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un tipo de documento por su ID.
     */
    @Override
    public TipoDocumentoDTO obtenerTipoDocumentoPorId(Integer id) {
        TipoDocumento tipo = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento no encontrado con ID: " + id));

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("TipoDocumento");
        movimiento.setIdEntidadAfectada(String.valueOf(tipo.getIdTipoDocumento()));
        movimiento.setNombreEntidadAfectada(tipo.getNombre());
        movimiento.setDetalleMovimiento("Se consultó el tipo de documento con ID: " + id);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(tipo);
    }

    /**
     * Crea un nuevo tipo de documento en la base de datos a partir de un objeto TipoDocumentoDTO.
     */
    @Override
    public TipoDocumentoDTO crearTipoDocumento(TipoDocumentoDTO dto) {
        boolean existe = tipoDocumentoRepository.findAll().stream()
                .anyMatch(td -> td.getCodigo().equalsIgnoreCase(dto.getCodigo()) ||
                        td.getNombre().equalsIgnoreCase(dto.getNombre()));

        if (existe) {
            throw new DuplicadoException("Ya existe un tipo de documento con el mismo código o nombre.");
        }

        TipoDocumento tipo = new TipoDocumento();
        tipo.setCodigo(dto.getCodigo());
        tipo.setNombre(dto.getNombre());

        TipoDocumento guardado = tipoDocumentoRepository.save(tipo);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        String detalle = "Se creó el tipo de documento: " + guardado.getNombre() + " (código: " + guardado.getCodigo() + ")";

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("TipoDocumento");
        movimiento.setIdEntidadAfectada(String.valueOf(guardado.getIdTipoDocumento()));
        movimiento.setNombreEntidadAfectada(guardado.getNombre());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(guardado);
    }

    /**
     * Actualiza un tipo de documento existente.
     */
    @Override
    public TipoDocumentoDTO actualizarTipoDocumento(Integer id, TipoDocumentoDTO dto) {
        TipoDocumento tipo = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento no encontrado con ID: " + id));

        boolean existe = tipoDocumentoRepository.findAll().stream()
                .anyMatch(td -> !td.getIdTipoDocumento().equals(id) &&
                        (td.getCodigo().equalsIgnoreCase(dto.getCodigo()) ||
                                td.getNombre().equalsIgnoreCase(dto.getNombre())));

        if (existe) {
            throw new DuplicadoException("Ya existe otro tipo de documento con ese código o nombre.");
        }

        String codigoAnterior = tipo.getCodigo();
        String nombreAnterior = tipo.getNombre();

        tipo.setCodigo(dto.getCodigo());
        tipo.setNombre(dto.getNombre());

        TipoDocumento actualizado = tipoDocumentoRepository.save(tipo);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        StringBuilder detalle = new StringBuilder("Se actualizó el tipo de documento: " + actualizado.getNombre());
        if (!codigoAnterior.equals(actualizado.getCodigo())) {
            detalle.append(" | Código: ").append(codigoAnterior).append(" → ").append(actualizado.getCodigo());
        }
        if (!nombreAnterior.equals(actualizado.getNombre())) {
            detalle.append(" | Nombre: ").append(nombreAnterior).append(" → ").append(actualizado.getNombre());
        }

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("TipoDocumento");
        movimiento.setIdEntidadAfectada(String.valueOf(actualizado.getIdTipoDocumento()));
        movimiento.setNombreEntidadAfectada(actualizado.getNombre());
        movimiento.setDetalleMovimiento(detalle.toString());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return toDTO(actualizado);
    }

    /**
     * Elimina un tipo de documento por su ID.
     */
    @Override
    public void eliminarTipoDocumento(Integer id) {
        TipoDocumento tipo = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de documento no encontrado con ID: " + id));

        String nombreTipo = tipo.getNombre();

        tipoDocumentoRepository.deleteById(id);

        // 🔐 Usuario logueado desde el JWT
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Armar detalle del movimiento
        String detalle = "Se eliminó el tipo de documento: " + nombreTipo + " con ID " + id;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("TipoDocumento");
        movimiento.setIdEntidadAfectada(String.valueOf(id));
        movimiento.setNombreEntidadAfectada(nombreTipo);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

    }

    /**
     * Método auxiliar para convertir una entidad TipoDocumento a su DTO equivalente.
     */
    private TipoDocumentoDTO toDTO(TipoDocumento tipo) {
        return new TipoDocumentoDTO(tipo.getIdTipoDocumento(), tipo.getCodigo(), tipo.getNombre());
    }

}