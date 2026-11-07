package com.accenture.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/*
 * Crea la entidad JPA Book mapeada a la tabla "book", con Lombok @Getter, @Setter y @NoArgsConstructor.
 * Campos: id Long con @Id y @GeneratedValue(strategy = GenerationType.IDENTITY)
 * title String con @Column(name = "title", nullable = false, length = 150)
 * isbn String con @Column(name = "isbn", nullable = false, unique = true, length = 20)
 * publishedYear Integer con @Column(name = "published_year")
 * availableStock Integer con @Column(name = "available_stock", nullable = false)
 * totalStock Integer con @Column(name = "total_stock", nullable = false)
 * category Category con @ManyToOne(fetch = FetchType.LAZY) y @JoinColumn(name = "category_id")
 * authors Set<Author> inicializado con new HashSet<>(), con @ManyToMany(fetch = FetchType.LAZY) y
 * @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id")).
 * Sin cascade en ninguna relación. Añade Javadoc en español a la clase. — generado por IA
 */
/** 
 * Clase entidad JPA que representa un libro en la biblioteca.
 * Esta clase está mapeada a la tabla "book" en la base de datos.
 * Contiene información sobre el título, ISBN, año de publicación, stock disponible y total, así como la categoría y los autores del libro.
 */
@Entity 
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "total_stock", nullable = false)
    private Integer totalStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();
}