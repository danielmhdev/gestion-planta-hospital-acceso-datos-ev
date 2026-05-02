package dao;

import database.DatabaseConnection;
import model.Personal;
import model.enums.Categoria;
import model.enums.Turno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO {
    //CREATE
    public void insertarPersonal(Personal personal) {
        String sql = "INSERT INTO personal(nombre, apellidos, categoria, turno) VALUES (?, ?, ?::categoria, ?::turno)";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, personal.getNombre());
            ps.setString(2, personal.getApellidos());
            ps.setString(3, personal.getCategoria().name());
            ps.setString(4, personal.getTurno().name());

            ps.executeUpdate();
            System.out.println("Personal insertado correctamente en la base de datos");

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al insertar el personal.");
            throw new RuntimeException(e);
        }
    }
    //READ
    public List<Personal> listarPersonal() {
        List<Personal> listaPersonal = new ArrayList<>();
        String sql = "SELECT * FROM personal";

        try {
            Connection connection = DatabaseConnection.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Personal personal = new Personal(
                        rs.getInt("id_personal"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        Categoria.valueOf(rs.getString("categoria")),
                        Turno.valueOf(rs.getString("turno"))
                );

                listaPersonal.add(personal);
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al listar el personal");
            throw new RuntimeException(e);
        }
        return listaPersonal;
    }
    //UPDATE
    public void actualizarPersonal(Personal personal) {
        String sql = "UPDATE personal SET nombre = ?, apellidos = ?, categoria = ?::categoria, turno = ?::turno WHERE id_personal = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, personal.getNombre());
            ps.setString(2, personal.getApellidos());
            ps.setString(3, personal.getCategoria().name());
            ps.setString(4, personal.getTurno().name());

            //Seleccionamos id del personal que queremos actualizar
            ps.setInt(5, personal.getIdPersonal());

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el número de cuantas filas se han actualizado
            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("Error: No se pudo actualizar. No existe ningún empleado con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el personal");
            throw new RuntimeException(e);
        }
    }
    //DELETE
    public void eliminarPersonal(int idPersonal) {
        String sql = "DELETE FROM personal WHERE id_personal = ?";

        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement ps = connection.prepareStatement(sql);

            // Seleccionamos el ID del personal a eliminar
            ps.setInt(1, idPersonal);

            // Guardamos el resultado en una variable
            int filasAfectadas = ps.executeUpdate();

            // Comprobamos si el ps.executeUpdate nos devuelve el numero de cuantas filas se han eliminado
            if (filasAfectadas > 0) {
                System.out.println("Personal eliminado de la base de datos.");
            } else {
                System.out.println("Error: No se ha encontrado ningún empleado con ese ID.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar el personal.");
            throw new RuntimeException(e);
        }
    }

    //Consulta Avanzada Personal y Categoría (GROUP BY y ORDER BY)
    public List<String> listarPersonalPorCategoria() {
        List<String> personalCategoria = new ArrayList<>();
        String sql = "SELECT categoria, COUNT(*) as total FROM personal GROUP BY categoria ORDER BY total DESC";

        try {
            Connection connection = DatabaseConnection.conectar();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String linea = "Categoría: " + rs.getString("categoria") + " -> Total: " + rs.getInt("total") + " profesional(es)";
                personalCategoria.add(linea);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista del personal por categoría.");
            throw new RuntimeException(e);
        }
        return personalCategoria;
    }

}