import java.sql.*;

public final class Database {
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

    public static boolean submitLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            ResultSet rs = getData(query);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void getMovies() {
        String query = "SELECT * FROM movies";
        try {
            ResultSet rs = getData(query);
            while (rs.next()) {
                System.out.println(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getShowtimes(String movieID) {
        String query = "SELECT * FROM showtimes WHERE movie_id = " + movieID;
        try {
            ResultSet rs = getData(query);
            while (rs.next()) {
                System.out.println(rs.getString("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTickets(String userID) {
        String query = "SELECT * FROM tickets WHERE user_id = " + userID;
        try {
            ResultSet rs = getData(query);
            while (rs.next()) {
                System.out.println(rs.getString("seat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTheatres() {
        String query = "SELECT * FROM theatres";
        try {
            ResultSet rs = getData(query);
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
