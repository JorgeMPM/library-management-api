package com.accenture.library.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * Crea el record ErrorResponseDTO con los componentes: timestamp LocalDateTime con @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss"),
 * status int, error String, message String y path String.
 * Añade Javadoc en español al record con un @param por componente. — generado por IA
 */
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