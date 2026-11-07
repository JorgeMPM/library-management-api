package com.accenture.library.exception;

/**
 * Se lanza cuando se intenta prestar un libro sin stock disponible (409).
 */
public class BookOutOfStockException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje en español que indica el libro sin stock.
     *
     * @param message mensaje de error
     */
    public BookOutOfStockException(String message) {
        super(message);
    }
}