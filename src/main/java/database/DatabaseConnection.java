package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/planta_hospitalaria";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    public static Connection conectar(){
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a la Base de Datos exitosa!");
            return conn;

        }catch (SQLException e){
            System.out.println("Error al conectar a la Base de Datos!");
            throw new RuntimeException("Error de conexion", e);
        }

    }
}
