package com.accenture.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.library.dto.CategoryRequestDTO;
import com.accenture.library.dto.CategoryResponseDTO;
import com.accenture.library.dto.ErrorResponseDTO;
import com.accenture.library.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*
 * Crea el @RestController en la clase CategoryController con @RequestMapping("/api/v1/categories") y @Tag(name = "Categorías"),
 * con un campo final CategoryService inyectado por constructor.
 * GET: findAll, devuelve 200. POST con @Valid @RequestBody CategoryRequestDTO: devuelve 201 con el creado.
 * DELETE /{id}: devuelve 204 sin body.
 * Cada método con @Operation(summary) y @ApiResponses en español: GET 200 y 401; POST 201, 400, 409 y 401
 * DELETE 204, 404, 409, 401 y 403; en 400, 401, 403, 404 y 409 content con schema ErrorResponseDTO.
 * Sin try/catch ni lambdas. Javadoc en español en la clase, el constructor y cada método. — generado por IA
 */
/**
 * Controlador REST para gestionar las categorías de libros en la biblioteca.
 */
@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías de libros")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructor de la clase CategoryController.
     *
     * @param categoryService el servicio de categorías.
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Obtiene todas las categorías.
     *
     * @return una lista de todas las categorías.
     */
    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías de libros disponibles en la biblioteca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * Crea una nueva categoría.
     *
     * @param categoryRequestDTO los datos de la nueva categoría.
     * @return la categoría creada.
     */
    @PostMapping
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría de libros en la biblioteca con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la categoría inválidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "La categoría ya existe", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryRequestDTO));
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id el ID de la categoría a eliminar.
     * @return un mensaje indicando que la categoría fue eliminada.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría por su ID", description = "Elimina una categoría de libros de la biblioteca según su ID. Devuelve un código de estado 204 si la eliminación fue exitosa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "La categoría está siendo utilizada", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}