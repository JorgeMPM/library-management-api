package com.accenture.library.dto;

import java.util.List;

import com.accenture.library.validation.ValidBookStock;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150, message = "El título no puede superar los 150 caracteres")
        String title,

        @Schema (description = "El ISBN del libro", example = "978-3-16-148410-0")
        @NotBlank(message = "El ISBN es obligatorio")
        @Size(max = 20, message = "El ISBN no puede superar los 20 caracteres")
        String isbn,

        @Schema (description = "El año de publicación del libro", example = "1967")
        @Min(value = 1000, message = "El año de publicación debe estar entre 1000 y 2100")
        @Max(value = 2100, message = "El año de publicación debe estar entre 1000 y 2100")
        Integer publishedYear,

        @Schema (description = "El stock disponible del libro", example = "5")
        @NotNull(message = "El stock disponible es obligatorio")
        @Min(value = 0, message = "El stock disponible no puede ser negativo")
        Integer availableStock,

        @Schema (description = "El stock total del libro", example = "10")
        @NotNull(message = "El stock total es obligatorio")
        @Min(value = 1, message = "El stock total debe ser al menos 1")
        Integer totalStock,

        @Schema (description = "El ID de la categoría del libro", example = "1")
        Long categoryId,
        
        @Schema (description = "La lista de IDs de los autores del libro", example = "[1, 2]")
        List<@NotNull(message = "Los ids de autor no pueden ser nulos") 
             @Min(value = 1, message = "Los ids de autor no pueden ser menores que 1") 
             Long> authorIds) {
}