package com.accenture.library.exception;

/**
 * Se lanza cuando se intenta devolver un préstamo que ya está en estado RETURNED (409).
 */
public class LoanAlreadyReturnedException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje en español que indica el préstamo ya devuelto.
     *
     * @param message mensaje de error
     */
    public LoanAlreadyReturnedException(String message) {
        super(message);
    }
}