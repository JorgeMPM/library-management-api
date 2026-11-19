package com.accenture.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.accenture.library.constants.ValidationMessages;

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