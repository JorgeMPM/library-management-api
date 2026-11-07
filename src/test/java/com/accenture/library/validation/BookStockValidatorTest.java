package com.accenture.library.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.accenture.library.dto.BookRequestDTO;

/**
 * Test unitario del validador de clase de BookRequestDTO.
 */
class BookStockValidatorTest {

    private final BookStockValidator validator = new BookStockValidator();

    @Test
    void isValid_shouldReturnFalse_whenAvailableStockIsGreaterThanTotalStock() {
        // Given
        BookRequestDTO dto = new BookRequestDTO("Título", "978-1111111111", 2000, 5, 3, null, null);

        // When
        boolean result = validator.isValid(dto, null);

        // Then
        assertFalse(result);
    }
}