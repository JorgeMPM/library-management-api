package com.accenture.library.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.accenture.library.constants.ValidationMessages;

/*
 * Crea la anotación de validación de clase ValidLoanDates con @Target(ElementType.TYPE), @Retention(RetentionPolicy.RUNTIME),
 * @Documented y @Constraint(validatedBy = LoanDatesValidator.class).
 * Métodos: String message() default "La fecha de devolución prevista no puede ser anterior a la fecha de préstamo";
 * Class<?>[] groups() default {}; Class<? extends Payload>[] payload() default {}.
 * Añade Javadoc en español a la anotación y a cada método. — generado por IA
 */
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