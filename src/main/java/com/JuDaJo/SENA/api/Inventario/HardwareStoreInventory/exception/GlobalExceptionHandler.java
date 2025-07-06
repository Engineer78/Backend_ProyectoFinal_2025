package com.JuDaJo.SENA.api.Inventario.HardwareStoreInventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que maneja todas las excepciones lanzadas desde controladores y servicios.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de validaciones con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // 2. Manejo de excepciones personalizadas
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RecursoNoEncontradoException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    // 3. Mensaje de excepciones para indicar que se ha intentado crear o insertar una entidad
    // o recurso duplicado en el sistema.
    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleDuplicado(DuplicadoException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);
    }

    // 3. Cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobal(Exception ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Ocurrió un error inesperado");
        respuesta.put("detalle", ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
