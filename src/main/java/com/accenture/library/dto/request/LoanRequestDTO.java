package com.accenture.library.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import com.accenture.library.constants.ValidationMessages;

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
    
    @NotNull(message = ValidationMessages.LOAN_BOOK_ID_REQUIRED)
    Long bookId, 
    
    @NotNull(message = ValidationMessages.LOAN_DATE_REQUIRED)
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate loanDate, 
    
    @NotNull(message = ValidationMessages.LOAN_EXPECTED_RETURN_DATE_REQUIRED)
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate expectedReturnDate) {
}