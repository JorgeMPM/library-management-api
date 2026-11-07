package com.accenture.library.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

/*
 * Crea el record LoanRequestDTO con tres componentes:
 * bookId Long con @NotNull(message = "El id del libro es obligatorio")
 * loanDate LocalDate con @NotNull(message = "La fecha de préstamo es obligatoria") y @JsonFormat(pattern = "dd-MM-yyyy")
 * expectedReturnDate LocalDate con @NotNull(message = "La fecha de devolución prevista es obligatoria") y @JsonFormat(pattern = "dd-MM-yyyy").
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa una solicitud para crear un préstamo en la biblioteca.
 * @param bookId El ID del libro que se va a prestar.
 * @param loanDate La fecha de préstamo.
 * @param expectedReturnDate La fecha de devolución prevista.
 */
public record LoanRequestDTO(
    
    @NotNull(message = "El id del libro es obligatorio")
    Long bookId, 
    
    @NotNull(message = "La fecha de préstamo es obligatoria")
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate loanDate, 
    
    @NotNull(message = "La fecha de devolución prevista es obligatoria")
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate expectedReturnDate) {
}