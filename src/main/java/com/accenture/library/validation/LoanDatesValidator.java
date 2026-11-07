package com.accenture.library.validation;

import com.accenture.library.dto.LoanRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*
 * Crea la clase LoanDatesValidator que implementa ConstraintValidator<ValidLoanDates, LoanRequestDTO>.
 * Método isValid(LoanRequestDTO dto, ConstraintValidatorContext context): si dto es null, o loanDate
 * o expectedReturnDate son null, devuelve true; si no, devuelve !expectedReturnDate.isBefore(loanDate).
 * Sin lambdas. Añade Javadoc en español a la clase y al método. — generado por IA
 */
/**
 * Validador para las fechas de préstamo.
 */
public class LoanDatesValidator implements ConstraintValidator<ValidLoanDates, LoanRequestDTO> {

    /**    
     * Valida que la fecha de devolución prevista no sea anterior a la fecha de préstamo.
     * @param dto El objeto LoanRequestDTO a validar.
     * @param context El contexto de validación.
     * @return true si las fechas son válidas, false en caso contrario.
     */
    @Override
    public boolean isValid(LoanRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.loanDate() == null || dto.expectedReturnDate() == null) {
            return true;
        }
        return !dto.expectedReturnDate().isBefore(dto.loanDate());
    }

}