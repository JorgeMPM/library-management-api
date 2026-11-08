package com.accenture.library.service;

import java.util.List;

import com.accenture.library.dto.request.CategoryRequestDTO;
import com.accenture.library.dto.response.CategoryResponseDTO;

/*
 * Crea la interfaz CategoryService con los métodos:
 * List<CategoryResponseDTO> findAll()
 * CategoryResponseDTO create(CategoryRequestDTO dto)
 * void delete(Long id).
 * Añade Javadoc en español a la interfaz y a cada método. — generado por IA
 */
/**
 * Interfaz de servicio para gestionar categorías en la biblioteca.
 */
public interface CategoryService {

    /**
     * Obtiene todas las categorías disponibles en la biblioteca.
     * @return Una lista de objetos CategoryResponseDTO que representan las categorías.
     */
    List<CategoryResponseDTO> findAll();

    /**
     * Crea una nueva categoría en la biblioteca.
     * @param dto El objeto CategoryRequestDTO que contiene los datos de la nueva categoría.
     * @return Un objeto CategoryResponseDTO que representa la categoría creada.
     */
    CategoryResponseDTO create(CategoryRequestDTO dto);

    /**
     * Elimina una categoría existente de la biblioteca por su ID.
     * @param id El ID de la categoría a eliminar.
     * @throws ResourceNotFoundException si no existe la categoría con el ID proporcionado.
     * @throws com.accenture.library.exception.ResourceInUseException si tiene libros asignados
     */
    void delete(Long id);

}