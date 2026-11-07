package com.accenture.library.dto;

/*
 * Crea el record CategoryRequestDTO con dos componentes:
 * name String con @NotBlank(message = "El nombre de la categoría es obligatorio")
 * y @Size(max = 100, message = "El nombre de la categoría no puede superar los 100 caracteres");
 * description String con @Size(max = 500, message = "La descripción no puede superar los 500 caracteres").
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa una solicitud para crear o actualizar una categoría en la biblioteca.
 * @param name El nombre de la categoría.
 * @param description La descripción de la categoría.
 */
public record CategoryRequestDTO(String name, String description) {
}
