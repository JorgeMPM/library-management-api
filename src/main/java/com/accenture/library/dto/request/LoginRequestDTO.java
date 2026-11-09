package com.accenture.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import com.accenture.library.constants.ValidationMessages;

/**
 * Credenciales para iniciar sesión.
 *
 * @param username nombre de usuario, obligatorio
 * @param password contraseña, obligatoria
 */
public record LoginRequestDTO(
        @NotBlank(message = ValidationMessages.LOGIN_USERNAME_REQUIRED) String username,
        @NotBlank(message = ValidationMessages.LOGIN_PASSWORD_REQUIRED) String password) {
}