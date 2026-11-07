package com.accenture.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.library.dto.ErrorResponseDTO;
import com.accenture.library.dto.LoanRequestDTO;
import com.accenture.library.dto.LoanResponseDTO;
import com.accenture.library.service.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*
 * Crea el @RestController LoanController con @RequestMapping("/api/v1/loans") y @Tag(name = "Préstamos"),
 * con un campo final LoanService inyectado por constructor.
 * GET: findAll 200. GET /{id}: 200. POST con @Valid @RequestBody LoanRequestDTO: 201. PUT /{id}/return sin body: 200.
 * @ApiResponses en español: GET 200 y 401; GET /{id} 200, 404 y 401; POST 201, 400, 409 y 401; PUT return 200, 404, 409 y 401
 * en 400, 401, 404 y 409 content con schema ErrorResponseDTO.
 * Sin try/catch ni lambdas. Javadoc en español en la clase, el constructor y cada método. — generado por IA
 */
/**
 * Controlador REST para gestionar los préstamos de libros en la biblioteca.
 */
@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Préstamos", description = "Préstamo y devolución de libros")
public class LoanController {

    private final LoanService loanService;

    /**
     * Crea el controller con su servicio.
     *
     * @param loanService servicio de préstamos
     */
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Lista todos los préstamos.
     *
     * @return 200 con los préstamos ordenados por id
     */
    @GetMapping
    @Operation(summary = "Listar préstamos", description = "Devuelve todos los préstamos ordenados por id, con OVERDUE calculado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de préstamos"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<List<LoanResponseDTO>> findAll() {
        return ResponseEntity.ok(loanService.findAll());
    }

    /**
     * Obtiene un préstamo por su id.
     *
     * @param id id del préstamo
     * @return 200 con el préstamo
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener préstamo", description = "Devuelve un préstamo por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<LoanResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.findById(id));
    }

    /**
     * Crea un préstamo.
     *
     * @param dto datos del préstamo
     * @return 201 con el préstamo creado
     */
    @PostMapping
    @Operation(summary = "Crear préstamo", description = "Presta un libro y decrementa su stock disponible")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Préstamo creado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos o libro inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El libro no tiene stock disponible",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<LoanResponseDTO> create(@Valid @RequestBody LoanRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(dto));
    }

    /**
     * Devuelve un libro prestado.
     *
     * @param id id del préstamo
     * @return 200 con el préstamo actualizado
     */
    @PutMapping("/{id}/return")
    @Operation(summary = "Devolver préstamo", description = "Marca el préstamo como devuelto e incrementa el stock del libro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo devuelto"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El préstamo ya estaba devuelto",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o caducado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<LoanResponseDTO> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnLoan(id));
    }
}