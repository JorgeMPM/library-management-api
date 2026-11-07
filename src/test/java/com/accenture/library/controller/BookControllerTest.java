package com.accenture.library.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.accenture.library.dto.BookResponseDTO;
import com.accenture.library.exception.ResourceNotFoundException;
import com.accenture.library.security.JwtAuthenticationFilter;
import com.accenture.library.security.JwtService;
import com.accenture.library.security.SecurityConfig;
import com.accenture.library.service.BookService;

/**
 * Tests de la capa Controller de libros con MockMvc y la configuración de seguridad real, sin MySQL.
 */
@WebMvcTest(BookController.class)
@Import({ SecurityConfig.class, JwtAuthenticationFilter.class, JwtService.class })
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById_shouldReturn200_whenBookExists() throws Exception {
        // Given
        BookResponseDTO book = new BookResponseDTO(1L, "Don Quijote de la Mancha", "978-0000000001",
                1605, 2, 3, 1L, "Novela", List.of());
        when(bookService.findById(1L)).thenReturn(book);

        // When / Then
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Don Quijote de la Mancha"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById_shouldReturn404_whenBookDoesNotExist() throws Exception {
        // Given
        when(bookService.findById(99L)).thenThrow(new ResourceNotFoundException("Libro con id 99 no encontrado"));

        // When / Then
        mockMvc.perform(get("/api/v1/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Libro con id 99 no encontrado"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_shouldReturn400_whenTitleIsBlank() throws Exception {
        // Given
        String body = "{\"title\":\"\",\"isbn\":\"978-1111111111\",\"availableStock\":1,\"totalStock\":1}";

        // When / Then
        mockMvc.perform(post("/api/v1/books").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("El título es obligatorio")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void delete_shouldReturn403_whenUserHasRoleUser() throws Exception {
        // Given: un usuario autenticado con rol USER

        // When / Then
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isForbidden());
    }
}