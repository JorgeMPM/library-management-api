package com.accenture.library.mapper;

import org.springframework.stereotype.Component;

import com.accenture.library.dto.request.CategoryRequestDTO;
import com.accenture.library.dto.response.CategoryResponseDTO;
import com.accenture.library.model.Category;

/*
 * Crea la clase CategoryMapper anotada con @Component.
 * Método toEntity(CategoryRequestDTO dto): crea un Category nuevo y le asigna name y description del dto.
 * Método toResponseDTO(Category category): devuelve un CategoryResponseDTO con id, name y description.
 * Sin lambdas. Añade Javadoc en español a la clase y a cada método. — generado por IA
 */
/**
 * Mapeador para convertir entre CategoryRequestDTO, Category y CategoryResponseDTO.
 */
@Component
public class CategoryMapper {
  
   
    /**
     * Convierte un CategoryRequestDTO a una entidad Category.
     * @param dto El objeto CategoryRequestDTO a convertir.
     * @return La entidad Category correspondiente.
     */
    public Category toEntity(CategoryRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return category;
    }

    /**
     * Convierte una entidad Category a un CategoryResponseDTO.
     * @param category La entidad Category a convertir.
     * @return El objeto CategoryResponseDTO correspondiente.
     */
    public CategoryResponseDTO toResponseDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription());
    }
}