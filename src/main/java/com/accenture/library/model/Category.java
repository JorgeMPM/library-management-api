package com.accenture.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Crea la clase entidad JPA Category mapeada a la tabla "category", con Lombok @Getter, @Setter y @NoArgsConstructor.
 * Campos: id Long con @Id y @GeneratedValue(strategy = GenerationType.IDENTITY)
 * name String con @Column(name = "name", nullable = false, unique = true, length = 100)
 * description String con @Column(name = "description", length = 500).
 * Sin relación con Book. Añade Javadoc en español a la clase. — generado por IA
 */
/**
 * Clase entidad JPA que representa la categoría de un libro en la biblioteca.
 * Esta clase está mapeada a la tabla "category" en la base de datos.
 * Contiene información sobre el nombre y la descripción de la categoría.
 */
@Entity
@Table(name = "category")
@Getter 
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;
}