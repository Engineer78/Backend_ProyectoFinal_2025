package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception;

/**
 * Esta excepción se lanza para indicar que un recurso solicitado no se ha encontrado
 * en el sistema.
 *
 * Extiende {@code RuntimeException}, permitiendo su uso en tiempo de ejecución en
 * situaciones donde un recurso faltante necesita manejarse como un error.
 *
 * Esta excepción está pensada para ser manejada por un controlador global de excepciones,
 * devolviendo una respuesta HTTP estandarizada, como un estado 404 (NOT FOUND).
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
