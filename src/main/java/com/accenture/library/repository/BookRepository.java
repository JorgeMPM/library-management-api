package com.accenture.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accenture.library.model.Book;

/*
 * Crea la interfaz BookRepository que extiende JpaRepository<Book, Long>.
 * Métodos:
 * boolean existsByIsbn(String isbn) para comprobar si ya existe un libro con ese isbn
 * boolean existsByIsbnAndIdNot(String isbn, Long id) para comprobar si otro libro distinto ya usa ese isbn
 * boolean existsByCategoryId(Long categoryId) para comprobar si una categoría tiene libros
 * boolean existsByAuthorsId(Long authorId) para comprobar si un autor tiene libros.
 * Añade Javadoc en español a la interfaz y a cada método. — generado por IA
 */
/**
 * Interfaz de repositorio para la entidad Book.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Comprueba si existe un libro con el ISBN especificado.
     *
     * @param isbn ISBN del libro a comprobar.
     * @return true si existe un libro con ese ISBN, false en caso contrario.
     */
    boolean existsByIsbn(String isbn);

    /**
     * Comprueba si existe otro libro distinto con el mismo ISBN.
     *
     * @param isbn ISBN del libro a comprobar.
     * @param id   ID del libro que se está verificando para excluirlo de la búsqueda.
     * @return true si existe otro libro con ese ISBN, false en caso contrario.
     */
    boolean existsByIsbnAndIdNot(String isbn, Long id);

    /**
     * Comprueba si existen libros asociados a una categoría específica.
     *
     * @param categoryId ID de la categoría a comprobar.
     * @return true si existen libros asociados a esa categoría, false en caso contrario.
     */
    boolean existsByCategoryId(Long categoryId);

    /**
     * Comprueba si existen libros asociados a un autor específico.
     *
     * @param authorId ID del autor a comprobar.
     * @return true si existen libros asociados a ese autor, false en caso contrario.
     */
    boolean existsByAuthorsId(Long authorId);

    /*
    * Añade el método Page<Book> findByFilters(Long categoryId, Long authorId, Boolean available, Pageable pageable)
    * con @Param en cada filtro y esta @Query JPQL:
    * "SELECT b FROM Book b WHERE (:categoryId IS NULL OR b.category.id = :categoryId)
    *  AND (:authorId IS NULL OR EXISTS (SELECT 1 FROM Book b2 JOIN b2.authors a WHERE b2.id = b.id AND a.id = :authorId))
    *  AND (:available IS NULL OR (:available = true AND b.availableStock > 0) OR (:available = false AND b.availableStock = 0))"
    * Añade Javadoc en español al método. — generado por IA
    */
    /**
     * Busca libros aplicando filtros opcionales de categoría, autor y disponibilidad.
     *
     * @param categoryId ID de la categoría para filtrar (puede ser null).
     * @param authorId   ID del autor para filtrar (puede ser null).
     * @param available  Filtro de disponibilidad (true para disponibles, false para no disponibles, null para ignorar).
     * @param pageable   Información de paginación y ordenamiento.
     * @return Página de libros que cumplen con los filtros especificados.
     */
    @Query("SELECT b FROM Book b WHERE (:categoryId IS NULL OR b.category.id = :categoryId) " +
            "AND (:authorId IS NULL OR EXISTS (SELECT 1 FROM Book b2 JOIN b2.authors a WHERE b2.id = b.id AND a.id = :authorId)) " +
            "AND (:available IS NULL OR (:available = true AND b.availableStock > 0) OR (:available = false AND b.availableStock = 0))")
    Page<Book> findByFilters(@Param("categoryId") Long categoryId,
                             @Param("authorId") Long authorId,
                             @Param("available") Boolean available,
                             Pageable pageable);
}