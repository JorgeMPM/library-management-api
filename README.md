# Library – API REST de Gestión de Biblioteca

API REST desarrollada con Spring Boot para gestionar los **libros, autores, categorías y préstamos** de una biblioteca. Incluye paginación, ordenación y filtros en el listado de libros, validaciones con Bean Validation, manejo global de errores, documentación OpenAPI (Swagger UI) y seguridad con Spring Security y JWT.

Proyecto final de la 1ª Edición Back-End Java con GenAI.

---

## Requisitos previos

| Herramienta | Versión |
|---|---|
| Java (JDK) | 21 |
| Maven | 3.9 o superior (también se puede usar el wrapper incluido, `./mvnw`) |
| MySQL | 8.0 o superior |

---

## Puesta en marcha desde cero

### 1. Crear la base de datos

El script `db/init.sql` crea la base de datos `library` si no existe, borra y recrea todas las tablas y carga los datos de prueba. Se puede ejecutar tantas veces como se quiera: siempre deja la base de datos en su estado inicial.

Desde la raíz del proyecto (donde está `pom.xml`):

```bash
mysql --default-character-set=utf8mb4 -u root -p < db/init.sql
```

También se puede abrir `db/init.sql` en MySQL Workbench (o en el cliente de base de datos de VS Code) y ejecutarlo completo.

### 2. Configurar las credenciales de MySQL

