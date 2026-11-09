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
 * Crea la anotación de validación de clase ValidBookStock con @Target(ElementType.TYPE), @Retention(RetentionPolicy.RUNTIME),
 * @Documented y @Constraint(validatedBy = BookStockValidator.class).
 * Métodos: String message() default "El stock disponible no puede ser mayor que el stock total";
 * Class<?>[] groups() default {}; Class<? extends Payload>[] payload() default {}.
 * Añade Javadoc en español a la anotación y a cada método. — generado por IA
 */
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