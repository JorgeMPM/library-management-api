package com.accenture.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.accenture.library.constants.ValidationMessages;

/*
 * Crea el record AuthorRequestDTO con tres componentes:
 * firstName String con @NotBlank(message = "El nombre del autor es obligatorio")
 * y @Size(max = 100, message = "El nombre del autor no puede superar los 100 caracteres");
 * lastName String con @NotBlank(message = "Los apellidos del autor son obligatorios")
 * y @Size(max = 100, message = "Los apellidos del autor no pueden superar los 100 caracteres");
 * nationality String con @Size(max = 100, message = "La nacionalidad no puede superar los 100 caracteres").
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
/**
 * Representa una solicitud para crear o actualizar un autor en la biblioteca.
 * @param firstName El nombre del autor.
 * @param lastName Los apellidos del autor.
 * @param nationality La nacionalidad del autor.
 */
public record AuthorRequestDTO(
    
    @NotBlank(message = ValidationMessages.AUTHOR_FIRST_NAME_REQUIRED)
    @Size(max = 100, message = ValidationMessages.AUTHOR_FIRST_NAME_SIZE)
    String firstName, 
    
    @NotBlank (message = ValidationMessages.AUTHOR_LAST_NAME_REQUIRED)
    @Size(max = 100, message = ValidationMessages.AUTHOR_LAST_NAME_SIZE)
    String lastName, 
    
    @Size(max = 100, message = ValidationMessages.AUTHOR_NATIONALITY_SIZE)
    String nationality) {
}