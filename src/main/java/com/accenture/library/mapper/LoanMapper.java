package com.accenture.library.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.accenture.library.dto.request.LoanRequestDTO;
import com.accenture.library.dto.response.LoanResponseDTO;
import com.accenture.library.model.Book;
import com.accenture.library.model.Loan;
import com.accenture.library.model.LoanStatus;

/*
 * Crea la clase LoanMapper anotada con @Component.
 * Método toEntity(LoanRequestDTO dto, Book book): crea un Loan nuevo con loanDate y expectedReturnDate del dto y el book recibido.
 * Método toResponseDTO(Loan loan): calcula el status: si es ACTIVE y expectedReturnDate es anterior a LocalDate.now(),
 * usa OVERDUE; si no, el status guardado. Devuelve un LoanResponseDTO con id, book.getId(), book.getTitle(), loanDate,
 * expectedReturnDate, actualReturnDate y el status calculado.
 * Sin lambdas. Añade Javadoc en español a la clase y a cada método. — generado por IA
 */
@Component
public class LoanMapper {

    /**
     * Convierte un LoanRequestDTO y un Book a una entidad Loan.
     * @param dto El objeto LoanRequestDTO a convertir.
     * @param book La entidad Book asociada al préstamo.
     * @return La entidad Loan correspondiente.
     */
    public Loan toEntity(LoanRequestDTO dto, Book book) {
        if (dto == null || book == null) {
            return null;
        }
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setLoanDate(dto.loanDate());
        loan.setExpectedReturnDate(dto.expectedReturnDate());
        return loan;
    }

    /**
     * Convierte una entidad Loan a un LoanResponseDTO, calculando el estado del préstamo.
     * @param loan La entidad Loan a convertir.
     * @return El objeto LoanResponseDTO correspondiente.
     */
    public LoanResponseDTO toResponseDTO(Loan loan) {
        if (loan == null) {
            return null;
        }
        LoanStatus status = loan.getStatus();
        if (status == LoanStatus.ACTIVE && loan.getExpectedReturnDate().isBefore(LocalDate.now())) {
            status = LoanStatus.OVERDUE;
        }
        return new LoanResponseDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getExpectedReturnDate(),
                loan.getActualReturnDate(),
                status
        );
    }
}
