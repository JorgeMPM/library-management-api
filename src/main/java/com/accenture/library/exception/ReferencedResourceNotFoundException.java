package com.accenture.library.exception;

/**
 * Se lanza cuando un id enviado en el body (categoryId, authorIds o bookId) no existe (400).
 */
public class ReferencedResourceNotFoundException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje en español que indica el id inexistente.
     *
     * @param message mensaje de error
     */
    public ReferencedResourceNotFoundException(String message) {
        super(message);
    }
}