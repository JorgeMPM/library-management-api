package com.accenture.library.dto;

import java.time.LocalDate;

import com.accenture.library.model.LoanStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * Crea el record LoanResponseDTO con los componentes: id Long, bookId Long, bookTitle String,
 * loanDate LocalDate, expectedReturnDate LocalDate y actualReturnDate LocalDate, los tres con @JsonFormat(pattern = "dd-MM-yyyy"),
 * y status LoanStatus. Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa la respuesta de un préstamo en la biblioteca.
 * @param id El ID del préstamo.
 * @param bookId El ID del libro prestado.
 * @param bookTitle El título del libro prestado.
 * @param loanDate La fecha de préstamo.
 * @param expectedReturnDate La fecha de devolución prevista.
 * @param actualReturnDate La fecha de devolución real.
 * @param status El estado del préstamo.
 */
public record LoanResponseDTO(

        Long id,

        Long bookId,

        String bookTitle,
        
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate loanDate,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate expectedReturnDate,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate actualReturnDate,
        
        LoanStatus status
) {
}