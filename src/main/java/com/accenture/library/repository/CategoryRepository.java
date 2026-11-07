package com.accenture.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.library.model.Category;

/*
 * Crea la interfaz CategoryRepository que extiende JpaRepository<Category, Long>.
 * Métodos: List<Category> findAllByOrderByIdAsc() para listar ordenado por id ascendente
 * boolean existsByName(String name) para comprobar si ya existe una categoría con ese nombre.
 * Añade Javadoc en español a la interfaz y a cada método. — generado por IA
 */
/** 
 * Interfaz de repositorio para la entidad Category.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Obtiene todas las categorías ordenadas por su ID en orden ascendente.
     *
     * @return Lista de categorías ordenadas por ID ascendente.
     */
    List<Category> findAllByOrderByIdAsc();

    /**
     * Comprueba si existe una categoría con el nombre especificado.
     *
     * @param name Nombre de la categoría a comprobar.
     * @return true si existe una categoría con ese nombre, false en caso contrario.
     */
    boolean existsByName(String name);
}