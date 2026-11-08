package com.accenture.library.service;

import com.accenture.library.dto.request.BookRequestDTO;
import com.accenture.library.dto.response.BookResponseDTO;
import com.accenture.library.dto.response.PageResponseDTO;

/*
 * Crea la interfaz BookService con los métodos:
 * PageResponseDTO<BookResponseDTO> findAll(Integer page, Integer size, String[] sort, Long categoryId, Long authorId, Boolean available)
 * BookResponseDTO findById(Long id); BookResponseDTO create(BookRequestDTO dto)
 * BookResponseDTO update(Long id, BookRequestDTO dto); void delete(Long id).
 * Añade Javadoc en español a la interfaz y a cada método. — generado por IA
 */
/**
 * Interfaz de servicio para gestionar libros en la biblioteca.
 */
public interface BookService {

    /**
     * Obtiene una lista paginada de libros, con opciones de filtrado y ordenamiento.
     * @param page El número de página a obtener.
     * @param size El tamaño de la página.
     * @param sort Los criterios de ordenamiento.
     * @param categoryId El ID de la categoría para filtrar los libros (opcional).
     * @param authorId El ID del autor para filtrar los libros (opcional).
     * @param available Indica si se deben incluir solo libros disponibles (opcional).
     * @return Un objeto PageResponseDTO que contiene los libros encontrados.
     * @throws InvalidParameterException si los parámetros de paginación o filtrado son inválidos.
     */
    PageResponseDTO<BookResponseDTO> findAll(Integer page, Integer size, String[] sort, Long categoryId, Long authorId, Boolean available);

    /**
     * Obtiene un libro por su ID.
     * @param id El ID del libro a buscar.
     * @return Un objeto BookResponseDTO que representa el libro encontrado.
     * @throws ResourceNotFoundException si no existe el libro con el ID proporcionado.
     */
    BookResponseDTO findById(Long id);

    /**
     * Crea un nuevo libro en la biblioteca.
     * @param dto El objeto BookRequestDTO que contiene los datos del nuevo libro.
     * @return Un objeto BookResponseDTO que representa el libro creado.
     * @throws ReferenceNotFoundException si la categoría o los autores referenciados no existen.
     * @throws DuplicateResourceException si ya existe un libro con el mismo ISBN.
     */
    BookResponseDTO create(BookRequestDTO dto);

    /**
     * Actualiza un libro existente en la biblioteca por su ID.
     * @param id El ID del libro a actualizar.
     * @param dto El objeto BookRequestDTO que contiene los datos actualizados del libro.
     * @return Un objeto BookResponseDTO que representa el libro actualizado.
     * @throws ResourceNotFoundException si no existe el libro con el ID proporcionado.
     * @throws ReferenceNotFoundException si la categoría o los autores referenciados no existen.
     * @throws DuplicateResourceException si ya existe un libro con el mismo ISBN.
     */
    BookResponseDTO update(Long id, BookRequestDTO dto);

    /**
     * Elimina un libro existente de la biblioteca por su ID.
     * @param id El ID del libro a eliminar.
     * @throws ResourceNotFoundException si no existe el libro con el ID proporcionado.
     * @throws ResourceInUseException si el libro está actualmente prestado y no puede ser eliminado.
     */
    void delete(Long id);

}