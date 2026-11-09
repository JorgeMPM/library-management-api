package com.accenture.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.accenture.library.dto.request.AuthorRequestDTO;
import com.accenture.library.dto.response.AuthorResponseDTO;
import com.accenture.library.exception.DuplicateResourceException;
import com.accenture.library.exception.ResourceInUseException;
import com.accenture.library.exception.ResourceNotFoundException;
import com.accenture.library.mapper.AuthorMapper;
import com.accenture.library.model.Author;
import com.accenture.library.repository.AuthorRepository;
import com.accenture.library.repository.BookRepository;

import org.springframework.transaction.annotation.Transactional;
import com.accenture.library.constants.ErrorMessages;

/*
 * Crea la clase AuthorServiceImpl anotada con @Service que implementa AuthorService, con campos final AuthorRepository,
 * BookRepository y AuthorMapper inyectados por constructor. Método privado findAuthorOrThrow(Long id) que usa findById
 * y, si el Optional está vacío, lanza ResourceNotFoundException "Autor con id <id> no encontrado" (con if, sin orElseThrow).
 * findAll y findById con @Transactional(readOnly = true); create, update y delete con @Transactional.
 * delete: si bookRepository.existsByAuthorsId(id) lanza ResourceInUseException
 * "No se puede eliminar el autor con id <id> porque tiene libros asignados".
 * Sin lambdas ni streams. Añade Javadoc en español a la clase, al constructor y a cada método. — generado por IA
 */
/**
 * Implementación del servicio para gestionar autores en la biblioteca.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    // Campos finales inyectados por constructor.
    private final AuthorRepository authorRepository;
    // Campo final BookRepository inyectado por constructor.
    private final BookRepository bookRepository;
    // Campo final AuthorMapper inyectado por constructor.
    private final AuthorMapper authorMapper;

    /**
     * Constructor que inyecta los repositorios y el mapeador de autores.
     * @param authorRepository El repositorio de autores.
     * @param bookRepository El repositorio de libros.
     * @param authorMapper El mapeador de autores.
     */
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAll() {
        List<AuthorResponseDTO> result = new ArrayList<>();
        for (Author author : authorRepository.findAllByOrderByIdAsc()) {
            result.add(authorMapper.toResponseDTO(author));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDTO findById(Long id) {
        
        return authorMapper.toResponseDTO(findAuthorOrThrow(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AuthorResponseDTO create(AuthorRequestDTO dto) {
        if (authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(dto.firstName(), dto.lastName())) {
            throw new DuplicateResourceException(
                    ErrorMessages.AUTHOR_DUPLICATE_NAME.formatted(dto.firstName(), dto.lastName()));
        }
        Author author = authorMapper.toEntity(dto);
        return authorMapper.toResponseDTO(authorRepository.save(author));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AuthorResponseDTO update(Long id, AuthorRequestDTO dto) {
        Author author = findAuthorOrThrow(id);

        if (authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndIdNot(dto.firstName(), dto.lastName(), id)) {
            throw new DuplicateResourceException(
                    ErrorMessages.AUTHOR_DUPLICATE_NAME.formatted(dto.firstName(), dto.lastName()));
        }
    
        authorMapper.updateEntity(author, dto);
        return authorMapper.toResponseDTO(authorRepository.save(author));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional 
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException(ErrorMessages.AUTHOR_NOT_FOUND.formatted(id));
        }
        if (bookRepository.existsByAuthorsId(id)) {
            throw new ResourceInUseException(ErrorMessages.AUTHOR_IN_USE.formatted(id));
        }
        authorRepository.deleteById(id);
    }

    /**
     * Método privado que busca un autor por su ID o lanza una excepción si no se encuentra.
     * @param id El ID del autor a buscar.
     * @return El autor encontrado.
     * @throws ResourceNotFoundException Si el autor no se encuentra.
     */
    private Author findAuthorOrThrow(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessages.AUTHOR_NOT_FOUND.formatted(id));
        }
        return author.get();
    }

}