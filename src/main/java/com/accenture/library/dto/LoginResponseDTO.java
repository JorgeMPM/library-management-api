package com.accenture.library.dto;

/**
 * Respuesta del login con el token JWT.
 *
 * @param token token JWT que se envía en la cabecera Authorization como Bearer
 */
public record LoginResponseDTO(String token) {
}
