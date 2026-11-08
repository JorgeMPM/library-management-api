package com.accenture.library.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.accenture.library.dto.request.BookRequestDTO;
import com.accenture.library.dto.response.AuthorResponseDTO;
import com.accenture.library.dto.response.BookResponseDTO;
import com.accenture.library.model.Author;
import com.accenture.library.model.Book;

/*
 * Crea la clase BookMapper anotada con @Component, con un campo final AuthorMapper inyectado por constructor.
 * Método toEntity(BookRequestDTO dto): crea un Book nuevo y llama a updateBasicFields.
 * Método updateBasicFields(Book book, BookRequestDTO dto): asigna title, isbn, publishedYear, availableStock y totalStock.
 * Método toResponseDTO(Book book): categoryId y categoryName a null si book.getCategory() es null
 * authors como List<AuthorResponseDTO> ordenada por id ascendente, insertando cada autor en su posición con un bucle while.
 * Sin lambdas ni streams. Añade Javadoc en español a la clase, al constructor y a cada método. — generado por IA
 */
/**
 * Mapeador para convertir entre BookRequestDTO, Book y BookResponseDTO.
 */
@Component
public class BookMapper {

    // Campo final AuthorMapper inyectado por constructor.
    private final AuthorMapper authorMapper;

    /**
     * Constructor que inyecta el AuthorMapper.
     * @param authorMapper El AuthorMapper a inyectar.
     */
    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    /**
     * Convierte un BookRequestDTO a una entidad Book.
     * @param dto El objeto BookRequestDTO a convertir.
     * @return La entidad Book correspondiente.
     */
    public Book toEntity(BookRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Book book = new Book();
        updateBasicFields(book, dto);
        return book;
    }

    /**
     * Actualiza los campos básicos de una entidad Book con los datos de un BookRequestDTO.
     * @param book La entidad Book a actualizar.
     * @param dto El objeto BookRequestDTO con los nuevos datos.
     */
    public void updateBasicFields(Book book, BookRequestDTO dto) {
        if (book != null && dto != null) {
            book.setTitle(dto.title());
            book.setIsbn(dto.isbn());
            book.setPublishedYear(dto.publishedYear());
            book.setAvailableStock(dto.availableStock());
            book.setTotalStock(dto.totalStock());
        }
    }

    /**
     * Convierte una entidad Book a un BookResponseDTO.
     * @param book La entidad Book a convertir.
     * @return El objeto BookResponseDTO correspondiente.
     */
    public BookResponseDTO toResponseDTO(Book book) {
        if (book == null) {
            return null;
        }
        Long categoryId = null;
        String categoryName = null;
        if (book.getCategory() != null) {
            categoryId = book.getCategory().getId();
            categoryName = book.getCategory().getName();
        }

        List<AuthorResponseDTO> authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            AuthorResponseDTO authorDTO = authorMapper.toResponseDTO(author);
            int position = 0;
            while (position < authors.size() && authors.get(position).id() < authorDTO.id()) {
                position++;
            }
            authors.add(position, authorDTO);
        }

        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublishedYear(),
                book.getAvailableStock(),
                book.getTotalStock(),
                categoryId,
                categoryName,
                authors);
    }
}