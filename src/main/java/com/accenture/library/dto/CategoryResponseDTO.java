package com.accenture.library.dto;

/*
 * Crea el record CategoryResponseDTO con los componentes id Long, name String y description String.
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa la respuesta de una categoría en la biblioteca.
 * @param id El ID de la categoría.
 * @param name El nombre de la categoría.
 * @param description La descripción de la categoría.
 */
public record CategoryResponseDTO(Long id, String name, String description) {
}