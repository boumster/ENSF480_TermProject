package src.DB;

import src.Entity.*;
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/acmeplex";
    private static final String USER = "ENSF480";
    private static final String PASSWORD = "1234";

    private Connection connection;

    // Constructor to establish the database connection
    public Database() throws SQLException {
        try {
            // Load the MySQL JDBC driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully.");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Please include it in your library path.", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database: " + e.getMessage(), e);
        }
    }

    // Create method
    public int create(String query, Object... parameters) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, parameters);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1; // Return generated key if available
            }
        }
    }

    // Read method
    public ResultSet read(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        setParameters(stmt, parameters);
        return stmt.executeQuery(); // Caller is responsible for closing the ResultSet
    }

    // Update method
    public int update(String query, Object... parameters) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, parameters);
            return stmt.executeUpdate(); // Return number of affected rows
        }
    }

    // Delete method
    public int delete(String query, Object... parameters) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, parameters);
            return stmt.executeUpdate(); // Return number of affected rows
        }
    }

    // Helper method to set parameters in PreparedStatement
    private void setParameters(PreparedStatement stmt, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            stmt.setObject(i + 1, parameters[i]);
        }
    }

    // Close the connection
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed successfully.");
        }
    }

    // Example usage
    public static void main(String[] args) {
        try {
            Database db = new Database();

            // Example: Create
            String insertQuery = "INSERT INTO theatre (theatre_id, theatre_name) VALUES (?, ?)";
            int generatedId = db.create(insertQuery, "78", "cool_theatre");
            System.out.println("Inserted record with ID: " + generatedId);

            // Example: Read
            String selectQuery = "SELECT * FROM user WHERE UserId = ?";
            try (ResultSet rs = db.read(selectQuery, generatedId)) {
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("Username") + ", Email: " + rs.getString("Email"));
                }
            }

            // Create an object off the database
            selectQuery = "SELECT * FROM user";
            System.out.println("Printing all users:");
            try (ResultSet rs = db.read(selectQuery)) {
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("Username") + ", Email: " + rs.getString("Email"));
                    RegUser user = new RegUser(rs.getString("Username"), rs.getString("Email"), rs.getString("Address"), rs.getInt("PaymentInfo"));
                    user.toString();
                }
            }
            

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
