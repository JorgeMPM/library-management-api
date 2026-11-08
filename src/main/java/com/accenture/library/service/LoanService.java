package com.accenture.library.service;

import java.util.List;

import com.accenture.library.dto.request.LoanRequestDTO;
import com.accenture.library.dto.response.LoanResponseDTO;

/*
 * Crea la interfaz LoanService con los métodos: List<LoanResponseDTO> findAll(); LoanResponseDTO findById(Long id)
 * LoanResponseDTO create(LoanRequestDTO dto); LoanResponseDTO returnLoan(Long id).
 * Añade Javadoc en español a la interfaz y a cada método. — generado por IA
 */
public interface LoanService {

    /**
     * Obtiene una lista de todos los préstamos.
     *
     * @return una lista de objetos LoanResponseDTO que representan todos los préstamos.
     */
    List<LoanResponseDTO> findAll();

    /**
     * Busca un préstamo por su ID.
     *
     * @param id el ID del préstamo a buscar.
     * @return un objeto LoanResponseDTO que representa el préstamo encontrado.
     * @throws ResourceNotFoundException si no se encuentra el préstamo con el ID proporcionado.
     */
    LoanResponseDTO findById(Long id);

    /**
     * Crea un nuevo préstamo a partir de los datos proporcionados en el DTO.
     *
     * @param dto un objeto LoanRequestDTO que contiene los datos del préstamo a crear.
     * @return un objeto LoanResponseDTO que representa el préstamo creado.
     * @throws ResourceNotFoundException si no se encuentra el recurso relacionado con el préstamo.
     * @throws BookOutOfStockException si el libro solicitado no está disponible en stock.
     */
    LoanResponseDTO create(LoanRequestDTO dto);

    /**
     * Marca un préstamo como devuelto según su ID.
     *
     * @param id el ID del préstamo a devolver.
     * @return un objeto LoanResponseDTO que representa el préstamo devuelto.
     * @throws ResourceNotFoundException si no se encuentra el préstamo con el ID proporcionado.
     * @throws
     */
    LoanResponseDTO returnLoan(Long id);
}
