package com.accenture.library.dto.response;

/*
 * Crea el record AuthorResponseDTO con los componentes id Long, firstName String, lastName String y nationality String.
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa la respuesta de un autor en la biblioteca.
 * @param id El ID del autor.
 * @param firstName El nombre del autor.
 * @param lastName Los apellidos del autor.
 * @param nationality La nacionalidad del autor.
 */
public record AuthorResponseDTO(Long id, String firstName, String lastName, String nationality) {
}