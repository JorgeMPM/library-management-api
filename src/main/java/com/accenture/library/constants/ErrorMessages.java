package com.accenture.library.constants;

/**
 * Mensajes de error que devuelve la API al lanzar excepciones o al gestionarlas en el
 * {@code GlobalExceptionHandler}.
 * <p>
 * Los mensajes con datos variables (un id, un ISBN, una ruta…) son plantillas con
 * {@code %s} o {@code %d} y se completan con {@link String#formatted(Object...)}, por ejemplo:
 * {@code ErrorMessages.BOOK_NOT_FOUND.formatted(id)}.
 */
public final class ErrorMessages {

    /**
     * Constructor privado: clase de constantes, no se instancia.
     */
    private ErrorMessages() {
    }

    // ---------- 404: recurso de la URL no encontrado ----------
    public static final String BOOK_NOT_FOUND = "Libro con id %d no encontrado";
    public static final String AUTHOR_NOT_FOUND = "Autor con id %d no encontrado";
    public static final String CATEGORY_NOT_FOUND = "Categoría con id %d no encontrada";
    public static final String LOAN_NOT_FOUND = "Préstamo con id %d no encontrado";
    public static final String ROUTE_NOT_FOUND = "La ruta %s no existe";

    // ---------- 400: id inexistente en el body ----------
    public static final String REFERENCED_BOOK_NOT_FOUND = "El libro con id %d no existe";
    public static final String REFERENCED_CATEGORY_NOT_FOUND = "La categoría con id %d no existe";
    public static final String REFERENCED_AUTHOR_NOT_FOUND = "El autor con id %s no existe";

    // ---------- 400: paginación y ordenación ----------
    public static final String PAGE_NEGATIVE = "El parámetro page no puede ser negativo";
    public static final String SIZE_MIN = "El parámetro size debe ser al menos 1";
    public static final String SORT_SINGLE = "Solo se admite un criterio de ordenación";
    public static final String SORT_FORMAT = "El parámetro sort debe tener el formato campo o campo,dirección";
    public static final String SORT_FIELD_INVALID = "No se puede ordenar por '%s'. Campos permitidos: title, isbn, publishedYear, availableStock, totalStock";
    public static final String SORT_DIRECTION_INVALID = "La dirección de ordenación debe ser asc o desc";

    // ---------- 400: petición mal formada ----------
    public static final String MALFORMED_BODY = "El cuerpo de la petición no es válido: JSON mal formado o valor no válido en algún campo" + " (las fechas deben tener el formato dd-MM-yyyy)";
    public static final String INVALID_PARAMETER = "El parámetro '%s' tiene un valor no válido: '%s'";
    public static final String MISSING_PARAMETER = "Falta el parámetro obligatorio '%s'";

    // ---------- 409: conflicto con el estado de los datos ----------
    public static final String BOOK_DUPLICATE_ISBN = "Ya existe un libro con el ISBN '%s'";
    public static final String CATEGORY_DUPLICATE_NAME = "Ya existe una categoría con el nombre '%s'";
    public static final String AUTHOR_DUPLICATE_NAME = "Ya existe un autor con el nombre '%s %s'";
    public static final String BOOK_IN_USE = "No se puede eliminar el libro con id %d porque tiene préstamos asociados";
    public static final String AUTHOR_IN_USE = "No se puede eliminar el autor con id %d porque tiene libros asignados";
    public static final String CATEGORY_IN_USE = "No se puede eliminar la categoría con id %d porque tiene libros asignados";
    public static final String BOOK_OUT_OF_STOCK = "El libro con id %d no tiene stock disponible";
    public static final String LOAN_ALREADY_RETURNED = "El préstamo con id %d ya está devuelto";
    public static final String DATA_INTEGRITY_VIOLATION = "La operación viola una restricción de la base de datos (valor único repetido)";

    // ---------- 401 / 403 ----------
    public static final String TOKEN_REQUIRED = "Se requiere un token válido para acceder a este recurso";
    public static final String BAD_CREDENTIALS = "Credenciales incorrectas";
    public static final String ACCESS_DENIED = "No tienes permiso para realizar esta operación";

    // ---------- 405 / 406 / 415 / 500 ----------
    public static final String METHOD_NOT_ALLOWED = "El método %s no está permitido en la ruta %s";
    public static final String NOT_ACCEPTABLE = "La API solo puede responder en formato JSON";
    public static final String UNSUPPORTED_MEDIA_TYPE = "El tipo de contenido %s no está soportado; usa application/json";
    public static final String INTERNAL_ERROR = "Error interno del servidor";
}
