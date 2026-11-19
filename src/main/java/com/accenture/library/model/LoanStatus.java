package com.accenture.library.model;

/**
 * Enum que representa el estado de un préstamo en la biblioteca.
 * Contiene los valores ACTIVE, RETURNED y OVERDUE.
 * El estado OVERDUE no se guarda en la base de datos, sino que se calcula al responder.
 */
public enum LoanStatus {
    /**
     * Estado de préstamo activo.
     */
    ACTIVE,

    /**
     * Estado de préstamo devuelto.
     */
    RETURNED,

    /**
     * Estado de préstamo vencido.
     * Este estado no se guarda en la base de datos, se calcula al responder.
     */
    OVERDUE
}