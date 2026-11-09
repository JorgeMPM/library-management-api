package com.accenture.library.dto.request;

import java.util.List;

import com.accenture.library.validation.ValidBookStock;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.accenture.library.constants.ValidationMessages;

/*
 * Crea el record BookRequestDTO con @Schema(description, example) en español en cada componente:
 * title String con @NotBlank(message = "El título es obligatorio") y @Size(max = 150, message = "El título no puede superar los 150 caracteres")
 * isbn String con @NotBlank(message = "El ISBN es obligatorio") y @Size(max = 20, message = "El ISBN no puede superar los 20 caracteres")
 * publishedYear Integer con @Min(value = 1000, message = "El año de publicación debe estar entre 1000 y 2100") y @Max(value = 2100, mismo mensaje)
 * availableStock Integer con @NotNull(message = "El stock disponible es obligatorio") y @Min(value = 0, message = "El stock disponible no puede ser negativo")
 * totalStock Integer con @NotNull(message = "El stock total es obligatorio") y @Min(value = 1, message = "El stock total debe ser al menos 1")
 * categoryId Long; authorIds List<Long>.
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa una solicitud para crear o actualizar un libro en la biblioteca.
 * @param title El título del libro.
 * @param isbn El ISBN del libro.
 * @param publishedYear El año de publicación del libro.
 * @param availableStock El stock disponible del libro.
 * @param totalStock El stock total del libro.
 * @param categoryId El ID de la categoría del libro.
 * @param authorIds La lista de IDs de los autores del libro.
 */
@ValidBookStock 
public record BookRequestDTO(

        @Schema (description = "El título del libro", example = "Cien años de soledad")
        @NotBlank(message = ValidationMessages.BOOK_TITLE_REQUIRED)
        @Size(max = 150, message = ValidationMessages.BOOK_TITLE_SIZE)
        String title,

        @Schema (description = "El ISBN del libro", example = "978-3-16-148410-0")
        @NotBlank(message = ValidationMessages.BOOK_ISBN_REQUIRED)
        @Size(max = 20, message = ValidationMessages.BOOK_ISBN_SIZE)
        String isbn,

        @Schema (description = "El año de publicación del libro", example = "1967")
        @Min(value = 1000, message = ValidationMessages.BOOK_PUBLISHED_YEAR_RANGE)
        @Max(value = 2100, message = ValidationMessages.BOOK_PUBLISHED_YEAR_RANGE)
        Integer publishedYear,

        @Schema (description = "El stock disponible del libro", example = "5")
        @NotNull(message = ValidationMessages.BOOK_AVAILABLE_STOCK_REQUIRED)
        @Min(value = 0, message = ValidationMessages.BOOK_AVAILABLE_STOCK_NEGATIVE)
        Integer availableStock,

        @Schema (description = "El stock total del libro", example = "10")
        @NotNull(message = ValidationMessages.BOOK_TOTAL_STOCK_REQUIRED)
        @Min(value = 1, message = ValidationMessages.BOOK_TOTAL_STOCK_MIN)
        Integer totalStock,

        @Schema (description = "El ID de la categoría del libro", example = "1")
        Long categoryId,
        
        @Schema (description = "La lista de IDs de los autores del libro", example = "[1, 2]")
        List<@NotNull(message = ValidationMessages.BOOK_AUTHOR_ID_REQUIRED) 
             @Min(value = 1, message = ValidationMessages.BOOK_AUTHOR_ID_MIN) 
             Long> authorIds) {
}