package dao;

import database.DatabaseConnection;
import model.Habitacion;
import model.enums.Estado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {
    //CREATE
    public void insertarHabitacion(Habitacion habitacion) {
        String sql = "INSERT INTO habitacion(numero, planta, estado) VALUES (?, ?, ?::estado)";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, habitacion.getNumero());
            ps.setString(2, habitacion.getPlanta());
            ps.setString(3, habitacion.getEstado().name()); // Convertimos Enum a texto

            ps.executeUpdate();
            System.out.println("Habitación insertada correctamente en la base de datos");

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(" Error al insertar la habitación");
            throw new RuntimeException(e);
        }
    }
    //READ
    public List<Habitacion> listarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM habitacion";

        try {
            Connection connection = DatabaseConnection.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Habitacion habitacion = new Habitacion(
                        rs.getInt("id_habitacion"),
                        rs.getInt("numero"),
                        rs.getString("planta"),
                        Estado.valueOf(rs.getString("estado")) // Convertimos String a Enum
                );
                habitaciones.add(habitacion);
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(" Error al listar habitaciones.");
            throw new RuntimeException(e);
        }
        return habitaciones;
    }

    //UPDATE
    public void actualizarHabitacion(Habitacion habitacion) {
        String sql = "UPDATE habitacion SET numero = ?, planta = ?, estado = ?::estado WHERE id_habitacion = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, habitacion.getNumero());
            ps.setString(2, habitacion.getPlanta());
            ps.setString(3, habitacion.getEstado().name());

            // Seleccionamos id de la habitación que queremos actualizar
            ps.setInt(4, habitacion.getIdHabitacion());

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el número de cuantas filas se han actualizado
            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("Error: No se pudo actualizar. No existe ninguna Habitación con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(" Error al actualizar la habitación.");
            throw new RuntimeException(e);
        }
    }

    //DELETE
    public void eliminarHabitacion(int idHabitacion) {
        String sql = "DELETE FROM habitacion WHERE id_habitacion = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            // Seleccionamos id de la habitación que queremos eliminar
            ps.setInt(1, idHabitacion);

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el numero de cuantas filas se han eliminado
            if (filasAfectadas > 0) {
                System.out.println("Habitación eliminada de la base de datos.");
            } else {
                System.out.println("Error: No se ha encontrado ninguna habitación con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar la habitación.");
            throw new RuntimeException(e);
        }
    }
}