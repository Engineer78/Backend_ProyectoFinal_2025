package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.inventarioModulo.dto;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase representa un DTO (Data Transfer Object) para encapsular
 * los mensajes de error de validación.
 * Se ha diseñado esta clase para transformar errores de validación en una lista
 * de mensajes simples que se pueden utilizar fácilmente en la capa de presentación.
 */
public class ValidationErrorDTO {

    // Lista que contiene los mensajes de error de validación en formato de texto.
    private List<String> errores;

    /**
     * Constructor de la clase ValidationErrorDTO.
     * Aquí se convierto una lista de objetos ObjectError en una lista de cadenas de texto
     * que contienen los mensajes de error predeterminados.
     *
     * @param errores Lista de errores de validación (ObjectError) provenientes de Spring.
     */
    public ValidationErrorDTO(List<ObjectError> errores) {
        // Se usa un flujo (Stream) para mapear cada error al mensaje predeterminado y crear la lista final.
        this.errores = errores.stream()
                .map(ObjectError::getDefaultMessage) // Extraigo el mensaje predeterminado de cada error.
                .collect(Collectors.toList()); // Recolecto los mensajes en una lista.
    }

    /**
     * Método para obtener la lista de mensajes de error.
     * Se he diseñado para que devuelva los errores en formato de texto ya procesados.
     *
     * @return Lista de cadenas de texto que representan los mensajes de error.
     */
    public List<String> getErrores() {
        // Devuelve la lista de errores en forma de texto.
        return errores;
    }
}

