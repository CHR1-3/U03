import java.sql.Connection
import java.sql.SQLException

object VideojuegoDAO {

    fun listarVideojuegos(): List<Videojuego> {
        val lista = mutableListOf<Videojuego>()
        val sql = "SELECT * FROM videojuegos"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    lista.add(Videojuego(
                        id = rs.getInt("id"),
                        titulo = rs.getString("titulo"),
                        genero = rs.getString("genero"),
                        anio = rs.getInt("anio"),
                        nota = rs.getDouble("nota")
                    ))
                }
            }
        }
        return lista
    }

    fun consultarVideojuegoPorId(id: Int): Videojuego? {
        var videojuego: Videojuego? = null
        val sql = "SELECT * FROM videojuegos WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    videojuego = Videojuego(
                        id = rs.getInt("id"),
                        titulo = rs.getString("titulo"),
                        genero = rs.getString("genero"),
                        anio = rs.getInt("anio"),
                        nota = rs.getDouble("nota")
                    )
                }
            }
        }
        return videojuego
    }
    fun insertarVideojuegos(videojuego: Videojuego) {
        val sql = "INSERT INTO videojuegos(titulo, genero, anio, nota) VALUES (?, ?, ?, ?)"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, videojuego.titulo)
                stmt.setString(2, videojuego.genero)
                stmt.setInt(3, videojuego.anio)
                stmt.setDouble(4, videojuego.nota)
                if (stmt.executeUpdate() > 0) {
                    println("${videojuego.titulo} insertada con éxito.")
                }
            }
        }
    }

    fun actualizarVideojuegos(videojuego: Videojuego) {
        if (videojuego.id == null) {
            println("Error: No se puede actualizar un vieojuego sin ID.")
            return
        }
        val sql = "UPDATE videojuegos SET titulo = ?, genero = ?, anio = ?, nota = ? WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, videojuego.titulo)
                stmt.setString(2, videojuego.genero)
                stmt.setInt(3, videojuego.anio)
                stmt.setDouble(4, videojuego.nota)
                stmt.setInt(5, videojuego.id)
                if (stmt.executeUpdate() > 0) {
                    println("Videojuego con ID ${videojuego.id} actualizado.")
                } else {
                    println("No se encontró ningun videojuego con ID ${videojuego.id} para actualizar.")
                }
            }
        }
    }

    fun eliminarVideojuego(id: Int) {
        val sql = "DELETE FROM videojuegos WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                if (stmt.executeUpdate() > 0) {
                    println("Videojuego con ID $id eliminado.")
                } else {
                    println("No se encontró ningun videojuego con ID $id para eliminar.")
                }
            }
        }
    }

    fun anyadirALista(idLista: Int, idVideojuego: Int, fecha: String){
        var conn: Connection? = null

        try {
            conn = ConexionBD.getConnection()
            conn?.autoCommit = false

            val sqlSelect = "SELECT id FROM videojuegos WHERE id = ?"

            conn?.prepareStatement(sqlSelect)?.use { stmt ->
                stmt.setInt(1, idVideojuego)

                val rs = stmt.executeQuery()

                if (!rs.next()) {
                    throw SQLException("No existe un videojuego con el ID $idVideojuego")
                }
            }

            val sqlInsert = "INSERT INTO listas_videojuegos(id_lista, id_videojuego, fecha_añadido)VALUES (?, ?, ?)"

            conn?.prepareStatement(sqlInsert)?.use { stmt ->
                stmt.setInt(1, idLista)
                stmt.setInt(2, idVideojuego)
                stmt.setString(3, fecha)

                stmt.executeUpdate()
            }

            conn?.commit()
            println("Videojuego añadido a lista con exito")
        } catch (e: Exception) {
            conn?.rollback()
            println("Error: ${e.message}")
            println("No se modifico nada")
        } finally {
            conn?.autoCommit = true
            conn?.close()
        }
    }
}