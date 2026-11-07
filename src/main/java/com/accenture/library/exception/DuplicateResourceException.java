package com.accenture.library.exception;

/**
 * Se lanza cuando se intenta guardar un valor único repetido, como un ISBN o un nombre de categoría (409).
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje en español que indica el valor repetido.
     *
     * @param message mensaje de error
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}
