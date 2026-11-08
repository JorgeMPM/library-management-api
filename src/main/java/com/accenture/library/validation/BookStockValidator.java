package com.accenture.library.validation;

import com.accenture.library.dto.request.BookRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*
 * Crea la clase BookStockValidator que implementa ConstraintValidator<ValidBookStock, BookRequestDTO>.
 * Método isValid(BookRequestDTO dto, ConstraintValidatorContext context): si dto es null, o availableStock
 * o totalStock son null, devuelve true; si no, devuelve availableStock <= totalStock.
 * Sin lambdas. Añade Javadoc en español a la clase y al método. — generado por IA
 */
/** 
 * Validador para la anotación @ValidBookStock.
 */
public class BookStockValidator implements ConstraintValidator<ValidBookStock, BookRequestDTO> {

    /** 
     * Valida que el stock disponible de un libro no sea mayor que el stock total.
     * @param dto El objeto BookRequestDTO a validar.
     * @param context El contexto de validación.
     * @return true si el stock es válido, false en caso contrario.
     */
    @Override
    public boolean isValid(BookRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        if (dto.availableStock() == null || dto.totalStock() == null) {
            return true;
        }
        return dto.availableStock() <= dto.totalStock();
    }
}