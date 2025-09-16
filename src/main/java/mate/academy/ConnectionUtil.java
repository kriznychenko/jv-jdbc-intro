package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String USER = "root";
    private static final String PASSWORD = "rOOt1233";

    private ConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
