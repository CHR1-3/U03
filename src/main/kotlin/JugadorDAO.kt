object JugadorDAO {

    fun insertarJugador(jugador: Jugador) {
        val sql = "INSERT INTO jugadores(nickname, email) VALUES (?, ?)"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, jugador.nickname)
                stmt.setString(2, jugador.email)
                if (stmt.executeUpdate() > 0) {
                    println("Jugador '${jugador.nickname}' creado con éxito.")
                }
            }
        }
    }

    fun eliminarJugador(id: Int) {
        val sql = "DELETE FROM jugadores WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                if (stmt.executeUpdate() > 0) {
                    println("Jugador con ID $id eliminado (junto con sus listas asociadas).")
                } else {
                    println("No se encontró ningún jugador con ID $id.")
                }
            }
        }
    }

    fun existeJugador(id: Int): Boolean {
        val sql = "SELECT id FROM jugadores WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                val rs = stmt.executeQuery()
                return rs.next()
            }
        }
        return false
    }
}