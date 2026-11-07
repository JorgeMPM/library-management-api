-- Crea la base de datos si no existe
CREATE DATABASE IF NOT EXISTS library
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE library;

-- Borrado en orden inverso a las dependencias (primero las tablas hijas)
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  name        VARCHAR(100) NOT NULL,
  description VARCHAR(500) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_category_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE author (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  first_name  VARCHAR(100) NOT NULL,
  last_name   VARCHAR(100) NOT NULL,
  nationality VARCHAR(100) NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book (
  id              BIGINT       NOT NULL AUTO_INCREMENT,
  title           VARCHAR(150) NOT NULL,
  isbn            VARCHAR(20)  NOT NULL,
  published_year  INT          NULL,
  available_stock INT          NOT NULL,
  total_stock     INT          NOT NULL,
  category_id     BIGINT       NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_book_isbn (isbn),
  CONSTRAINT fk_book_category FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book_author (
  book_id   BIGINT NOT NULL,
  author_id BIGINT NOT NULL,
  PRIMARY KEY (book_id, author_id),
  CONSTRAINT fk_book_author_book   FOREIGN KEY (book_id)   REFERENCES book (id),
  CONSTRAINT fk_book_author_author FOREIGN KEY (author_id) REFERENCES author (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE loan (
  id                   BIGINT      NOT NULL AUTO_INCREMENT,
  loan_date            DATE        NOT NULL,
  expected_return_date DATE        NOT NULL,
  actual_return_date   DATE        NULL,
  status               VARCHAR(20) NOT NULL,
  book_id              BIGINT      NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_loan_book FOREIGN KEY (book_id) REFERENCES book (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===================== DATOS DE PRUEBA =====================

-- 3 categorías: la 3 (Ensayo) no tiene libros
INSERT INTO category (id, name, description) VALUES
  (1, 'Novela', 'Narrativa de ficción'),
  (2, 'Poesía', 'Obras en verso'),
  (3, 'Ensayo', 'Textos de reflexión y análisis');

-- 5 autores: el 5 (Vargas Llosa) no tiene libros
INSERT INTO author (id, first_name, last_name, nationality) VALUES
  (1, 'Miguel', 'de Cervantes', 'Española'),
  (2, 'Gabriel', 'García Márquez', 'Colombiana'),
  (3, 'Isabel', 'Allende', 'Chilena'),
  (4, 'Federico', 'García Lorca', 'Española'),
  (5, 'Mario', 'Vargas Llosa', 'Peruana');

-- 10 libros: (id, title, isbn, published_year, available_stock, total_stock, category_id)
-- available_stock = total_stock - préstamos ACTIVE de ese libro
INSERT INTO book (id, title, isbn, published_year, available_stock, total_stock, category_id) VALUES
  (1,  'Don Quijote de la Mancha',          '978-0000000001', 1605, 2, 3, 1),
  (2,  'Cien años de soledad',              '978-0000000002', 1967, 1, 2, 1),
  (3,  'La casa de los espíritus',          '978-0000000003', 1982, 2, 2, 1),
  (4,  'Romancero gitano',                  '978-0000000004', 1928, 0, 1, 2),
  (5,  'Poeta en Nueva York',               '978-0000000005', 1940, 2, 2, 2),
  (6,  'Antología hispanoamericana',        '978-0000000006', 2001, 1, 1, 2),
  (7,  'Novelas ejemplares',                '978-0000000007', 1613, 2, 2, 1),
  (8,  'Paula',                             '978-0000000008', 1994, 1, 1, 1),
  (9,  'Crónica de una muerte anunciada',   '978-0000000009', 1981, 3, 3, NULL),
  (10, 'Enciclopedia general',              '978-0000000010', NULL, 1, 1, NULL);

-- Relación libro-autor: el libro 6 tiene dos autores; el libro 10 no tiene ninguno
INSERT INTO book_author (book_id, author_id) VALUES
  (1, 1), (2, 2), (3, 3), (4, 4), (5, 4),
  (6, 2), (6, 4),
  (7, 1), (8, 3), (9, 2);

-- 5 préstamos: (id, loan_date, expected_return_date, actual_return_date, status, book_id)
INSERT INTO loan (id, loan_date, expected_return_date, actual_return_date, status, book_id) VALUES
  (1, '2026-10-01', '2099-12-31', NULL, 'ACTIVE', 1),
  (2, DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 15 DAY), NULL, 'ACTIVE', 2),
  (3, '2026-09-01', '2026-09-15', '2026-09-10', 'RETURNED', 3),
  (4, '2026-10-15', '2099-12-31', NULL, 'ACTIVE', 4),
  (5, '2026-08-01', '2026-08-20', '2026-08-18', 'RETURNED', 8);