package com.accenture.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.library.dto.request.CategoryRequestDTO;
import com.accenture.library.dto.response.CategoryResponseDTO;
import com.accenture.library.exception.DuplicateResourceException;
import com.accenture.library.exception.ResourceInUseException;
import com.accenture.library.exception.ResourceNotFoundException;
import com.accenture.library.mapper.CategoryMapper;
import com.accenture.library.model.Category;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.CategoryRepository;

import org.springframework.transaction.annotation.Transactional;
import com.accenture.library.constants.ErrorMessages;

/**
 * Implementación del servicio para gestionar categorías en la biblioteca.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Constructor que inyecta los repositorios y el mapeador de categorías.
     * @param categoryRepository El repositorio de categorías.
     * @param bookRepository El repositorio de libros.
     * @param categoryMapper El mapeador de categorías.
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        List<CategoryResponseDTO> dtos = new ArrayList<>();
        for (Category category : categoryRepository.findAllByOrderByIdAsc()) {
            dtos.add(categoryMapper.toResponseDTO(category));
        }
        return dtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        if (categoryRepository.existsByName(dto.name())) {
            throw new DuplicateResourceException(ErrorMessages.CATEGORY_DUPLICATE_NAME.formatted(dto.name()));
        }
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toResponseDTO(categoryRepository.save(category));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.formatted(id));
        }
        if (bookRepository.existsByCategoryId(id)) {
            throw new ResourceInUseException(ErrorMessages.CATEGORY_IN_USE.formatted(id));
        }
        categoryRepository.deleteById(id);
    }

}