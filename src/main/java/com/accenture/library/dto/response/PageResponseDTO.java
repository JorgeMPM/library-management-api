package com.accenture.library.dto.response;

import java.util.List;

/*
 * Crea el record genérico PageResponseDTO<T> con los componentes: content List<T>, pageNumber int, pageSize int,
 * totalElements long, totalPages int y last boolean.
 * Añade Javadoc en español al record con un @param por componente y @param <T> para el tipo. — generado por IA
 */
/**
 * Representa una respuesta de paginación genérica para cualquier tipo de contenido.
 * @param <T> El tipo de contenido de la página.
 * @param content La lista de elementos de la página.
 * @param pageNumber El número de la página.
 * @param pageSize El tamaño de la página.
 * @param totalElements El número total de elementos.
 * @param totalPages El número total de páginas.
 * @param last Indica si es la última página.
 */
public record PageResponseDTO<T>(
    
        List<T> content,

        int pageNumber,

        int pageSize,

        long totalElements,

        int totalPages,
        
        boolean last
) {
}
