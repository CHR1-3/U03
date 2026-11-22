# Catálogo de Videojuegos con BD Relacional

Programa de consola en Kotlin para gestionar un catálogo de videojuegos, jugadores y listas personalizadas.

## 1. Estructura de datos

### **Data Classes:**

```kotlin
data class Videojuego(
    val id: Int? = null,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val nota: Double
)

data class Jugador(
    val id: Int? = null,
    val nickname: String,
    val email: String
)

data class Lista(
    val id: Int? = null,
    val nombre: String,
    val idJugador: Int
)

data class ListaVideojuego(
    val idLista: Int,
    val idVideojuego: Int,
    val fechaAniadido: String
)
```

### **Esquema de la Base de Datos Relacional (`datos/videojuegos.sql`):**

1. **`videojuegos`**: Almacena los datos de cada título.
    - `id`: `SERIAL PRIMARY KEY`
    - `titulo`: `TEXT NOT NULL`
    - `genero`: `TEXT NOT NULL`
    - `anio`: `INTEGER NOT NULL`
    - `nota`: `REAL NOT NULL`

2. **`jugadores`**: Registra los usuarios propietarios de las listas.
    - `id`: `SERIAL PRIMARY KEY`
    - `nickname`: `TEXT NOT NULL UNIQUE`
    - `email`: `TEXT NOT NULL`

3. **`listas`**: Almacena las listas personalizadas asociadas a un jugador concreto (relación 1:N).
    - `id_lista`: `SERIAL PRIMARY KEY`
    - `nombre`: `TEXT NOT NULL`
    - `id_jugador`: `INTEGER NOT NULL` — **FK** hacia `jugadores(id)` con `ON DELETE CASCADE`

4. **`listas_videojuegos`**: Tabla intermedia que resuelve la relación N:M entre listas y videojuegos.
    - `id_lista`: `INTEGER NOT NULL` — **PK** y **FK** hacia `listas(id_lista)` con `ON DELETE CASCADE`
    - `id_videojuego`: `INTEGER NOT NULL` — **PK** y **FK** hacia `videojuegos(id)` con `ON DELETE CASCADE`
    - `fecha_añadido`: `TEXT NOT NULL`

```sql
CREATE TABLE videojuegos (
    id SERIAL PRIMARY KEY,
    titulo TEXT NOT NULL,
    genero TEXT NOT NULL,
    anio INTEGER NOT NULL,
    nota REAL NOT NULL
);

CREATE TABLE jugadores (
    id SERIAL PRIMARY KEY,
    nickname TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL
);

CREATE TABLE listas (
    id_lista SERIAL PRIMARY KEY,
    nombre TEXT NOT NULL,
    id_jugador INTEGER NOT NULL,
    CONSTRAINT fk_jugador FOREIGN KEY (id_jugador) 
        REFERENCES jugadores(id) ON DELETE CASCADE
);

CREATE TABLE listas_videojuegos (
    id_lista INTEGER NOT NULL,
    id_videojuego INTEGER NOT NULL,
    fecha_añadido TEXT NOT NULL,
    PRIMARY KEY (id_lista, id_videojuego),
    CONSTRAINT fk_lista FOREIGN KEY (id_lista) 
        REFERENCES listas(id_lista) ON DELETE CASCADE,
    CONSTRAINT fk_videojuego FOREIGN KEY (id_videojuego) 
        REFERENCES videojuegos(id) ON DELETE CASCADE
);
```

## 2. Instrucciones de ejecución

- **Requisitos previos**: IntelliJ IDEA con soporte Gradle y un servidor **PostgreSQL**.
- **Creación de la base de datos**: Crea una base de datos llamada `videojuegos` y ejecuta el script completo ubicado en `datos/videojuegos.sql`.
- **Configuración de la conexión**: Cambia `URL`, `USER` y `PASSWORD` en `src/main/kotlin/ConexionBD.kt` para verificar que coinciden tu servidor PostgreSQL.

## 3. Decisiones de diseño

- Elegí **PostgreSQL** como SGBD cliente-servidor utilizando el tipo `SERIAL`
- Diseñé la tabla `jugadores` con los campos `nickname` (con restricción `UNIQUE` para evitar usuarios duplicados) y `email` como datos para identifiar cada perfil.
- Validé previamente la existencia del jugador en `ListaDAO` antes de insertar una nueva lista.
- Implementé el patrón **DAO** separado por entidades (`VideojuegoDAO`, `JugadorDAO`, `ListaDAO` y `ListaVideojuegoDAO`).
