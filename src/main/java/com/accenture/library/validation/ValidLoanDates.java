package com.accenture.library.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.accenture.library.constants.ValidationMessages;

/** 
 * Anotación de validación para asegurar que la fecha de devolución prevista no sea anterior a la fecha de préstamo.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LoanDatesValidator.class)
public @interface ValidLoanDates {
    
    /**
     * Mensaje de error por defecto.
     * @return El mensaje de error.
     */
    String message() default ValidationMessages.LOAN_DATES_INVALID;

    /**
     * Grupos de validación.
     * @return Los grupos de validación.
     */
    Class<?>[] groups() default {};

    /**
     * Payload de la anotación.
     * @return El payload de la anotación.
     */
    Class<? extends Payload>[] payload() default {};
}   