package com.accenture.library.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.accenture.library.dto.LoanResponseDTO;
import com.accenture.library.model.Book;
import com.accenture.library.model.Loan;
import com.accenture.library.model.LoanStatus;

/**
 * Test unitario de LoanMapper.
 */
class LoanMapperTest {

    private final LoanMapper loanMapper = new LoanMapper();

    @Test
    void toResponseDTO_shouldReturnOverdue_whenActiveLoanIsPastExpectedReturnDate() {
        // Given
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Cien años de soledad");
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setLoanDate(LocalDate.now().minusDays(30));
        loan.setExpectedReturnDate(LocalDate.now().minusDays(1));
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setBook(book);

        // When
        LoanResponseDTO dto = loanMapper.toResponseDTO(loan);

        // Then
        assertEquals(LoanStatus.OVERDUE, dto.status());
    }
}
