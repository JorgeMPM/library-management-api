package com.accenture.library.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.accenture.library.dto.response.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

/*
 * Crea la clase GlobalExceptionHandler con @RestControllerAdvice. Un método privado build(HttpStatus, String message,
 * HttpServletRequest) que devuelve ResponseEntity<ErrorResponseDTO> con contentType APPLICATION_JSON, timestamp LocalDateTime.now(),
 * status.value(), status.getReasonPhrase(), el mensaje y request.getRequestURI().
 * Un @ExceptionHandler por excepción: MethodArgumentNotValidException 400 (une con "; " los mensajes de field errors y global errors
 * con bucles for); HttpMessageNotReadableException, MethodArgumentTypeMismatchException, MissingServletRequestParameterException,
 * ReferencedResourceNotFoundException e InvalidQueryParameterException 400; ResourceNotFoundException y NoResourceFoundException 404
 * HttpRequestMethodNotSupportedException 405; HttpMediaTypeNotAcceptableException 406; HttpMediaTypeNotSupportedException 415
 * DuplicateResourceException, ResourceInUseException, BookOutOfStockException, LoanAlreadyReturnedException y
 * DataIntegrityViolationException 409; Exception 500. Mensajes en español. Sin lambdas ni streams.
 * Javadoc en español en la clase y en cada método. — generado por IA
 */
/**
 * Manejo centralizado de errores: convierte cada excepción en una respuesta con la estructura común de error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Construye la respuesta de error con la estructura común y en formato JSON.
     *
     * @param status  código HTTP
     * @param message mensaje en español
     * @param request petición HTTP, para obtener la ruta
     * @return la respuesta de error
     */
    private ResponseEntity<ErrorResponseDTO> build(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponseDTO body = new ErrorResponseDTO(LocalDateTime.now(), status.value(),
                status.getReasonPhrase(), message, request.getRequestURI());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    /**
     * Errores de Bean Validation: reúne en un solo mensaje los errores de campo y los de clase.
     *
     * @param ex      excepción de validación
     * @param request petición HTTP
     * @return 400 con todos los mensajes de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (message.length() > 0) {
                message.append("; ");
            }
            message.append(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            if (message.length() > 0) {
                message.append("; ");
            }
            message.append(error.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, message.toString(), request);
    }

    /**
     * JSON mal formado o valor no válido en un campo (enum inexistente, fecha con otro formato...).
     *
     * @param ex      excepción de lectura del body
     * @param request petición HTTP
     * @return 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotReadable(HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición no es válido: JSON mal formado o valor no válido en algún campo"
                        + " (las fechas deben tener el formato dd-MM-yyyy)",
                request);
    }

    /**
     * Parámetro de la URL con un tipo no válido (por ejemplo /books/abc o available=maybe).
     *
     * @param ex      excepción de conversión de tipo
     * @param request petición HTTP
     * @return 400
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST,
                "El parámetro '" + ex.getName() + "' tiene un valor no válido: '" + ex.getValue() + "'", request);
    }

    /**
     * Parámetro obligatorio de la URL ausente.
     *
     * @param ex      excepción de parámetro ausente
     * @param request petición HTTP
     * @return 400
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParameter(MissingServletRequestParameterException ex,
            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Falta el parámetro obligatorio '" + ex.getParameterName() + "'", request);
    }

    /**
     * Id inexistente en el body o parámetro de paginación no válido.
     *
     * @param ex      excepción de negocio
     * @param request petición HTTP
     * @return 400
     */
    @ExceptionHandler({ ReferencedResourceNotFoundException.class, InvalidQueryParameterException.class })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    /**
     * Recurso pedido por su id en la URL que no existe.
     *
     * @param ex      excepción de recurso no encontrado
     * @param request petición HTTP
     * @return 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Ruta inexistente.
     *
     * @param ex      excepción de ruta no encontrada
     * @param request petición HTTP
     * @return 404
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoRoute(NoResourceFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "La ruta " + request.getRequestURI() + " no existe", request);
    }

    /**
     * Método HTTP no permitido en una ruta existente.
     *
     * @param ex      excepción de método no soportado
     * @param request petición HTTP
     * @return 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {
        return build(HttpStatus.METHOD_NOT_ALLOWED,
                "El método " + ex.getMethod() + " no está permitido en la ruta " + request.getRequestURI(), request);
    }

    /**
     * La cabecera Accept pide un formato que la API no puede devolver.
     *
     * @param ex      excepción de tipo no aceptable
     * @param request petición HTTP
     * @return 406
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpServletRequest request) {
        return build(HttpStatus.NOT_ACCEPTABLE, "La API solo puede responder en formato JSON", request);
    }

    /**
     * Content-Type de la petición no soportado.
     *
     * @param ex      excepción de tipo no soportado
     * @param request petición HTTP
     * @return 415
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex,
            HttpServletRequest request) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "El tipo de contenido " + ex.getContentType() + " no está soportado; usa application/json", request);
    }

    /**
     * Reglas de negocio incumplidas o valor único repetido.
     *
     * @param ex      excepción de negocio
     * @param request petición HTTP
     * @return 409
     */
    @ExceptionHandler({ DuplicateResourceException.class, ResourceInUseException.class,
            BookOutOfStockException.class, LoanAlreadyReturnedException.class })
    public ResponseEntity<ErrorResponseDTO> handleConflict(RuntimeException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    /**
     * Violación de una restricción de la base de datos (por ejemplo, un UNIQUE): nunca debe dar 500.
     *
     * @param ex      excepción de integridad de datos
     * @param request petición HTTP
     * @return 409
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrity(DataIntegrityViolationException ex,
            HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, "La operación viola una restricción de la base de datos (valor único repetido)",
                request);
    }

    /**
     * Petición sin token, con token inválido o caducado, o credenciales de login incorrectas.
     *
     * @param ex      excepción de autenticación
     * @param request petición HTTP
     * @return 401
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(AuthenticationException ex, HttpServletRequest request) {
        String message = "Se requiere un token válido para acceder a este recurso";
        if (ex instanceof BadCredentialsException) {
            message = "Credenciales incorrectas";
        }
        return build(HttpStatus.UNAUTHORIZED, message, request);
    }

    /**
     * Usuario autenticado sin permiso para la operación.
     *
     * @param ex      excepción de acceso denegado
     * @param request petición HTTP
     * @return 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(AccessDeniedException ex, HttpServletRequest request) {
        return build(HttpStatus.FORBIDDEN, "No tienes permiso para realizar esta operación", request);
    }

    /**
     * Cualquier error no controlado.
     *
     * @param ex      excepción no controlada
     * @param request petición HTTP
     * @return 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", request);
    }
}