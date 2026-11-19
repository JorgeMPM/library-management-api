package com.accenture.library.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Representa la respuesta de error que se devuelve al cliente en caso de una excepción.
 * 
 * @param timestamp
 * @param status
 * @param error
 * @param message
 * @param path
 */
public record ErrorResponseDTO(
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path) {
}