La conexión se configura **únicamente** en `src/main/resources/application.properties`. Para usar el proyecto en otro equipo basta con cambiar estas tres propiedades:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
```

### 3. Arrancar la aplicación

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### 4. Ejecutar los tests

```bash
./mvnw test
```

Los tests no necesitan MySQL ni ningún servicio externo.

---

## Documentación de la API (Swagger UI)

**http://localhost:8080/swagger-ui.html**

Para probar los endpoints protegidos:
1. Ejecutar `POST /auth/login` con uno de los usuarios de la tabla siguiente.
2. Copiar el `token` de la respuesta.
3. Pulsar **Authorize** y pegar el token.

---

## Usuarios de la API

| Usuario | Contraseña | Rol | Permisos |
|---|---|---|---|
| `admin` | `admin123` | ADMIN | Todos los endpoints |
| `user` | `user123` | USER | Todos excepto los `DELETE` de libros, autores y categorías (403) |

Las credenciales están definidas en el código (`SecurityConfig`), marcadas con un `TODO`: en producción deben venir de base de datos.

---

## Endpoints

Todos requieren el token en la cabecera `Authorization: Bearer <token>`, salvo `POST /auth/login`.

| Método | URL | Descripción |
|---|---|---|
| POST | `/auth/login` | Iniciar sesión y obtener el token JWT (público) |
| GET | `/api/v1/books` | Listar libros (paginado, ordenado y filtrado) |
| GET | `/api/v1/books/{id}` | Obtener un libro |
| POST | `/api/v1/books` | Crear un libro |
| PUT | `/api/v1/books/{id}` | Actualizar un libro |
| DELETE | `/api/v1/books/{id}` | Eliminar un libro (solo ADMIN) |
| GET | `/api/v1/authors` | Listar autores |
| GET | `/api/v1/authors/{id}` | Obtener un autor |
| POST | `/api/v1/authors` | Crear un autor |
| PUT | `/api/v1/authors/{id}` | Actualizar un autor |
| DELETE | `/api/v1/authors/{id}` | Eliminar un autor (solo ADMIN) |
| GET | `/api/v1/categories` | Listar categorías |
| POST | `/api/v1/categories` | Crear una categoría |
| DELETE | `/api/v1/categories/{id}` | Eliminar una categoría (solo ADMIN) |
| GET | `/api/v1/loans` | Listar préstamos |
| GET | `/api/v1/loans/{id}` | Obtener un préstamo |
| POST | `/api/v1/loans` | Crear un préstamo |
| PUT | `/api/v1/loans/{id}/return` | Devolver un libro |

### Parámetros de `GET /api/v1/books`

| Parámetro | Descripción | Por defecto |
|---|---|---|
| `page` | Número de página, desde 0 | 0 |
| `size` | Elementos por página (máximo 100) | 20 |
| `sort` | `campo` o `campo,dirección`. Campos: `title`, `isbn`, `publishedYear`, `availableStock`, `totalStock` | `title,asc` |
| `categoryId` | Solo libros de esa categoría | — |
| `authorId` | Solo libros de ese autor | — |
| `available` | `true`: con stock disponible; `false`: sin stock | — |

Se han implementado los **tres filtros**, que se combinan entre sí y con la paginación.

---

## Decisiones técnicas

**Arquitectura.** Aplicación **monolítica por capas**: un único proyecto que se despliega como un solo `.jar` contra una única base de datos, organizado internamente en Controller → Service (interfaz + implementación) → Repository, con DTOs para la entrada y la salida y Mappers manuales en clases separadas. Los controllers no contienen lógica de negocio ni capturan excepciones. Para el tamaño de este dominio (cuatro entidades muy relacionadas entre sí), un monolito es más simple de desarrollar, probar y desplegar que una arquitectura de microservicios.

**Relaciones JPA**
- **Unidireccionales**, navegando desde la entidad que tiene la clave foránea: `Book → Category`, `Book → Author` y `Loan → Book`. `Category` y `Author` no tienen colección de libros. Las comprobaciones inversas ("¿tiene libros?") se hacen con consultas del repositorio (`existsByCategoryId`, `existsByAuthorsId`). Así el modelo es más simple, se evita la recursión infinita al convertir a JSON y no se cargan colecciones que no se usan. Los libros de una categoría o de un autor se obtienen con `GET /api/v1/books?categoryId=X` o `?authorId=X`, de forma paginada.
- **`Book` es la propietaria del `@ManyToMany`** con `Author` (lleva el `@JoinTable book_author`), porque los autores se asignan al crear o editar un libro mediante `authorIds`.
- **`FetchType.LAZY`** en todas las relaciones: `@ManyToOne` es EAGER por defecto y se ha cambiado para no cargar datos que no se necesitan. Las relaciones se cargan dentro de las transacciones del Service, donde se hace la conversión a DTO (`spring.jpa.open-in-view=false`).
- Los autores de un libro se guardan en un **`Set`**: no admite duplicados, por lo que `authorIds` con ids repetidos asocia cada autor una sola vez.
- **Sin `cascade`** en ninguna relación, como exige el enunciado.
- `ddl-auto=validate`: Hibernate solo comprueba que las entidades coinciden con el esquema creado por `db/init.sql`.

**Préstamos**
- El estado **OVERDUE** no se guarda nunca: lo calcula `LoanMapper` al construir la respuesta (préstamo ACTIVE con `expectedReturnDate` anterior a hoy). Se calcula en una variable local para no modificar la entidad y que Hibernate no lo persista.
- La creación y la devolución de un préstamo son **atómicas** gracias a `@Transactional`: si algo falla, se deshacen tanto el préstamo como el cambio de stock.

**Validaciones**
- Bean Validation en los DTO de entrada, con mensajes en español.
- Dos validaciones de clase personalizadas (`@ValidBookStock` y `@ValidLoanDates`) para las reglas que dependen de dos campos. Si alguno de los campos es nulo, la regla no se evalúa.

**Errores**
- `GlobalExceptionHandler` (`@RestControllerAdvice`) centraliza todos los errores con la misma estructura (`ErrorResponseDTO`) y fuerza la respuesta a JSON, también en el 406.
- Excepciones propias con nombres descriptivos: `ResourceNotFoundException` (404), `ReferencedResourceNotFoundException` (400, id inexistente en el body), `InvalidQueryParameterException` (400, paginación u ordenación no válidas), `DuplicateResourceException`, `ResourceInUseException`, `BookOutOfStockException` y `LoanAlreadyReturnedException` (409).
- Se respeta el orden de prioridad del enunciado: 401 → 403 → 400 → 404 → 409.
- Cualquier violación de una restricción de la base de datos (`DataIntegrityViolationException`) devuelve 409.

**Paginación y ordenación.** Se validan manualmente en el Service. El parámetro `sort` se lee tal como llega en la petición (`getParameterValues`) para poder detectar si viene repetido. Siempre se desempata por `id` ascendente. El filtro por autor usa una subconsulta `EXISTS` para que cada libro aparezca una sola vez y la paginación sea correcta.

**Seguridad**
- Autenticación **stateless** con JWT firmado con **HS256**. El token incluye `sub`, `role`, `iat` y `exp` y caduca a las 24 horas. La clave secreta se lee de `application.properties`.
- El rol que se guarda en el token es la autoridad que empieza por `ROLE_`, no `FACTOR_PASSWORD`.
- Los `DELETE` usan el patrón `/api/v1/books/*` (un solo nivel): así `DELETE /api/v1/books` sin id y `DELETE /api/v1/loans/{id}` devuelven 405 para cualquier rol.
- Los errores 401 y 403 se producen en los filtros de seguridad; el *entry point* y el *access denied handler* los reenvían al `HandlerExceptionResolver` para que el `GlobalExceptionHandler` los devuelva con la estructura de error estándar.
- CORS configurado para el frontal de prueba (solo para desarrollo).

**Código.** Sin lambdas ni Stream API en `src/main`, salvo en la configuración de seguridad (permitido por el enunciado). Javadoc en todas las clases y métodos de `src/main`.

---

## Extras y cambios respecto al enunciado

- **Lombok** (`@Getter`, `@Setter` y `@NoArgsConstructor`), solo en las entidades JPA, para reducir el código repetitivo. No se usa `@Data` porque el `equals`, el `hashCode` y el `toString` que genera dan problemas con JPA (id nulo antes de guardar, carga LAZY y recursión).
- **DTOs como `record`** de Java: inmutables y concisos.
- **`ErrorResponseDTO`**: la respuesta de error se nombra con el sufijo `ResponseDTO` para seguir la convención de nombres de los DTO del enunciado.
- **`PageResponseDTO<T>`** genérico para la respuesta paginada, con los campos exactos del enunciado.
- Se han implementado **los tres filtros** de libros (`categoryId`, `authorId` y `available`); el enunciado pide al menos uno.

---

## Docker (requisito opcional)

La imagen se construye con un `Dockerfile` multi-stage (Maven con Java 21 para compilar y `eclipse-temurin:21-jre-alpine` para ejecutar). Dentro del contenedor se activa el perfil `docker` (`application-docker.properties`), que sustituye MySQL por una base de datos **H2 en memoria** con `ddl-auto=create-drop`. El script `db/init.sql` no se usa en este perfil, así que la base de datos arranca vacía. Fuera del contenedor, la aplicación sigue funcionando con MySQL.

Desde la raíz del proyecto:

```bash
docker build -t library:1.0 .
docker run -p 8080:8080 library:1.0
```

Para comprobarlo en Swagger UI (`http://localhost:8080/swagger-ui.html`): hacer login con `POST /auth/login`, autorizar con el token, crear una categoría con `POST /api/v1/categories` y listarla con `GET /api/v1/categories`.
---


## Tests

12 tests con JUnit 5, Mockito y MockMvc, con el patrón Given / When / Then:

| Clase | Tests | Casos del enunciado |
|---|---|---|
| `LoanServiceImplTest` | 5 | 1, 2, 3 (stock y estado) y 4 |
| `BookControllerTest` | 4 | 5, 6, un 200 y 10 (403 con la seguridad real) |
| `LoanMapperTest` | 1 | 7 |
| `BookStockValidatorTest` | 1 | 8 |
| `JwtServiceTest` | 1 | 9 |