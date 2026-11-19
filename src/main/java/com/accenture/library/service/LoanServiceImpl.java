package com.accenture.library.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.library.dto.request.LoanRequestDTO;
import com.accenture.library.dto.response.LoanResponseDTO;
import com.accenture.library.exception.BookOutOfStockException;
import com.accenture.library.exception.LoanAlreadyReturnedException;
import com.accenture.library.exception.ReferencedResourceNotFoundException;
import com.accenture.library.exception.ResourceNotFoundException;
import com.accenture.library.mapper.LoanMapper;
import com.accenture.library.model.Book;
import com.accenture.library.model.Loan;
import com.accenture.library.model.LoanStatus;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.LoanRepository;
import com.accenture.library.constants.ErrorMessages;



/**
 * Implementación del servicio de préstamos de libros.
 * Proporciona métodos para crear, devolver y consultar préstamos.
 */
@Service 
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    /**
     * Constructor de la clase LoanServiceImpl.
     *
     * @param loanRepository el repositorio de préstamos.
     * @param bookRepository el repositorio de libros.
     * @param loanMapper el mapeador de préstamos.
     */
    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDTO> findAll() {
        List<LoanResponseDTO> loanResponseDTOs = new ArrayList<>();
        for (Loan loan : loanRepository.findAllByOrderByIdAsc()) {
            loanResponseDTOs.add(loanMapper.toResponseDTO(loan));
        }
        return loanResponseDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public LoanResponseDTO findById(Long id) {
        return loanMapper.toResponseDTO(findLoanOrThrow(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoanResponseDTO create(LoanRequestDTO dto) {
        Optional<Book> optionalBook = bookRepository.findById(dto.bookId());
        if (optionalBook.isEmpty()) {
            throw new ReferencedResourceNotFoundException(ErrorMessages.REFERENCED_BOOK_NOT_FOUND.formatted(dto.bookId()));
        }
        Book book = optionalBook.get();
        if (book.getAvailableStock() == 0) {
            throw new BookOutOfStockException(ErrorMessages.BOOK_OUT_OF_STOCK.formatted(book.getId()));
        }
        Loan loan = loanMapper.toEntity(dto, book);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setActualReturnDate(null);
        book.setAvailableStock(book.getAvailableStock() - 1);
        bookRepository.save(book);
        return loanMapper.toResponseDTO(loanRepository.save(loan));
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    @Transactional 
    public LoanResponseDTO returnLoan(Long id) {
        Loan loan = findLoanOrThrow(id);
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturnedException(ErrorMessages.LOAN_ALREADY_RETURNED.formatted(id));
        }
        loan.setStatus(LoanStatus.RETURNED);
        loan.setActualReturnDate(LocalDate.now());
        Book book = loan.getBook();
        book.setAvailableStock(book.getAvailableStock() + 1);
        bookRepository.save(book);
        return loanMapper.toResponseDTO(loanRepository.save(loan));
    }

    /**
     * Busca un préstamo por el id de la URL o lanza una excepción 404.
     *
     * @param id id del préstamo
     * @return el préstamo encontrado
     * @throws ResourceNotFoundException si no existe
     */
    private Loan findLoanOrThrow(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);
        if (loan.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessages.LOAN_NOT_FOUND.formatted(id));
        }
        return loan.get();
    }
}