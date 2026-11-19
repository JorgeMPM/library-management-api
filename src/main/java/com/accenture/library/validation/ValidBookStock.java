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
 * Anotación de validación para asegurar que el stock disponible de un libro no sea mayor que el stock total.
 */ 
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BookStockValidator.class)
public @interface ValidBookStock {
    
    /**
     * Mensaje de error por defecto.
     * @return El mensaje de error.
     */
    String message() default ValidationMessages.BOOK_STOCK_INVALID;

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