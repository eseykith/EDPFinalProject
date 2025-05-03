import java.sql.*;

public class DatabaseConnection {
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=HotelReservation;encrypt=true;trustServerCertificate=true";
    private static final String user = "HotelUser";
    private static final String pass = "HotelUserDB123";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;    }

}


