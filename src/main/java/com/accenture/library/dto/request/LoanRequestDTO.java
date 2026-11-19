package com.accenture.library.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import com.accenture.library.constants.ValidationMessages;

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