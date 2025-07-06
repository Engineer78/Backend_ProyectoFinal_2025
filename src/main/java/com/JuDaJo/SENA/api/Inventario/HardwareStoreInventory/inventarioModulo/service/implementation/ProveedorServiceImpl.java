package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.ProveedorDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.Proveedor;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.ProveedorRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.ProveedorService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception.RecursoNoEncontradoException;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz ProveedorService.
 * Encargada de la lógica de negocio para proveedores.
 */
@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    // Inyecta repositorios necesarios para las operaciones con movimientos de inventario
    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    // Inyecta repositorios necesarios para las operaciones con usuarios
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroMovimientoService registroMovimientoService;


    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * Lista todos los proveedores del sistema.
     */
    @Override
    public List<ProveedorDTO> listarProveedores() {
        List<ProveedorDTO> lista = proveedorRepository.findAll().stream()
                .map(p -> new ProveedorDTO(
                        p.getIdProveedor(),
                        p.getNombreProveedor(),
                        p.getNitProveedor(),
                        p.getTelefonoProveedor(),
                        p.getDireccionProveedor()
                ))
                .collect(Collectors.toList());

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setDetalleMovimiento("Se listaron todos los proveedores del sistema");
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        return lista;
    }

    /**
     * Obtiene un proveedor por ID.
     */
    @Override
    public ResponseEntity<ProveedorDTO> obtenerProveedor(int id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        String detalle = "Se consultó el proveedor con ID: " + id;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setIdEntidadAfectada(String.valueOf(proveedor.getIdProveedor()));
        movimiento.setNombreEntidadAfectada(proveedor.getNombreProveedor());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        ProveedorDTO dto = new ProveedorDTO(
                proveedor.getIdProveedor(),
                proveedor.getNombreProveedor(),
                proveedor.getNitProveedor(),
                proveedor.getTelefonoProveedor(),
                proveedor.getDireccionProveedor()
        );

        return ResponseEntity.ok(dto);
    }


    /**
     * Busca un proveedor por su NIT.
     */
    @Override
    public ResponseEntity<ProveedorDTO> buscarPorNit(String nitProveedor) {
        Proveedor proveedor = proveedorRepository.findByNitProveedor(nitProveedor)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con NIT: " + nitProveedor));

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        String detalle = "Se consultó el proveedor con NIT: " + nitProveedor;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setIdEntidadAfectada(String.valueOf(proveedor.getIdProveedor()));
        movimiento.setNombreEntidadAfectada(proveedor.getNombreProveedor());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        ProveedorDTO dto = new ProveedorDTO(
                proveedor.getIdProveedor(),
                proveedor.getNombreProveedor(),
                proveedor.getNitProveedor(),
                proveedor.getTelefonoProveedor(),
                proveedor.getDireccionProveedor()
        );

        return ResponseEntity.ok(dto);
    }


    /**
     * Busca un proveedor por su nombre.
     */
    @Override
    public ResponseEntity<ProveedorDTO> buscarPorNombre(String nombreProveedor) {
        Proveedor proveedor = proveedorRepository.findByNombreProveedor(nombreProveedor)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con nombre: " + nombreProveedor));

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        String detalle = "Se consultó el proveedor con nombre: " + nombreProveedor;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CONSULTAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setIdEntidadAfectada(String.valueOf(proveedor.getIdProveedor()));
        movimiento.setNombreEntidadAfectada(proveedor.getNombreProveedor());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno());

        registroMovimientoService.registrarMovimiento(movimiento);

        ProveedorDTO dto = new ProveedorDTO(
                proveedor.getIdProveedor(),
                proveedor.getNombreProveedor(),
                proveedor.getNitProveedor(),
                proveedor.getTelefonoProveedor(),
                proveedor.getDireccionProveedor()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Crea un nuevo proveedor en el sistema.
     */
    @Override
    public ResponseEntity<Object> crearProveedor(ProveedorDTO dto) {
        boolean existeNit = proveedorRepository.findByNitProveedor(dto.getNitProveedor()).isPresent();
        if (existeNit) {
            throw new RecursoNoEncontradoException("Ya existe un proveedor con ese NIT. No se puede duplicar.");
        }

        Proveedor proveedor = new Proveedor(
                dto.getNombreProveedor(),
                dto.getNitProveedor(),
                dto.getTelefonoProveedor(),
                dto.getDireccionProveedor()
        );

        Proveedor nuevo = proveedorRepository.save(proveedor);

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        String detalle = "Se creó el proveedor: " + nuevo.getNombreProveedor() + " | NIT: " + nuevo.getNitProveedor();

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.CREAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setIdEntidadAfectada(String.valueOf(nuevo.getIdProveedor()));
        movimiento.setNombreEntidadAfectada(nuevo.getNombreProveedor());
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        ProveedorDTO respuesta = new ProveedorDTO(
                nuevo.getIdProveedor(),
                nuevo.getNombreProveedor(),
                nuevo.getNitProveedor(),
                nuevo.getTelefonoProveedor(),
                nuevo.getDireccionProveedor()
        );

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }


    /**
     * Actualiza un proveedor existente.
     */
    @Override
    public ResponseEntity<ProveedorDTO> actualizarProveedor(int id, ProveedorDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));

        // Guardar datos anteriores para el detalle
        String nombreAnterior = proveedor.getNombreProveedor();
        String nitAnterior = proveedor.getNitProveedor();

        // Actualizar campos
        proveedor.setNombreProveedor(dto.getNombreProveedor());
        proveedor.setNitProveedor(dto.getNitProveedor());
        proveedor.setTelefonoProveedor(dto.getTelefonoProveedor());
        proveedor.setDireccionProveedor(dto.getDireccionProveedor());

        Proveedor actualizado = proveedorRepository.save(proveedor);

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        StringBuilder detalle = new StringBuilder("Se actualizó el proveedor: ").append(actualizado.getNombreProveedor());

        if (!nombreAnterior.equals(actualizado.getNombreProveedor())) {
            detalle.append(" | Nombre: ").append(nombreAnterior).append(" → ").append(actualizado.getNombreProveedor());
        }
        if (!nitAnterior.equals(actualizado.getNitProveedor())) {
            detalle.append(" | NIT: ").append(nitAnterior).append(" → ").append(actualizado.getNitProveedor());
        }

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ACTUALIZAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setIdEntidadAfectada(String.valueOf(actualizado.getIdProveedor()));
        movimiento.setNombreEntidadAfectada(actualizado.getNombreProveedor());
        movimiento.setDetalleMovimiento(detalle.toString());
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        ProveedorDTO respuesta = new ProveedorDTO(
                actualizado.getIdProveedor(),
                actualizado.getNombreProveedor(),
                actualizado.getNitProveedor(),
                actualizado.getTelefonoProveedor(),
                actualizado.getDireccionProveedor()
        );

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Elimina un proveedor por su ID.
     */
    @Override
    public ResponseEntity<Void> eliminarProveedor(int id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));

        // Guardar datos antes de eliminar
        String nombreProveedor = proveedor.getNombreProveedor();
        String nitProveedor = proveedor.getNitProveedor();

        proveedorRepository.delete(proveedor);

        // 🔐 Usuario responsable
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioResponsable = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autenticado no encontrado"));
        Empleado empleadoResponsable = usuarioResponsable.getEmpleado();

        // 🧾 Trazabilidad
        String detalle = "Se eliminó el proveedor " + nombreProveedor + " con NIT: " + nitProveedor;

        MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO();
        movimiento.setTipoMovimiento(TipoMovimiento.ELIMINAR);
        movimiento.setEntidadAfectada("Proveedor");
        movimiento.setIdEntidadAfectada(String.valueOf(id));
        movimiento.setNombreEntidadAfectada(nombreProveedor);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setIdEmpleadoResponsable(empleadoResponsable.getIdEmpleado());
        movimiento.setNombreEmpleadoResponsable(
                empleadoResponsable.getNombres() + " " + empleadoResponsable.getApellidoPaterno()
        );

        registroMovimientoService.registrarMovimiento(movimiento);

        return ResponseEntity.noContent().build();
    }
}

