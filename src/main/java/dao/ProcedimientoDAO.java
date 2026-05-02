package dao;

import database.DatabaseConnection;
import model.Procedimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedimientoDAO {
    //CREATE
    public void insertarProcedimiento(Procedimiento procedimiento) {
        String sql = "INSERT INTO procedimiento(fecha_hora, observaciones, id_paciente, id_personal) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            // Traducimos el LocalDateTime a Timestamp para SQL
            ps.setTimestamp(1, Timestamp.valueOf(procedimiento.getFechaHora()));
            ps.setString(2, procedimiento.getObservaciones());
            ps.setInt(3, procedimiento.getIdPaciente());
            ps.setInt(4, procedimiento.getIdPersonal());

            ps.executeUpdate();
            System.out.println("Procedimiento registrado correctamente.");

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al registrar el procedimiento.");
            throw new RuntimeException(e);
        }
    }
    //READ
    public List<Procedimiento> listarProcedimientos() {
        List<Procedimiento> listaProcedimientos = new ArrayList<>();
        String sql = "SELECT * FROM procedimiento";

        try {
            Connection connection = DatabaseConnection.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Procedimiento procedimiento = new Procedimiento(
                        rs.getInt("id_procedimiento"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(), // Pasamos de Timestamp a LocalDateTime
                        rs.getString("observaciones"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_personal")
                );

                listaProcedimientos.add(procedimiento);
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al listar los procedimientos.");
            throw new RuntimeException(e);
        }
        return listaProcedimientos;
    }
    //UPDATE
    public void actualizarProcedimiento(Procedimiento procedimiento) {
        String sql = "UPDATE procedimiento SET fecha_hora = ?, observaciones = ?, id_paciente = ?, id_personal = ? WHERE id_procedimiento = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(procedimiento.getFechaHora()));
            ps.setString(2, procedimiento.getObservaciones());
            ps.setInt(3, procedimiento.getIdPaciente());
            ps.setInt(4, procedimiento.getIdPersonal());

            ps.setInt(5, procedimiento.getIdProcedimiento());

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el número de cuantas filas se han actualizado
            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado correctamente en la base de datos.");
            } else {
                System.out.println("Error: No se pudo actualizar. No existe ningún procedimiento con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el procedimiento");
            throw new RuntimeException(e);
        }
    }
    //DELETE
    public void eliminarProcedimiento(int idProcedimiento) {
        String sql = "DELETE FROM procedimiento WHERE id_procedimiento = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, idProcedimiento);

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el numero de cuantas filas se han eliminado
            if (filasAfectadas > 0) {
                System.out.println("Procedimiento eliminado de la base de datos.");
            } else {
                System.out.println("Error: No se ha encontrado ningún procedimiento con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar el procedimiento");
            throw new RuntimeException(e);
        }
    }

    //Consulta Avanzada Procedimientos y Paciente (JOIN y WHERE)

    public List<String> verHistorialDePaciente(int idPacienteBuscado) {
        List<String> historial = new ArrayList<>();
        String sql = "SELECT pr.fecha_hora, pr.observaciones, " +
                "pa.nombre AS nombre_paciente, " +
                "pe.nombre AS nombre_personal, pe.apellidos AS apellidos_personal, pe.categoria " +
                "FROM procedimiento pr " +
                "JOIN paciente pa ON pr.id_paciente = pa.id_paciente " +
                "JOIN personal pe ON pr.id_personal = pe.id_personal " +
                "WHERE pa.id_paciente = ? " +
                "ORDER BY pr.fecha_hora DESC";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idPacienteBuscado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String linea = "Fecha: " + rs.getTimestamp("fecha_hora") +
                        " | Paciente: " + rs.getString("nombre_paciente") +
                        " | Procedimiento: " + rs.getString("observaciones") +
                        " | Realizado por: " + rs.getString("nombre_personal") + " " +
                        rs.getString("apellidos_personal") + " (" + rs.getString("categoria") + ")";
                historial.add(linea);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar el historial del paciente en la base de datos.");
            throw new RuntimeException(e);
        }
        return historial;
    }
}