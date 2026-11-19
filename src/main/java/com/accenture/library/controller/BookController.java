package com.accenture.library.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.library.dto.request.BookRequestDTO;
import com.accenture.library.dto.response.BookResponseDTO;
import com.accenture.library.dto.response.ErrorResponseDTO;
import com.accenture.library.dto.response.PageResponseDTO;
import com.accenture.library.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar los libros en la biblioteca.
 */
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Libros", description = "Operaciones relacionadas con los libros de la biblioteca")
public class BookController {

    private final BookService bookService;

    /**
     * Constructor de la clase BookController.
     *
     * @param bookService el servicio de libros.
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Obtiene una lista paginada de libros, con opciones de filtrado y ordenamiento.
     *
     * @param page       el número de página (opcional).
     * @param size       el tamaño de la página (opcional).
     * @param categoryId el ID de la categoría para filtrar los libros (opcional).
     * @param authorId   el ID del autor para filtrar los libros (opcional).
     * @param available  indica si se deben incluir solo los libros disponibles (opcional).
     * @param request    el objeto HttpServletRequest para obtener parámetros adicionales, como sort.
     * @return una respuesta HTTP con un objeto PageResponseDTO que contiene la lista de libros.
     */
    @GetMapping
    @Operation(summary = "Listar libros", description = "Devuelve los libros paginados, ordenados y filtrados")
    @Parameter(name = "sort", in = ParameterIn.QUERY, example = "title,asc",
            description = "Ordenación campo o campo,dirección. Campos: title, isbn, publishedYear, availableStock, totalStock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de libros"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación, ordenación o filtro no válidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean available,
            @Parameter(hidden = true) HttpServletRequest request) {
        String[] sort = request.getParameterValues("sort");
        return ResponseEntity.ok(bookService.findAll(page, size, sort, categoryId, authorId, available));
    }

    /**
     * Obtiene un libro por su id.
     *
     * @param id id del libro
     * @return 200 con el libro
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro", description = "Devuelve un libro por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    /**
     * Crea un libro.
     *
     * @param dto datos del libro
     * @return 201 con el libro creado
     */
    @PostMapping
    @Operation(summary = "Crear libro", description = "Crea un libro con ISBN único")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos o categoría/autor inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un libro con ese ISBN",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    /**
     * Actualiza un libro.
     *
     * @param id  id del libro
     * @param dto nuevos datos
     * @return 200 con el libro actualizado
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro", description = "Actualiza un libro según la semántica de PUT del enunciado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos o categoría/autor inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El ISBN ya lo usa otro libro",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    /**
     * Elimina un libro.
     *
     * @param id id del libro
     * @return 204 sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro", description = "Elimina un libro sin préstamos (solo ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Libro eliminado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El libro tiene préstamos asociados",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Solo el rol ADMIN puede eliminar",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

