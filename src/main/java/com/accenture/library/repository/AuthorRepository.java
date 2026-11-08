package com.accenture.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.library.model.Author;

/*
 * Crea la interfaz AuthorRepository que extiende JpaRepository<Author, Long>.
 * Método: List<Author> findAllByOrderByIdAsc() para listar ordenado por id ascendente.
 * Añade Javadoc en español a la interfaz y al método. — generado por IA
 */
/**
 * Interfaz de repositorio para la entidad Author.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
    /**
     * Obtiene todos los autores ordenados por su ID en orden ascendente.
     *
     * @return Lista de autores ordenados por ID ascendente.
     */
    List<Author> findAllByOrderByIdAsc();

    /**
     * Comprueba si ya existe un autor con ese nombre y apellidos, sin distinguir
     * mayúsculas.
     *
     * @param firstName nombre del autor
     * @param lastName  apellidos del autor
     * @return true si ya existe
     */
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    /**
     * Comprueba si otro autor distinto del indicado ya tiene ese nombre y
     * apellidos.
     *
     * @param firstName nombre del autor
     * @param lastName  apellidos del autor
     * @param id id del autor que se está editando
     * @return true si otro autor ya los usa
     */
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndIdNot(String firstName, String lastName, Long id);

}