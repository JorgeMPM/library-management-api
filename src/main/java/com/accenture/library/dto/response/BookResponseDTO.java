package com.accenture.library.dto.response;

import java.util.List;

/**
 * Representa la respuesta de un libro en la biblioteca.
 * @param id El ID del libro.
 * @param title El título del libro.
 * @param isbn El ISBN del libro.
 * @param publishedYear El año de publicación del libro.
 * @param availableStock El stock disponible del libro.
 * @param totalStock El stock total del libro.
 * @param categoryId El ID de la categoría del libro.
 * @param categoryName El nombre de la categoría del libro.
 * @param authors La lista de autores del libro.
 */
public record BookResponseDTO(

        Long id,

        String title,

        String isbn,

        Integer publishedYear,

        Integer availableStock,

        Integer totalStock,

        Long categoryId,

        String categoryName,
        
        List<AuthorResponseDTO> authors
) {
}