package com.accenture.library.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Credenciales para iniciar sesión.
 *
 * @param username nombre de usuario, obligatorio
 * @param password contraseña, obligatoria
 */
public record LoginRequestDTO(
        @NotBlank(message = "El usuario es obligatorio") String username,
        @NotBlank(message = "La contraseña es obligatoria") String password) {
}