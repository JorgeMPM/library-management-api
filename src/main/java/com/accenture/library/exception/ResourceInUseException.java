package com.accenture.library.exception;

/**
 * Se lanza cuando se intenta eliminar un recurso que tiene otros asociados (409).
 */
public class ResourceInUseException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje en español que indica qué recurso está en uso.
     *
     * @param message mensaje de error
     */
    public ResourceInUseException(String message) {
        super(message);
    }
}