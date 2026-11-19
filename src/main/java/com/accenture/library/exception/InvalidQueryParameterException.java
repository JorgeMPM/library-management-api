package com.accenture.library.exception;

/**
 * Excepción que se lanza cuando se detecta un parámetro de consulta inválido en una solicitud.
 * Esta excepción extiende RuntimeException y se utiliza para indicar que los parámetros
 */
public class InvalidQueryParameterException extends RuntimeException {

    /**
     * Constructor de la excepción InvalidQueryParameterException.
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public InvalidQueryParameterException(String message) {
        super(message);
    }
}