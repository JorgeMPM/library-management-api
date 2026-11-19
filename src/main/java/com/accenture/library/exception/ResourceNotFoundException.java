package com.accenture.library.exception;

/**
 * Excepción que se lanza cuando un recurso no se encuentra en la biblioteca.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor de la excepción ResourceNotFoundException.
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}