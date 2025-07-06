package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.implementation;

import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto.MovimientoInventarioDTO;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.MovimientoInventario;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.model.TipoMovimiento;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.model.Empleado;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.repository.MovimientoInventarioRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.usuariosModulo.repository.EmpleadoRepository;
import com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.service.MovimientoInventarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Implementación de la interfaz MovimientoInventarioService.
 * Proporciona servicios para gestionar los movimientos del inventario.
 */
@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    /**
     * Dependencia inyectada de tipo MovimientoInventarioRepository que proporciona acceso
     * a los métodos de manipulación de datos relacionados con la entidad
     * MovimientoInventario. Facilita la interacción con la base de datos para realizar
     * operaciones como crear, leer, actualizar y eliminar movimientos de inventario.
     *
     * Es utilizada principalmente dentro de la clase MovimientoInventarioServiceImpl
     * para ejecutar operaciones definidas por JpaRepository o personalizadas para
     * manejar los registros de los movimientos en el sistema de inventario.
     *
     * @see MovimientoInventarioRepository
     * @see MovimientoInventarioServiceImpl
     */
    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    /**
     * Dependencia inyectada de tipo EmpleadoRepository que proporciona acceso
     * a los métodos de manipulación de datos relacionados con la entidad
     * Empleado. Permite realizar operaciones como búsqueda, eliminación y
     * personalizaciones específicas en la gestión de empleados del sistema.
     *
     * Es utilizada principalmente dentro de la clase MovimientoInventarioServiceImpl
     * para ejecutar operaciones relacionadas con empleados, como la búsqueda de
     * empleados responsables a partir del nombre de usuario asociado.
     *
     * @see EmpleadoRepository
     * @see MovimientoInventarioServiceImpl
     */
    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Recupera una lista de todos los movimientos de inventario y los mapea a sus correspondientes objetos DTO.
     * Los datos son obtenidos desde el repositorio subyacente y transformados a un formato más adecuado
     * para uso externo.
     *
     * @return una lista de objetos {@code MovimientoInventarioDTO} que representan todos los movimientos de inventario.
     */
    @Override
    public List<MovimientoInventarioDTO> listarMovimientos() {
        return movimientoInventarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera una lista de movimientos de inventario asociados a una entidad específica.
     * Los movimientos son filtrados por el nombre de la entidad afectada, ignorando
     * diferencias de mayúsculas y minúsculas, y posteriormente convertidos a objetos DTO.
     *
     * @param entidadAfectada el nombre de la entidad cuya lista de movimientos de inventario
     *                        se desea obtener.
     * @return una lista de objetos {@code MovimientoInventarioDTO} que representan los
     *         movimientos de inventario*/
    @Override
    public List<MovimientoInventarioDTO> listarPorEntidad(String entidadAfectada) {
        return movimientoInventarioRepository.findAll()
                .stream()
                .filter(m -> m.getEntidadAfectada().equalsIgnoreCase(entidadAfectada))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Registra un movimiento en el inventario basado en la información proporcionada.
     * Valida el token para extraer el usuario responsable, verifica su existencia y
     * luego crea y persiste un nuevo registro de movimiento en la base de datos.
     *
     * @param token            El token que contiene información sobre el usuario que realiza el movimiento.
     * @param entidadAfectada  Nombre de la entidad o recurso afectado por el movimiento.
     * @param tipoMovimiento   Tipo de movimiento a registrar (por ejemplo, ENTRADA, SALIDA).
     * @param idEntidadAfectada Identificador único de la entidad o recurso afectado por el movimiento.
     * @param detalle          Descripción adicional o detalles específicos sobre el movimiento.
     * @throws RuntimeException Si el token no contiene un usuario válido, si el empleado no es encontrado
     *                          o si el tipo de movimiento no es válido.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void registrarMovimiento(String token, String entidadAfectada, String tipoMovimiento, String idEntidadAfectada, String detalle) {
        // Extraer nombre de usuario desde el token
        String nombreUsuario = extraerNombreUsuarioDesdeToken(token);
        if (nombreUsuario == null) {
            throw new RuntimeException("No se pudo extraer el usuario desde el token");
        }

        // Buscar el empleado responsable
        Empleado empleado = empleadoRepository.buscarPorNombreUsuario(nombreUsuario);
        if (empleado == null) {
            throw new RuntimeException("Empleado no encontrado para el usuario: " + nombreUsuario);
        }

        // Crear movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setFechaHoraMovimiento(LocalDateTime.now());
        //movimiento.setTipoMovimiento(TipoMovimiento.valueOf(tipoMovimiento));
        try {
            TipoMovimiento tipo = TipoMovimiento.valueOf(tipoMovimiento.trim().toUpperCase());
            movimiento.setTipoMovimiento(tipo);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de movimiento inválido: " + tipoMovimiento);
        }
        movimiento.setEntidadAfectada(entidadAfectada);
        movimiento.setIdEntidadAfectada(idEntidadAfectada);
        movimiento.setNombreEntidadAfectada(null);
        movimiento.setDetalleMovimiento(detalle);
        movimiento.setEmpleadoResponsable(empleado);

        System.out.println("🧪 Valor tipoMovimiento antes de persistir: " + movimiento.getTipoMovimiento());
        System.out.println("🧪 tipoMovimiento: " + movimiento.getTipoMovimiento());
        System.out.println("🧪 clase: " + (movimiento.getTipoMovimiento() != null ? movimiento.getTipoMovimiento().getClass().getName() : "null"));
        System.out.println("TIPO MOVIMIENTO RECIBIDO: " + tipoMovimiento);

        movimientoInventarioRepository.save(movimiento);
    }

    /**
     * Convierte una entidad de tipo MovimientoInventario en un objeto de transferencia de datos (DTO).
     *
     * @param movimiento el objeto MovimientoInventario que se desea convertir a DTO.
     * @return un objeto MovimientoInventarioDTO que representa la información del movimiento de inventario.
     */
    private MovimientoInventarioDTO convertirADTO(MovimientoInventario movimiento) {
        Empleado emp = movimiento.getEmpleadoResponsable();
        String nombreCompleto = emp.getNombres() + " " + emp.getApellidoPaterno();

        return new MovimientoInventarioDTO(
                movimiento.getIdMovimiento(),
                movimiento.getFechaHoraMovimiento(),
                movimiento.getTipoMovimiento(),
                movimiento.getEntidadAfectada(),
                movimiento.getIdEntidadAfectada(),
                movimiento.getNombreEntidadAfectada(),
                movimiento.getDetalleMovimiento(),
                emp.getIdEmpleado(),
                nombreCompleto
        );
    }

    /**
     * Extrae el nombre de usuario de un token JWT dado.
     *
     * Este método analiza el token JWT proporcionado utilizando una clave secreta predefinida para
     * extraer el sujeto (nombre de usuario) de las declaraciones del token. Si el token no puede
     * ser analizado o es inválido, el método retorna null.
     *
     * @param token el token JWT del cual se extraerá el nombre de usuario
     * @return el nombre de usuario extraído del token, o null si el token es inválido o no puede ser analizado
     */
    private String extraerNombreUsuarioDesdeToken(String token) {
        try {
            String clave = "claveSuperSecretaSeguraQueDebeTenerAlMenos32Caracteres";
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(clave.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // nombreUsuario
        } catch (Exception e) {
            return null;
        }
    }

}