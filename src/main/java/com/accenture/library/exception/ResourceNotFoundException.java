package com.accenture.library.exception;

/*
 * Crea la excepción ResourceNotFoundException que extiende RuntimeException,
 * con un constructor que recibe String message y llama a super(message).
 * Añade Javadoc en español a la clase y al constructor. — generado por IA
 */
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