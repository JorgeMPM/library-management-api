package com.accenture.library.mapper;

import org.springframework.stereotype.Component;

import com.accenture.library.dto.AuthorRequestDTO;
import com.accenture.library.dto.AuthorResponseDTO;
import com.accenture.library.model.Author;

/*
 * Crea la clase AuthorMapper anotada con @Component.
 * Método toEntity(AuthorRequestDTO dto): crea un Author nuevo con firstName, lastName y nationality del dto.
 * Método updateEntity(Author author, AuthorRequestDTO dto): asigna al author existente firstName, lastName y nationality del dto.
 * Método toResponseDTO(Author author): devuelve un AuthorResponseDTO con id, firstName, lastName y nationality.
 * Sin lambdas. Añade Javadoc en español a la clase y a cada método. — generado por IA
 */
/**
 * Mapeador para convertir entre AuthorRequestDTO, Author y AuthorResponseDTO.
 */
@Component
public class AuthorMapper {

    /**
     * Convierte un AuthorRequestDTO a una entidad Author.
     * @param dto El objeto AuthorRequestDTO a convertir.
     * @return La entidad Author correspondiente.
     */
    public Author toEntity(AuthorRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Author author = new Author();
        updateEntity(author, dto);
        return author;
    }

    /**
     * Actualiza una entidad Author existente con los datos de un AuthorRequestDTO.
     * @param author La entidad Author a actualizar.
     * @param dto El objeto AuthorRequestDTO con los nuevos datos.
     */
    public void updateEntity(Author author, AuthorRequestDTO dto) {
        if (author != null && dto != null) {
            author.setFirstName(dto.firstName());
            author.setLastName(dto.lastName());
            author.setNationality(dto.nationality());
        }
    }

    /**
     * Convierte una entidad Author a un AuthorResponseDTO.
     * @param author La entidad Author a convertir.
     * @return El objeto AuthorResponseDTO correspondiente.
     */
    public AuthorResponseDTO toResponseDTO(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorResponseDTO(author.getId(), author.getFirstName(), author.getLastName(), author.getNationality());
    }
}