package com.accenture.library.dto.request;

import com.accenture.library.constants.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
public record CategoryRequestDTO(
        @NotBlank(message = ValidationMessages.CATEGORY_NAME_REQUIRED)
        @Size(max = 100, message = ValidationMessages.CATEGORY_NAME_SIZE)
        String name,

        @Size(max = 500, message = ValidationMessages.CATEGORY_DESCRIPTION_SIZE)
        String description) {
}
