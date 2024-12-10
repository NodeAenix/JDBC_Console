package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static Connection connection;

    private DB() {}

    private static void init() {
        final String URL = "jdbc:mariadb://localhost:3306/db_empleados";
        final String USER = "root";
        final String PASSWORD = "";

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("--------------------");
            System.out.println("Conexión establecida");
            System.out.println(connection.getCatalog());
            System.out.println("--------------------");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            init();
        }
        return connection;
    }
}
