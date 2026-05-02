package com.renerodriguez.core_client_management_system.infraestructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * Controlador de asesoría global para la gestión centralizada de excepciones en la capa de infraestructura.
 * <p>
 * Esta clase captura las excepciones lanzadas por los controladores y las transforma en respuestas
 * HTTP estandarizadas con formato JSON, garantizando que el cliente reciba una estructura de error
 * consistente y ocultando detalles técnicos sensibles en errores genéricos.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Maneja excepciones de tipo {@link IllegalArgumentException}.
 * Generalmente se dispara cuando los argumentos enviados a un método no cumplen con las precondiciones de negocio.
 * * @param ex Excepción capturada durante la ejecución.
 * @return {@link ResponseEntity} con estado 400 (Bad Request) y el detalle del error.
 */

/**
 * Maneja excepciones de tipo {@link RuntimeException}.
 * Implementa una lógica de filtrado para diferenciar errores de autenticación (401)
 * de errores internos del servidor (500) basándose en el contenido del mensaje.
 * * @param ex Excepción de tiempo de ejecución capturada.
 * @return {@link ResponseEntity} con el estado HTTP correspondiente y metadatos del error.
 */

/**
 * Maneja excepciones genéricas no controladas de tipo {@link Exception}.
 * Actúa como última barrera de seguridad para registrar errores inesperados y retornar
 * un mensaje amigable al usuario sin exponer la traza del error.
 * * @param ex Excepción de alto nivel capturada.
 * @return {@link ResponseEntity} con estado 500 (Internal Server Error).
 */

/**
 * Maneja errores de validación de argumentos en métodos anotados con {@code @Valid}.
 * Extrae los errores específicos de cada campo del objeto DTO para devolver una lista detallada
 * de qué campos fallaron y por qué.
 * * @param ex Excepción disparada por fallos en las restricciones de Bean Validation.
 * @return {@link ResponseEntity} con estado 400 y un mapa detallado de errores por campo.
 */

/**
 * Método de utilidad privado para construir la estructura de respuesta estándar.
 * * @param status  El código de estado HTTP a retornar.
 * * @param Error  Breve descripción del tipo de error.
 * * @param message Mensaje detallado sobre la causa de la excepción.
 * @return {@link ResponseEntity} con el mapa de datos y el estado HTTP.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Error de validación", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        log.error("Runtime error: ", ex);
        HttpStatus status = ex.getMessage().contains("incorrectos") ? HttpStatus.UNAUTHORIZED : HttpStatus.INTERNAL_SERVER_ERROR;
        return buildResponse(status, "Error de ejecución", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("Unexpected error: ", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "Ocurrió un error inesperado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Error de validación");
        body.put("message", "Existen campos con errores");
        body.put("errors", fieldErrors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}