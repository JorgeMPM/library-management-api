package com.accenture.library.service;

import java.util.List;

import com.accenture.library.dto.AuthorRequestDTO;
import com.accenture.library.dto.AuthorResponseDTO;

/*
 * Crea la interfaz AuthorService con los métodos: List<AuthorResponseDTO> findAll(); AuthorResponseDTO findById(Long id)
 * AuthorResponseDTO create(AuthorRequestDTO dto); AuthorResponseDTO update(Long id, AuthorRequestDTO dto); void delete(Long id).
 * Añade Javadoc en español a la interfaz y a cada método. — generado por IA
 */
/**
 * Interfaz de servicio para gestionar autores en la biblioteca.
 */
public interface AuthorService {

    /**
     * Obtiene todos los autores disponibles en la biblioteca.
     * @return Una lista de objetos AuthorResponseDTO que representan los autores.
     */
    List<AuthorResponseDTO> findAll();

    /**
     * Obtiene un autor por su ID.
     * @param id El ID del autor a buscar.
     * @return Un objeto AuthorResponseDTO que representa el autor encontrado.
     * @throws ResourceNotFoundException si no existe el autor con el ID proporcionado.
     */
    AuthorResponseDTO findById(Long id);

    /**
     * Crea un nuevo autor en la biblioteca.
     * @param dto El objeto AuthorRequestDTO que contiene los datos del nuevo autor.
     * @return Un objeto AuthorResponseDTO que representa el autor creado.
     */
    AuthorResponseDTO create(AuthorRequestDTO dto);

    /**
     * Actualiza un autor existente en la biblioteca por su ID.
     * @param id El ID del autor a actualizar.
     * @param dto El objeto AuthorRequestDTO que contiene los datos actualizados del autor.
     * @return Un objeto AuthorResponseDTO que representa el autor actualizado.
     * @throws ResourceNotFoundException si no existe el autor con el ID proporcionado.
     */
    AuthorResponseDTO update(Long id, AuthorRequestDTO dto);

    /**
     * Elimina un autor existente de la biblioteca por su ID.
     * @param id El ID del autor a eliminar.
     * @throws ResourceNotFoundException si no existe el autor con el ID proporcionado.
     * @throws ResourceInUseException si tiene libros asignados
     */
    void delete(Long id);

}