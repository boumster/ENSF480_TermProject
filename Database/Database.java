import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/acmeplex";
    private static final String USER = "ENSF480_11"; 
    private static final String PASSWORD = "ENSF480"; 
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    // insert
    public static int insertData(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

    // get data
    public static ResultSet getData(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query);
        }
    }

    // delete data
    public static int deleteData(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

    // close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
