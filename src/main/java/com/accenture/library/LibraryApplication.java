package com.accenture.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la API REST de gestión de biblioteca.
 */
@SpringBootApplication
public class LibraryApplication {

	/**
	 * Método principal de la aplicación.
	 *
	 * @param args los argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
