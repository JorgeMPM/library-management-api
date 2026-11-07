package com.accenture.library.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Crea la entidad JPA Loan mapeada a la tabla "loan", con Lombok @Getter, @Setter y @NoArgsConstructor.
 * Campos: id Long con @Id y @GeneratedValue(strategy = GenerationType.IDENTITY)
 * loanDate LocalDate con @Column(name = "loan_date", nullable = false)
 * expectedReturnDate LocalDate con @Column(name = "expected_return_date", nullable = false)
 * actualReturnDate LocalDate con @Column(name = "actual_return_date")
 * status LoanStatus con @Enumerated(EnumType.STRING) y @Column(name = "status", nullable = false, length = 20)
 * book Book con @ManyToOne(fetch = FetchType.LAZY, optional = false) y @JoinColumn(name = "book_id", nullable = false).
 * Sin cascade. Añade Javadoc en español a la clase. — generado por IA
 */
/**
 * Clase entidad JPA que representa un préstamo de libro en la biblioteca.
 * Esta clase está mapeada a la tabla "loan" en la base de datos.
 */
@Entity
@Table(name = "loan")
@Getter
@Setter
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "expected_return_date", nullable = false)
    private LocalDate expectedReturnDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}