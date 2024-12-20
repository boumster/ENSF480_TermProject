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
    private static ArrayList<Ticket> listTickets = new ArrayList<Ticket>();
    private static ArrayList<Mail> listMails = new ArrayList<Mail>();

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
    public void initData() {
        listMovies.clear();
        listTheatres.clear();
        listShowtimes.clear();
        listRegUsers.clear();
        listUsers.clear();
        listTickets.clear();
        listMails.clear();
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
                    Movie movie = new Movie(rs.getInt("movieID"), rs.getString("Movie_name"),
                            rs.getString("Movie_description"), rs.getString("Movie_genre"),
                            rs.getString("Movie_rating"), rs.getInt("Movie_duration"));
                    listMovies.add(movie);
                }
            }

            selectQuery = "SELECT * FROM user";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    RegUser user = new RegUser(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getInt("PaymentInfo"), rs.getDouble("credits"), rs.getBoolean("IsRegisteredUser"));
                    listRegUsers.add(user);
                }
            }

            selectQuery = "SELECT * FROM auditorium";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    for (Theatre theatre : listTheatres) {
                        if (theatre.getId() == rs.getInt("theatre_id")) {
                            Auditorium auditorium = new Auditorium(rs.getInt("auditorium_id"), rs.getInt("capacity"),
                                    theatre);
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
                    if (movie == null) {
                        System.out.println("Movie not found for ID: " + rs.getInt("movieID"));
                        continue; // Skip this showtime entry
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
                    if (aud == null) {
                        System.out.println("Auditorium not found for ID: " + rs.getInt("auditoriumID"));
                        continue; // Skip this showtime entry
                    }
                    Showtime showtime = new Showtime(rs.getInt("showtimeID"), rs.getTimestamp("time").toLocalDateTime(),
                            aud, movie);
                    listShowtimes.add(showtime);
                }
            }

            selectQuery = "SELECT * FROM tickets";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    Showtime showtime = null;
                    for (Showtime s : listShowtimes) {
                        if (s.getShowtimeId() == rs.getInt("showtimeID")) {
                            showtime = s;
                            break;
                        }
                    }
                    RegUser user = null;
                    int userID = rs.getInt("userID");
                    if (userID != 0) {
                        for (RegUser u : listRegUsers) {
                            if (u.getUserID() == userID) {
                                user = u;
                                break;
                            }
                        }
                    }
                    if (showtime == null || user == null) {
                        System.out.println("Showtime or user not found for ticket ID: " + rs.getInt("ID"));
                        continue; // Skip this ticket entry
                    }
                    showtime.bookSeat(rs.getInt("seatNumber"));
                    Ticket ticket = new Ticket(rs.getInt("ID"), showtime, rs.getDouble("price"),
                            rs.getInt("seatNumber"), user);
                    listTickets.add(ticket);
                }
            }

            selectQuery = "SELECT * FROM mails";
            try (ResultSet rs = read(selectQuery)) {
                while (rs.next()) {
                    int mailID = rs.getInt("mailID");
                    int userID = rs.getInt("userID");
                    int ticketID = rs.getInt("ticketID");
                    String message = rs.getString("message");
                    LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();

                    // Find associated User
                    User user = null;
                    if (userID != 0) {
                        for (User u : listRegUsers) { // Assuming listRegUsers is the collection of User objects
                            if (u.getUserID() == userID) {
                                user = u;
                                break;
                            }
                        }
                    }

                    // Find associated Ticket
                    Ticket ticket = null;
                    if (ticketID != 0) {
                        for (Ticket t : listTickets) { // Assuming listTickets is the collection of Ticket objects
                            if (t.getTicketId() == ticketID) {
                                ticket = t;
                                break;
                            }
                        }
                    }

                    // If user or ticket not found, log and skip
                    if (user == null) {
                        System.out.println("User not found for Mail ID: " + mailID);
                    }
                    if (ticket == null && ticketID != 0) {
                        System.out.println("Ticket not found for Mail ID: " + mailID);
                    }

                    // Create Mail object and add it to the list
                    Mail mail = new Mail(mailID, user, ticket, message, time);
                    listMails.add(mail); // Assuming listMails is the collection of Mail objects
                }
            } catch (SQLException e) {
                System.err.println("Error fetching mails: " + e.getMessage());
                e.printStackTrace();
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
            int result = stmt.executeUpdate();
            initData();
            return result; // Return number of affected rows
        }
    }

    // Delete method
    public int delete(String query, Object... parameters) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, parameters);
            int result = stmt.executeUpdate();
            initData();
            return result; // Return number of affected rows
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

    public static ArrayList<Ticket> getListTickets() {
        return listTickets;
    }

    public static ArrayList<Mail> getListMails() {
        return listMails;
    }

    public static RegUser getRegUser(int userId) {
        for (RegUser user : listRegUsers) {
            if (user.getUserID() == userId) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<Ticket> getUserTickets(int userId) {
        ArrayList<Ticket> userTickets = new ArrayList<Ticket>();
        for (Ticket ticket : listTickets) {
            if (ticket.getUser().getUserID() == userId) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }
}