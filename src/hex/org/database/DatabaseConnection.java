package hex.org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sis_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Shraddha@2003"; 

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connected to Database successfully!!!!");
        return conn;
    }
}
