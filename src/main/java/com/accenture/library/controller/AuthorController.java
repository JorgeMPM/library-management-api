package com.accenture.library.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.library.dto.request.AuthorRequestDTO;
import com.accenture.library.dto.response.AuthorResponseDTO;
import com.accenture.library.dto.response.ErrorResponseDTO;
import com.accenture.library.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


/** 
 * Controlador para la gestión de autores.
 */
@RestController
@RequestMapping("/api/v1/authors")  
@Tag(name = "Autores", description = "Operaciones relacionadas con los autores de libros")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Constructor de la clase AuthorController.
     *
     * @param authorService el servicio de autores.
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Obtiene todos los autores.
     *
     * @return una lista de todos los autores.
     */
    @GetMapping
    @Operation (summary = "Obtener todos los autores", description = "Devuelve una lista de todos los autores disponibles en la biblioteca.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autores obtenidos correctamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<List<AuthorResponseDTO>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    /**
     * Busca un autor por su ID.
     *
     * @param id el ID del autor a buscar.
     * @return el autor encontrado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un autor por su ID", description = "Devuelve un autor específico según su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    /**
     * Crea un nuevo autor.
     *
     * @param authorRequestDTO los datos del autor a crear.
     * @return el autor creado.
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo autor", description = "Crea un nuevo autor con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Autor creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Ya existe un autor con ese nombre y apellidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(authorRequestDTO));
    }

    /**
     * Actualiza un autor existente.
     *
     * @param id el ID del autor a actualizar.
     * @param authorRequestDTO los datos actualizados del autor.
     * @return el autor actualizado.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un autor", description = "Actualiza los datos de un autor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Ya existe un autor con ese nombre y apellidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
        ,
    })
    public ResponseEntity<AuthorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        return ResponseEntity.ok(authorService.update(id, authorRequestDTO));
    }

    /**
     * Elimina un autor.
     *
     * @param id el ID del autor a eliminar.
     * @return una respuesta indicando si se eliminó correctamente.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un autor", description = "Elimina un autor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Autor eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Conflicto al eliminar el autor", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}