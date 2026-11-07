package com.accenture.library.security;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test unitario de la generación de tokens JWT.
 */
class JwtServiceTest {

    private final JwtService jwtService = new JwtService("clave-de-test-con-al-menos-32-caracteres", 86400000L);

    @Test
    void generateToken_shouldContainUsernameAndRole_whenUserIsAdmin() {
        // Given
        String username = "admin";
        String role = "ROLE_ADMIN";

        // When
        String token = jwtService.generateToken(username, role);

        // Then
        assertAll(
                () -> assertEquals(username, jwtService.extractUsername(token)),
                () -> assertEquals(role, jwtService.extractRole(token)));
    }
}
