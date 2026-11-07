package com.accenture.library.exception;

/*
 * Crea la excepción InvalidQueryParameterException que extiende RuntimeException,
 * con un constructor que recibe String message y llama a super(message).
 * Añade Javadoc en español a la clase y al constructor. — generado por IA
 */
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