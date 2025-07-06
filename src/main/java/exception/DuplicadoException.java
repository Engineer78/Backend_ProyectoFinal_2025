package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception;

/**
 * Esta excepción se lanza para indicar que se ha intentado crear o insertar una entidad
 * o recurso duplicado en el sistema.
 *
 * Extiende {@code RuntimeException}, permitiendo su uso para el manejo de errores en tiempo
 * de ejecución en escenarios donde no se permite la duplicación de datos.
 *
 * Esta excepción se utiliza típicamente en contextos donde se necesitan aplicar restricciones
 * de unicidad y generalmente es manejada por un controlador global de excepciones para
 * retornar una respuesta estandarizada.
 */
public class DuplicadoException extends RuntimeException {
    public DuplicadoException(String mensaje) {
        super(mensaje);
    }
}