import java.time.LocalDate

fun main() {
    var opcion: Int

    do {
        println("\n===== CATÁLOGO DE VIDEOJUEGOS =====")
        println("--- VIDEOJUEGOS ---")
        println("1. Ver todos los videojuegos")
        println("2. Buscar videojuego por ID")
        println("3. Añadir videojuego")
        println("4. Modificar videojuego")
        println("5. Eliminar videojuego")
        println("--- JUGADORES ---")
        println("6. Crear jugador")
        println("7. Eliminar jugador")
        println("--- LISTAS ---")
        println("8. Crear lista")
        println("9. Eliminar lista")
        println("10. Añadir videojuego a una lista")
        println("11. Ver videojuegos de una lista")
        println("0. Salir")
        println("====================================")
        print("Escoge una opción: ")

        opcion = readln().toIntOrNull() ?: -1

        when (opcion) {
            1 -> {
                println("Lista de Videojuegos:")
                VideojuegoDAO.listarVideojuegos().forEach {
                    println(" - [${it.id}] ${it.titulo} (${it.genero}), anio: ${it.anio} nota: ${it.nota}")
                }
            }
            2 -> {
                print("Introduce el ID del videojuego: ")
                val id = readln().toIntOrNull() ?: 0
                val videojuego = VideojuegoDAO.consultarVideojuegoPorId(id)
                if (videojuego != null) {
                    println("Videojuego encontrado: [${videojuego.id}] ${videojuego.titulo} (${videojuego.genero}), anio: ${videojuego.anio} nota: ${videojuego.nota}")
                } else {
                    println("No se encontró ningún videojuego con ese ID.")
                }
            }
            3 -> {
                print("Título: ")
                val titulo = readln()
                print("Género: ")
                val genero = readln()
                print("Año: ")
                val anio = readln().toIntOrNull() ?: 2026
                print("Nota: ")
                val nota = readln().toDoubleOrNull() ?: 0.0

                VideojuegoDAO.insertarVideojuegos(
                    Videojuego(
                        titulo = titulo,
                        genero = genero,
                        anio = anio,
                        nota = nota
                    )
                )
            }
            4 -> {
                print("ID del videojuego a actualizar: ")
                val id = readln().toIntOrNull() ?: 0
                print("Nuevo título: ")
                val titulo = readln()
                print("Nuevo género: ")
                val genero = readln()
                print("Nuevo año: ")
                val anio = readln().toIntOrNull() ?: 2026
                print("Nueva nota: ")
                val nota = readln().toDoubleOrNull() ?: 0.0

                VideojuegoDAO.actualizarVideojuegos(
                    Videojuego(
                        id = id,
                        titulo = titulo,
                        genero = genero,
                        anio = anio,
                        nota = nota
                    )
                )
            }
            5 -> {
                print("ID del videojuego a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                VideojuegoDAO.eliminarVideojuego(id)
            }
            6 -> {
                print("Nickname: ")
                val nickname = readln()
                print("Email: ")
                val email = readln()
                JugadorDAO.insertarJugador(Jugador(nickname = nickname, email = email))
            }
            7 -> {
                print("ID del jugador a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                JugadorDAO.eliminarJugador(id)
            }
            8 -> {
                print("Nombre de la lista: ")
                val nombre = readln()
                print("ID del jugador al que pertenece: ")
                val idJugador = readln().toIntOrNull() ?: 0
                ListaDAO.insertarLista(Lista(nombre = nombre, idJugador = idJugador))
            }
            9 -> {
                print("ID de la lista a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                ListaDAO.eliminarLista(id)
            }
            10 -> {
                print("ID de la lista: ")
                val idLista = readln().toIntOrNull() ?: 0
                print("ID del videojuego: ")
                val idVideojuego = readln().toIntOrNull() ?: 0
                val fecha = LocalDate.now().toString()
                ListaVideojuegoDAO.anyadirALista(idLista, idVideojuego, fecha)
            }
            11 -> {
                print("ID de la lista: ")
                val idLista = readln().toIntOrNull() ?: 0
                val videojuegos = ListaVideojuegoDAO.obtenerVideojuegosDeLista(idLista)
                if (videojuegos.isEmpty()) {
                    println("No hay videojuegos en esta lista o la lista no existe.")
                } else {
                    println("Videojuegos de la lista $idLista:")
                    videojuegos.forEach {
                        println(" - [${it.id}] ${it.titulo} (${it.genero}), anio: ${it.anio} nota: ${it.nota}")
                    }
                }
            }
            0 -> println("Saliendo...")
            else -> println("Opción no válida.")
        }
    } while (opcion != 0)
}
