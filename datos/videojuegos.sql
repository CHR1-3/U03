DROP TABLE IF EXISTS listas_videojuegos;
DROP TABLE IF EXISTS listas;
DROP TABLE IF EXISTS jugadores;
DROP TABLE IF EXISTS videojuegos;

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

INSERT INTO videojuegos (titulo, genero, anio, nota) VALUES
    ('The Legend of Zelda: Breath of the Wild', 'Aventura', 2017, 9.7),
    ('Elden Ring', 'Soulslike', 2022, 9.6),
    ('Hollow Knight', 'Metroidvania', 2017, 9.4),
    ('Red Dead Redemption 2', 'Acción', 2018, 9.8),
    ('Portal 2', 'Puzles', 2011, 9.5);

INSERT INTO jugadores (nickname, email) VALUES
    ('GeraltDeRivia', 'geralt@kaermorhen.com'),
    ('AloyNora', 'aloy@meridian.com'),
    ('KratosSparta', 'kratos@midgard.com');

INSERT INTO listas (nombre, id_jugador) VALUES
    ('Favoritos RPG', 1),
    ('Pendientes de jugar', 2),
    ('Obras Maestras', 3);

INSERT INTO listas_videojuegos (id_lista, id_videojuego, fecha_añadido) VALUES
    (1, 2, '2026-11-01'),
    (1, 4, '2026-11-05'),
    (2, 3, '2026-11-10'),
    (3, 1, '2026-11-15'),
    (3, 5, '2026-11-20');