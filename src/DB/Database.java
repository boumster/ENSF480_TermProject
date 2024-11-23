package src.DB;

import src.Entity.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/acmeplex";
    private final String USER = "ENSF480";
    private static final String PASSWORD = "1234";

    private static Database instance;
    private Connection connection;

    private static boolean isInitialized = false;
    private static ArrayList<Movie> listMovies = new ArrayList<Movie>();
    private static ArrayList<Theatre> listTheatres = new ArrayList<Theatre>();
    private static ArrayList<Showtime> listShowtimes = new ArrayList<Showtime>();
    private static ArrayList<RegUser> listRegUsers = new ArrayList<RegUser>();
    private static ArrayList<User> listUsers = new ArrayList<User>();
    private static ArrayList<Booking> listBookings = new ArrayList<Booking>();

    // Constructor to establish the database connection
    private Database() throws SQLException {
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

    // Singleton pattern to ensure only one instance of the database connection
    public static synchronized Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
            if (!isInitialized) {
                instance.initData();
                isInitialized = true;
            }
        }
        return instance;
    }

    // Get Data from the database
    private void initData() {
        listMovies.clear();
        listTheatres.clear();
        listShowtimes.clear();
        listRegUsers.clear();
        listUsers.clear();
        listBookings.clear();
        try {
            String selectQuery = "SELECT * FROM theatre";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    Theatre theatre = new Theatre(rs.getString("theatre_name"), rs.getInt("theatre_id"));
                    listTheatres.add(theatre);
                }
            }

            selectQuery = "SELECT * from movies";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    System.out.println(rs.getString("Movie_name"));
                    Movie movie = new Movie(rs.getInt("movieID"), rs.getString("Movie_name"),
                            rs.getString("Movie_description"), rs.getString("Movie_genre"), 
                            rs.getString("Movie_rating"), rs.getInt("Movie_duration"));
                    listMovies.add(movie);
                }
            }

            selectQuery = "SELECT * FROM user";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    RegUser user = new RegUser(rs.getString("Username"), rs.getString("Email"), rs.getString("Address"),
                            rs.getInt("PaymentInfo"));
                    listRegUsers.add(user);
                }
            }

            selectQuery = "SELECT * FROM auditorium";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    for (Theatre theatre : listTheatres) {
                        if (theatre.getId() == rs.getInt("theatre_id")) {
                        Auditorium auditorium = new Auditorium(rs.getInt("auditorium_id"), rs.getInt("capacity"), theatre);
                            theatre.addAuditorium(auditorium);
                            break;
                        }
                    }
                }
            }

            selectQuery = "SELECT * FROM containsMovie";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    int theatreID = rs.getInt("theatre_id");
                    int movieID = rs.getInt("movieID");
                    for (Theatre theatre : listTheatres) {
                        if (theatre.getId() == theatreID) {
                            for (Movie movie : listMovies) {
                                if (movie.getId() == movieID) {
                                    theatre.addMovie(movie);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }

            selectQuery = "SELECT * FROM showtimes";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    Movie movie = null;
                    for (Movie m : listMovies) {
                        if (m.getId() == rs.getInt("movieID")) {
                            movie = m;
                            break;
                        }
                    }
                    Auditorium aud = null;
                    for (Theatre theatre : listTheatres) {
                        for (Auditorium a : theatre.getAuditoriums()) {
                            if (a.getAudId() == rs.getInt("auditoriumID")) {
                                aud = a;
                                break;
                            }
                        }
                    }
                    Showtime showtime = new Showtime(rs.getTimestamp("time").toLocalDateTime(), aud, movie);
                    listShowtimes.add(showtime);
                }
            }

            selectQuery = "SELECT * FROM tickets";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    Showtime showtime = null;
                    for (Showtime s : listShowtimes) {
                        if (s.getShowtime().equals(rs.getTimestamp("time").toLocalDateTime())) {
                            showtime = s;
                            break;
                        }
                    }
                    RegUser user = null;
                    for (RegUser u : listRegUsers) {
                        if (u.getEmail().equals(rs.getString("email"))) {
                            user = u;
                            break;
                        }
                    }
                    Movie movie = null;
                    for (Movie m : listMovies) {
                        if (m.getId() == rs.getInt("movieID")) {
                            movie = m;
                            break;
                        }
                    }

                    Booking booking = new Booking(rs.getInt("ID"), showtime, user, movie, rs.getInt("NumTickets"), rs.getDouble("TotalPrice"));
                    listBookings.add(booking);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create method
    public int create(String query, Object... parameters) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, parameters);
            stmt.executeUpdate();
            initData();
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
            initData();
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

    // Getter methods for the lists
    public static ArrayList<Movie> getListMovies() {
        return listMovies;
    }

    public static ArrayList<Theatre> getListTheatres() {
        return listTheatres;
    }

    public static ArrayList<Showtime> getListShowtimes() {
        return listShowtimes;
    }

    public static ArrayList<RegUser> getListRegUsers() {
        return listRegUsers;
    }

    public static ArrayList<User> getListUsers() {
        return listUsers;
    }

    public static ArrayList<Booking> getListBookings() {
        return listBookings;
    }

    public static RegUser getRegUser(String query) {
        try {
            try (ResultSet rs = getInstance().read(query)) {
                if (rs.next()) {
                    return new RegUser(rs.getString("Username"), rs.getString("Email"), rs.getString("Address"),
                            rs.getInt("PaymentInfo"));
                } else {
                    System.out.println("No user found with the provided email and password.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}