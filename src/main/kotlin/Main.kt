fun main() {
    val conn = ConexionBD.getConnection()
    if (conn != null) {
        println("Conectado a la BD correctamente.")
        ConexionBD.closeConnection(conn)
    }
    // Listar todos los videojuegos
    println("Lista de Videojuegos:")
    VideojuegoDAO.listarVideojuegos().forEach {
        println(" - [${it.id}] ${it.titulo} (${it.genero}), anio: ${it.anio} nota: ${it.nota}€")
    }

    // Consultar videojuego por ID
    val videojuego = VideojuegoDAO.consultarVideojuegoPorId(1)
    if (videojuego != null) {
        println("Planta encontrada: [${videojuego.id}] ${videojuego.titulo} (${videojuego.genero}), anio: ${videojuego.anio} nota: ${videojuego.nota}€")
    } else {
        println("No se encontró ninguna planta con ese ID.")
    }

    // Insertar videojuego nuevo
    VideojuegoDAO.insertarVideojuegos(
        Videojuego(
            titulo = "Elden Ring",
            genero = "Soul-like",
            anio = 2022,
            nota = 9.6
        )
    )

    // Actualizar videojuego con id=1 (Aloe Vera)
    VideojuegoDAO.actualizarVideojuegos(
        Videojuego(
            titulo = "Candy Crush",
            genero = "rompecabezas",
            anio = 2012,
            nota = 10.0
        )
    )

    // Eliminar videojuego con id=2 (Lavanda)
    VideojuegoDAO.eliminarVideojuego(2)
}
