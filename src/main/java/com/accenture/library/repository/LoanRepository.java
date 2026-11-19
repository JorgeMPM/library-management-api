package com.accenture.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.library.model.Loan;

/**
 * Interfaz de repositorio para la entidad Loan.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas.
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    /**
     * Lista todos los préstamos ordenados por ID en orden ascendente.
     * @return Lista de préstamos ordenados por ID.
     */
    List<Loan> findAllByOrderByIdAsc();

    /**
     * Comprueba si existe algún préstamo para un libro específico.
     * @param bookId El ID del libro a comprobar.
     * @return true si existe algún préstamo para el libro, false en caso contrario.
     */
    boolean existsByBookId(Long bookId);
}
