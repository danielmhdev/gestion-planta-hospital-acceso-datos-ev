package dao;

import database.DatabaseConnection;
import model.Paciente;
import model.enums.Sexo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    //CREATE
    public void insertarPaciente(Paciente paciente){
        String sql = "INSERT INTO paciente(nhc, nombre, apellidos, sexo, fecha_nacimiento, id_habitacion) VALUES (?, ?, ?, ?::sexo, ?, ?)";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, paciente.getNhc());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellidos());
            ps.setString(4, paciente.getSexo().name());
            ps.setDate(5, Date.valueOf(paciente.getFechaNacimiento()));
            ps.setInt(6, paciente.getIdHabitacion());

            ps.executeUpdate();
            System.out.println("Paciente insertado correctamente en la base de datos");

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al insertar el paciente");
            throw new RuntimeException(e);
        }
    }
    //READ

    public List<Paciente> listarPacientes(){
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try {
            Connection connection = DatabaseConnection.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nhc"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        Sexo.valueOf(rs.getString("sexo")), // Pasa el String a Enum
                        rs.getDate("fecha_nacimiento").toLocalDate(),   // Pasa Date a LocalDate
                        rs.getInt("id_habitacion")
                );

                pacientes.add(paciente);

            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al listar pacientes.");

            throw new RuntimeException(e);
        }
        return pacientes;
    }
    //UPDATE
    public void actualizarPaciente(Paciente paciente) {
        String sql = "UPDATE paciente SET nhc = ?, nombre = ?, apellidos = ?, sexo = ?::sexo, fecha_nacimiento = ?, id_habitacion = ? WHERE id_paciente = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, paciente.getNhc());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellidos());
            ps.setString(4, paciente.getSexo().name());
            ps.setDate(5, Date.valueOf(paciente.getFechaNacimiento()));
            ps.setInt(6, paciente.getIdHabitacion());

            //Seleccionamos id del paciente que queremos actualizar
            ps.setInt(7, paciente.getIdPaciente());

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el número de cuantas filas se han actualizado
            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("Error: No se pudo actualizar. No existe ningún paciente con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el paciente.");
            throw new RuntimeException(e);
        }
    }
    //DELETE
    public void eliminarPaciente(int idPaciente) {
        String sql = "DELETE FROM paciente WHERE id_paciente = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            //Seleccionamos id del paciente que queremos eliminar
            ps.setInt(1, idPaciente);

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el numero de cuantas filas se han eliminado
            if (filasAfectadas > 0) {
                System.out.println("Paciente eliminado de la base de datos.");
            } else {
                System.out.println("Error: No se ha encontrado ningún paciente con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar el paciente.");
            throw new RuntimeException(e);
        }
    }

    // Buscar paciente por ID
    public Paciente obtenerPacientePorId(int idPaciente) {
        String sql = "SELECT * FROM paciente WHERE id_paciente = ?";
        Paciente paciente = null;

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idPaciente);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nhc"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        Sexo.valueOf(rs.getString("sexo")),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getInt("id_habitacion")
                );
            }
            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al buscar paciente por ID.");
            throw new RuntimeException(e);
        }
        return paciente; // Devolverá null si no encuentra a nadie
    }

    //Consulta Avanzada Paciente y Habitación (JOIN y ORDER BY)
    public List<String> listarPacientesConHabitacion() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT p.nombre, p.apellidos, h.numero " +
                "FROM paciente p " +
                "JOIN habitacion h ON p.id_habitacion = h.id_habitacion " +
                "ORDER BY h.numero ASC";

        try {
            Connection connection = DatabaseConnection.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String linea = "Habitación " + rs.getInt("numero") + " -> Paciente: " +
                        rs.getString("nombre") + " " + rs.getString("apellidos");
                lista.add(linea);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al listar pacientes por habitación.");
            throw new RuntimeException(e);
        }
        return lista;
    }
}
