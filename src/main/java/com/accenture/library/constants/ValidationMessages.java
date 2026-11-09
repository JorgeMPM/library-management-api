package com.accenture.library.constants;

/**
 * Mensajes de error de las validaciones de los DTO de entrada (Bean Validation).
 * <p>
 * Son constantes de compilación ({@code static final String} con un literal) porque las anotaciones
 * como {@code @NotBlank(message = ...)} solo admiten valores conocidos al compilar.
 */
public final class ValidationMessages {

    /**
     * Constructor privado: clase de constantes, no se instancia.
     */
    private ValidationMessages() {
    }

    // ---------- Login ----------
    public static final String LOGIN_USERNAME_REQUIRED = "El usuario es obligatorio";
    public static final String LOGIN_PASSWORD_REQUIRED = "La contraseña es obligatoria";

    // ---------- Libros ----------
    public static final String BOOK_TITLE_REQUIRED = "El título es obligatorio";
    public static final String BOOK_TITLE_SIZE = "El título no puede superar los 150 caracteres";
    public static final String BOOK_ISBN_REQUIRED = "El ISBN es obligatorio";
    public static final String BOOK_ISBN_SIZE = "El ISBN no puede superar los 20 caracteres";
    public static final String BOOK_PUBLISHED_YEAR_RANGE = "El año de publicación debe estar entre 1000 y 2100";
    public static final String BOOK_AVAILABLE_STOCK_REQUIRED = "El stock disponible es obligatorio";
    public static final String BOOK_AVAILABLE_STOCK_NEGATIVE = "El stock disponible no puede ser negativo";
    public static final String BOOK_TOTAL_STOCK_REQUIRED = "El stock total es obligatorio";
    public static final String BOOK_TOTAL_STOCK_MIN = "El stock total debe ser al menos 1";
    public static final String BOOK_AUTHOR_ID_REQUIRED = "Los ids de autor no pueden ser nulos";
    public static final String BOOK_AUTHOR_ID_MIN = "Los ids de autor no pueden ser menores que 1";
    public static final String BOOK_STOCK_INVALID = "El stock disponible no puede ser mayor que el stock total";

    // ---------- Autores ----------
    public static final String AUTHOR_FIRST_NAME_REQUIRED = "El nombre del autor es obligatorio";
    public static final String AUTHOR_FIRST_NAME_SIZE = "El nombre del autor no puede superar los 100 caracteres";
    public static final String AUTHOR_LAST_NAME_REQUIRED = "Los apellidos del autor son obligatorios";
    public static final String AUTHOR_LAST_NAME_SIZE = "Los apellidos del autor no pueden superar los 100 caracteres";
    public static final String AUTHOR_NATIONALITY_SIZE = "La nacionalidad no puede superar los 100 caracteres";

    // ---------- Categorías ----------
    public static final String CATEGORY_NAME_REQUIRED = "El nombre de la categoría es obligatorio";
    public static final String CATEGORY_NAME_SIZE = "El nombre de la categoría no puede superar los 100 caracteres";
    public static final String CATEGORY_DESCRIPTION_SIZE = "La descripción no puede superar los 500 caracteres";

    // ---------- Préstamos ----------
    public static final String LOAN_BOOK_ID_REQUIRED = "El id del libro es obligatorio";
    public static final String LOAN_DATE_REQUIRED = "La fecha de préstamo es obligatoria";
    public static final String LOAN_EXPECTED_RETURN_DATE_REQUIRED = "La fecha de devolución prevista es obligatoria";
    public static final String LOAN_DATES_INVALID = "La fecha de devolución prevista no puede ser anterior a la fecha de préstamo";
}
