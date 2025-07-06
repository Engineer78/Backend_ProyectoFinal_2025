package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.MovimientoInventario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.MovimientoInventarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.RegistroMovimientoService;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Usuario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.EmpleadoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementación del servicio de registro de movimientos de inventario.
 * Esta clase proporciona la lógica de negocio para registrar y almacenar
 * movimientos en el inventario relacionados con las operaciones realizadas
 * por los empleados y usuarios del sistema.
 *
 * Utiliza repositorios para interactuar con las entidades de dominio
 * MovimientoInventario, Usuario y Empleado, asegurando una correcta
 * persistencia de los datos en la base de datos.
 *
 * La clase implementa el método definido en la interface RegistroMovimientoService
 * y ejecuta la lógica para registrar un movimiento, incluyendo la validación
 * del usuario autenticado y la asociación del movimiento con el empleado responsable.
 *
 * Anotaciones:
 * - @Service: Indica que esta clase es un componente de servicio en el contexto de Spring.
 * - @Async: Permite la ejecución asíncrona del método registrado, delegando la ejecución
 *   de un subproceso independiente.
 * - @Transactional: Garantiza la gestión transaccional para operaciones con
 *   la base de datos, asegurando consistencia y rollback en caso de errores.
 */
@Service
public class RegistroMovimientoServiceImpl implements RegistroMovimientoService {

    /**
     * Inyección de dependencias automática de los repositorios necesarios:
     * - MovimientoInventarioRepository: Para gestionar las operaciones de movimientos de inventario
     * - EmpleadoRepository: Para manejar las operaciones relacionadas con empleados
     * - UsuarioRepository: Para gestionar las operaciones relacionadas con usuarios
     */
    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registra un movimiento en el inventario, asociándolo con el empleado responsable.
     * El movimiento incluye información relacionada con el tipo de movimiento, la entidad afectada,
     * y el detalle del movimiento. La operación es gestionada de manera transaccional y asíncrona.
     *
     * @param dto Objeto de transferencia de datos (MovimientoInventarioDTO) que contiene la
     *            información del movimiento a registrar, incluyendo tipo, entidad afectada,
     *            identificador de la entidad, nombre de la entidad y detalle del movimiento.
     */
    @Override
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void registrarMovimiento(MovimientoInventarioDTO dto) {
        // ✅ Obtener el usuario logueado desde el token JWT
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado."));

        Empleado empleado = usuario.getEmpleado();

        // ✅ Crear y registrar el movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setFechaHoraMovimiento(LocalDateTime.now());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setEntidadAfectada(dto.getEntidadAfectada());
        movimiento.setIdEntidadAfectada(dto.getIdEntidadAfectada());
        movimiento.setNombreEntidadAfectada(dto.getNombreEntidadAfectada());
        movimiento.setDetalleMovimiento(dto.getDetalleMovimiento());
        movimiento.setEmpleadoResponsable(empleado); // ✅ asignar empleado real

        movimientoInventarioRepository.save(movimiento);
    }
}