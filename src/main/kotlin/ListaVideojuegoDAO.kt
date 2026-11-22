import java.sql.Connection
import java.sql.SQLException

object ListaVideojuegoDAO {

    fun anyadirALista(idLista: Int, idVideojuego: Int, fecha: String) {
        var conn: Connection? = null
        try {
            conn = ConexionBD.getConnection()
            conn?.autoCommit = false

            val sqlSelectJuego = "SELECT id FROM videojuegos WHERE id = ?"
            conn?.prepareStatement(sqlSelectJuego)?.use { stmt ->
                stmt.setInt(1, idVideojuego)
                val rs = stmt.executeQuery()
                if (!rs.next()) {
                    throw SQLException("No existe un videojuego con el ID $idVideojuego")
                }
            }

            val sqlSelectLista = "SELECT id_lista FROM listas WHERE id_lista = ?"
            conn?.prepareStatement(sqlSelectLista)?.use { stmt ->
                stmt.setInt(1, idLista)
                val rs = stmt.executeQuery()
                if (!rs.next()) {
                    throw SQLException("No existe una lista con el ID $idLista")
                }
            }

            val sqlInsert = "INSERT INTO listas_videojuegos(id_lista, id_videojuego, fecha_añadido) VALUES (?, ?, ?)"
            conn?.prepareStatement(sqlInsert)?.use { stmt ->
                stmt.setInt(1, idLista)
                stmt.setInt(2, idVideojuego)
                stmt.setString(3, fecha)
                stmt.executeUpdate()
            }

            conn?.commit()
            println("Videojuego añadido a la lista con éxito.")
        } catch (e: Exception) {
            conn?.rollback()
            println("Error en la transacción: ${e.message}")
            println("No se modificó nada.")
        } finally {
            conn?.autoCommit = true
            conn?.close()
        }
    }

    fun obtenerVideojuegosDeLista(idLista: Int): List<Videojuego> {
        val videojuegos = mutableListOf<Videojuego>()
        val sql = """
            SELECT v.id, v.titulo, v.genero, v.anio, v.nota 
            FROM videojuegos v
            INNER JOIN listas_videojuegos lv ON v.id = lv.id_videojuego
            WHERE lv.id_lista = ?
        """.trimIndent()

        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, idLista)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    videojuegos.add(
                        Videojuego(
                            id = rs.getInt("id"),
                            titulo = rs.getString("titulo"),
                            genero = rs.getString("genero"),
                            anio = rs.getInt("anio"),
                            nota = rs.getDouble("nota")
                        )
                    )
                }
            }
        }
        return videojuegos
    }
}