package com.accenture.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.accenture.library.dto.LoanRequestDTO;
import com.accenture.library.exception.BookOutOfStockException;
import com.accenture.library.exception.LoanAlreadyReturnedException;
import com.accenture.library.mapper.LoanMapper;
import com.accenture.library.model.Book;
import com.accenture.library.model.Loan;
import com.accenture.library.model.LoanStatus;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.LoanRepository;

/*
 * Crea la clase de test LoanServiceImplTest con @ExtendWith(MockitoExtension.class), @Mock LoanRepository y BookRepository,
 * @Spy LoanMapper loanMapper = new LoanMapper() y @InjectMocks LoanServiceImpl.
 * 5 tests con Given/When/Then comentados y nombre metodo_shouldX_whenY: crear préstamo decrementa el stock; crear préstamo sin stock
 * lanza BookOutOfStockException; devolver préstamo incrementa el stock; devolver préstamo pone el estado RETURNED
 * devolver préstamo ya devuelto lanza LoanAlreadyReturnedException. — generado por IA
 */
/**
 * Tests unitarios de LoanServiceImpl sin contexto de Spring.
 */
@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Spy
    private LoanMapper loanMapper = new LoanMapper();

    @InjectMocks
    private LoanServiceImpl loanService;

    private Book buildBook(int availableStock) {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Don Quijote de la Mancha");
        book.setAvailableStock(availableStock);
        book.setTotalStock(3);
        return book;
    }

    private Loan buildLoan(LoanStatus status, Book book) {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setLoanDate(LocalDate.now().minusDays(5));
        loan.setExpectedReturnDate(LocalDate.now().plusDays(10));
        loan.setStatus(status);
        loan.setBook(book);
        return loan;
    }

    @Test
    void create_shouldDecrementStock_whenBookHasStock() {
        // Given
        Book book = buildBook(2);
        LoanRequestDTO dto = new LoanRequestDTO(1L, LocalDate.now(), LocalDate.now().plusDays(15));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        loanService.create(dto);

        // Then
        assertEquals(1, book.getAvailableStock());
    }

    @Test
    void create_shouldThrowBookOutOfStockException_whenBookHasNoStock() {
        // Given
        Book book = buildBook(0);
        LoanRequestDTO dto = new LoanRequestDTO(1L, LocalDate.now(), LocalDate.now().plusDays(15));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When / Then
        assertThrows(BookOutOfStockException.class, () -> loanService.create(dto));
    }

    @Test
    void returnLoan_shouldIncrementStock_whenLoanIsActive() {
        // Given
        Book book = buildBook(1);
        Loan loan = buildLoan(LoanStatus.ACTIVE, book);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        loanService.returnLoan(1L);

        // Then
        assertEquals(2, book.getAvailableStock());
    }

    @Test
    void returnLoan_shouldSetStatusReturned_whenLoanIsActive() {
        // Given
        Book book = buildBook(1);
        Loan loan = buildLoan(LoanStatus.ACTIVE, book);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        loanService.returnLoan(1L);

        // Then
        assertEquals(LoanStatus.RETURNED, loan.getStatus());
    }

    @Test
    void returnLoan_shouldThrowLoanAlreadyReturnedException_whenLoanIsReturned() {
        // Given
        Loan loan = buildLoan(LoanStatus.RETURNED, buildBook(1));
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        // When / Then
        assertThrows(LoanAlreadyReturnedException.class, () -> loanService.returnLoan(1L));
    }
}