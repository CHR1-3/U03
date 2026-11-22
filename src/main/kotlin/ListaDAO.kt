object ListaDAO {

    fun insertarLista(lista: Lista) {
        if (!JugadorDAO.existeJugador(lista.idJugador)) {
            println("Error: No existe ningún jugador con ID ${lista.idJugador}. No se creó la lista.")
            return
        }

        val sql = "INSERT INTO listas(nombre, id_jugador) VALUES (?, ?)"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, lista.nombre)
                stmt.setInt(2, lista.idJugador)
                if (stmt.executeUpdate() > 0) {
                    println("Lista '${lista.nombre}' creada y asignada al jugador ${lista.idJugador}.")
                }
            }
        }
    }

    fun eliminarLista(id: Int) {
        val sql = "DELETE FROM listas WHERE id_lista = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                if (stmt.executeUpdate() > 0) {
                    println("Lista con ID $id eliminada con éxito.")
                } else {
                    println("No se encontró ninguna lista con ID $id.")
                }
            }
        }
    }
}