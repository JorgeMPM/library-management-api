package com.accenture.library.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.accenture.library.dto.BookRequestDTO;
import com.accenture.library.dto.BookResponseDTO;
import com.accenture.library.dto.PageResponseDTO;
import com.accenture.library.exception.DuplicateResourceException;
import com.accenture.library.exception.InvalidQueryParameterException;
import com.accenture.library.exception.ReferencedResourceNotFoundException;
import com.accenture.library.exception.ResourceInUseException;
import com.accenture.library.exception.ResourceNotFoundException;
import com.accenture.library.mapper.BookMapper;
import com.accenture.library.model.Author;
import com.accenture.library.model.Book;
import com.accenture.library.model.Category;
import com.accenture.library.repository.AuthorRepository;
import com.accenture.library.repository.BookRepository;
import com.accenture.library.repository.CategoryRepository;
import com.accenture.library.repository.LoanRepository;

import org.springframework.transaction.annotation.Transactional;

/*
 * Crea la clase BookServiceImpl anotada con @Service que implementa BookService, con campos final BookRepository,
 * CategoryRepository, AuthorRepository, LoanRepository y BookMapper inyectados por constructor.
 * Métodos privados, con if y sin orElseThrow ni lambdas:
 * findBookOrThrow(Long id): si no existe lanza ResourceNotFoundException "Libro con id <id> no encontrado"
 * resolveCategory(Long categoryId): devuelve la Category o lanza ReferencedResourceNotFoundException "La categoría con id <id> no existe"
 * resolveAuthors(List<Long> authorIds): devuelve un Set<Author>; por cada id, si es null o no existe lanza
 * ReferencedResourceNotFoundException "El autor con id <id> no existe".
 * Añade Javadoc en español a la clase, al constructor y a cada método. — generado por IA
 */
