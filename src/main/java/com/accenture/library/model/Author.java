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
 * Crea la entidad JPA Author mapeada a la tabla "author", con Lombok @Getter, @Setter y @NoArgsConstructor.
 * Campos: id Long con @Id y @GeneratedValue(strategy = GenerationType.IDENTITY)
 * firstName String con @Column(name = "first_name", nullable = false, length = 100)
 * lastName String con @Column(name = "last_name", nullable = false, length = 100)
 * nationality String con @Column(name = "nationality", length = 100).
 * Sin relación con Book. Añade Javadoc en español a la clase. — generado por IA
 */
/** 
 * Clase entidad JPA que representa un autor en la biblioteca.
 * Esta clase está mapeada a la tabla "author" en la base de datos.
 * Contiene información sobre el nombre, apellido y nacionalidad del autor.
*/
@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "nationality", length = 100)
    private String nationality;
}