/**
 * Implementación del servicio de gestión de libros en la biblioteca.
 * Esta clase proporciona métodos para realizar operaciones CRUD sobre los
 * libros,
 * así como para filtrar y paginar los resultados.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final LoanRepository loanRepository;
    private final BookMapper bookMapper;

    // Añade la constante SORTABLE_FIELDS = List.of("title", "isbn",
    // "publishedYear", "availableStock", "totalStock").
    // Campos con los que se permite ordenar el listado de libros.
    private static final List<String> SORTABLE_FIELDS = List.of("title", "isbn", "publishedYear", "availableStock",
            "totalStock");

    /**
     * Constructor de la clase BookServiceImpl.
     * 
     * @param bookRepository     Repositorio para acceder a los datos de los libros.
     * @param categoryRepository Repositorio para acceder a los datos de las
     *                           categorías.
     * @param authorRepository   Repositorio para acceder a los datos de los
     *                           autores.
     * @param loanRepository     Repositorio para acceder a los datos de los
     *                           préstamos.
     * @param bookMapper         Mapper para convertir entre entidades y DTOs de
     *                           libros.
     */
    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository,
            AuthorRepository authorRepository, LoanRepository loanRepository,
            BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.loanRepository = loanRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Busca un libro por el ID de la URL y lo devuelve o lanza una excepción 404.
     * 
     * @param id El ID del libro a buscar.
     * @return El libro encontrado.
     * @throws ResourceNotFoundException si no existe el libro con el ID
     *                                   proporcionado.
     */
    private Book findBookOrThrow(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Libro con id " + id + " no encontrado");
        }
        return book.get();
    }

    /**
     * Busca una categoría por su ID o lanza excepción 400.
     * 
     * @param categoryId El ID de la categoría a resolver.
     * @return La categoría encontrada.
     * @throws ReferencedResourceNotFoundException si no existe la categoría con el
     *                                             ID proporcionado.
     */
    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ReferencedResourceNotFoundException("La categoría con id " + categoryId + " no existe");
        }
        return category.get();
    }

    /**
     * Busca un conjunto de autores por sus IDs. Los ids repetidos se tratan como
     * uno solo. Si algún id es null o no existe, lanza excepción 400.
     * 
     * @param authorIds La lista de IDs de los autores a resolver.
     * @return Un conjunto de autores encontrados.
     * @throws ReferencedResourceNotFoundException si algún autor no existe o si
     *                                             algún ID es null.
     */
    private Set<Author> resolveAuthors(List<Long> authorIds) {
        Set<Author> authors = new HashSet<>();
        for (Long authorId : authorIds) {
            if (authorId == null) {
                throw new ReferencedResourceNotFoundException("El autor con id null no existe");
            }
            Optional<Author> author = authorRepository.findById(authorId);
            if (author.isEmpty()) {
                throw new ReferencedResourceNotFoundException("El autor con id " + authorId + " no existe");
            }
            authors.add(author.get());
        }
        return authors;
    }

    /*
     * Añade la constante SORTABLE_FIELDS = List.of("title", "isbn",
     * "publishedYear", "availableStock", "totalStock").
     * Añade el método findAll(page, size, sort, categoryId, authorId, available)
     * con @Transactional(readOnly = true): construye el Pageable con
     * buildPageable, llama a bookRepository.findByFilters, convierte el contenido
     * con un for y devuelve un PageResponseDTO.
     * Método privado buildPageable(Integer page, Integer size, String[] sort): page
     * por defecto 0 y si es negativo lanza
     * InvalidQueryParameterException; size por defecto 20, si es menor que 1 lanza
     * la excepción y si es mayor que 100 usa 100.
     * Método privado parseSort(String[] sort): si es null ordena por title asc; si
     * llega más de un valor, o el formato no es
     * campo o campo,dirección, o el campo no está en SORTABLE_FIELDS, o la
     * dirección no es asc/desc (sin distinguir mayúsculas),
     * lanza InvalidQueryParameterException. Siempre añade id asc como desempate.
     * Sin lambdas ni streams. Mensajes en español. Javadoc en español en cada
     * método. — generado por IA
     */
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<BookResponseDTO> findAll(Integer page, Integer size, String[] sort, Long categoryId,
            Long authorId, Boolean available) {

        Pageable pageable = buildPageable(page, size, sort);
        Page<Book> books = bookRepository.findByFilters(categoryId, authorId, available, pageable);
        List<BookResponseDTO> bookDTOs = new ArrayList<>();
        for (Book book : books.getContent()) {
            bookDTOs.add(bookMapper.toResponseDTO(book));
        }

        return new PageResponseDTO<>(bookDTOs, books.getNumber(), books.getSize(), books.getTotalElements(), books.getTotalPages(), books.isLast());
    }

    /**
     * Construye la paginación con los parámetros proporcionados.
     * 
     * @param page La página a recuperar.
     * @param size El tamaño de la página.
     * @param sort Los criterios de ordenación.
     * @return Un Pageable con los parámetros configurados.
     * @throws InvalidQueryParameterException si los parámetros de paginación o
     *                                        ordenación son inválidos.
     */
    private Pageable buildPageable(Integer page, Integer size, String[] sort) {

        int pageNumber = 0;
        if (page != null) {
            if (page < 0) {
                throw new InvalidQueryParameterException("El parámetro page no puede ser negativo");
            }
            pageNumber = page;
        }
        int pageSize = 20;
        if (size != null) {
            if (size < 1) {
                throw new InvalidQueryParameterException("El parámetro size debe ser al menos 1");
            }
            pageSize = Math.min(size, 100);
        }
        return PageRequest.of(pageNumber, pageSize, parseSort(sort));
    }

    /**
     * Valida el parámetro sort y construye la ordenación, con desempate por id
     * ascendente.
     * 
     * @param sort Los criterios de ordenación.
     * @return Un objeto Sort con los criterios parseados.
     * @throws InvalidQueryParameterException si los criterios de ordenación son
     *                                        inválidos.
     */
    private Sort parseSort(String[] sort) {

        if (sort == null) {
            return Sort.by(Sort.Order.asc("title"), Sort.Order.asc("id"));
        }
        if (sort.length != 1) {
            throw new InvalidQueryParameterException("Solo se admite un criterio de ordenación");
        }
        String[] parts = sort[0].split(",", -1);
        if (parts.length > 2) {
            throw new InvalidQueryParameterException("El parámetro sort debe tener el formato campo o campo,dirección");
        }
        String field = parts[0];
        if (!SORTABLE_FIELDS.contains(field)) {
            throw new InvalidQueryParameterException("No se puede ordenar por '" + field
                    + "'. Campos permitidos: title, isbn, publishedYear, availableStock, totalStock");
        }
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length == 2) {
            if (parts[1].equalsIgnoreCase("asc")) {
                direction = Sort.Direction.ASC;
            } else if (parts[1].equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            } else {
                throw new InvalidQueryParameterException("La dirección de ordenación debe ser asc o desc");
            }
        }
        return Sort.by(new Sort.Order(direction, field), Sort.Order.asc("id"));
    }

        /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id) {
        return bookMapper.toResponseDTO(findBookOrThrow(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookResponseDTO create(BookRequestDTO dto) {
        Category category = null;
        if (dto.categoryId() != null) {
            category = resolveCategory(dto.categoryId());
        }
        Set<Author> authors = new HashSet<>();
        if (dto.authorIds() != null) {
            authors = resolveAuthors(dto.authorIds());
        }
        if (bookRepository.existsByIsbn(dto.isbn())) {
            throw new DuplicateResourceException("Ya existe un libro con el ISBN '" + dto.isbn() + "'");
        }
        Book book = bookMapper.toEntity(dto);
        book.setCategory(category);
        book.setAuthors(authors);
        return bookMapper.toResponseDTO(bookRepository.save(book));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookResponseDTO update(Long id, BookRequestDTO dto) {
        Category newCategory = null;
        if (dto.categoryId() != null) {
            newCategory = resolveCategory(dto.categoryId());
        }
        Set<Author> newAuthors = null;
        if (dto.authorIds() != null) {
            newAuthors = resolveAuthors(dto.authorIds());
        }
        Book book = findBookOrThrow(id);
        if (bookRepository.existsByIsbnAndIdNot(dto.isbn(), id)) {
            throw new DuplicateResourceException("Ya existe un libro con el ISBN '" + dto.isbn() + "'");
        }
        bookMapper.updateBasicFields(book, dto);
        if (newCategory != null) {
            book.setCategory(newCategory);
        }
        if (newAuthors != null) {
            book.getAuthors().clear();
            book.getAuthors().addAll(newAuthors);
        }
        return bookMapper.toResponseDTO(bookRepository.save(book));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Book book = findBookOrThrow(id);
        if (loanRepository.existsByBookId(id)) {
            throw new ResourceInUseException(
                    "No se puede eliminar el libro con id " + id + " porque tiene préstamos asociados");
        }
        bookRepository.delete(book);
    }
}
