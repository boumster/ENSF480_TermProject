package src.Control;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import src.DB.Database;
import src.Entity.Movie;
import src.Entity.Mail;
import src.Entity.RegUser;

public class AdminControl {
    private Database db;

    public AdminControl(){
        try {
            db = Database.getInstance();
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addMovie(String name, String description, String genre, String rating, int duration) throws SQLException {
        try {
            String query = "INSERT INTO movies (Movie_name, Movie_description, Movie_Genre, Movie_rating, Movie_Duration) VALUES (?, ?, ?, ?, ?)";
            int rowsAffected = db.update(query, name, description, genre, rating,duration);
            if (rowsAffected > 0) {
                System.out.println("Movie added successfully");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(String movieName) throws SQLException {
        try {
            String query = "DELETE FROM movies WHERE Movie_name = ?";
            int rowsAffected = db.update(query, movieName);
            if (rowsAffected > 0) {
                System.out.println("Movie deleted successfully.");
            } else {
                System.out.println("No movie found with the given name.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting movie: " + e.getMessage());
            throw e;
        }
    }

    public ArrayList<String> getAllMovieNames() throws SQLException {
        ArrayList<Movie> movies = db.getListMovies();
        ArrayList<String> movieNames = new ArrayList<String>();
        for (int i = 0; i< movies.size(); i++){
            movieNames.add(movies.get(i).getTitle());

        }
        return movieNames;
    }

    public ArrayList<Mail> getUsersMail(int UserID) {
        ArrayList<Mail> userMails = new ArrayList<Mail>();
        for (Mail mail : db.getListMails()) {
            if (mail.getUser().getUserID() == UserID) {
                userMails.add(mail);
            }
        }
        return userMails;
    }

    public void sendEmail(int userId, Integer ticketId, String message) {
        if (message == null || message.trim().isEmpty()) {
            System.err.println("Message cannot be empty.");
            return;
        }

        try {
            // Get the current time and format it for database compatibility
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            // Insert the mail into the database with the current time
            String query = "INSERT INTO mails (userID, ticketID, message, time) VALUES (?, ?, ?, ?)";
            int rowsAffected = db.update(query, userId, ticketId, message, formattedTime);

            if (rowsAffected > 0) {
                System.out.println("Email sent successfully to User ID: " + userId);
                System.out.println("Time Sent: " + formattedTime);
            } else {
                System.err.println("Failed to send email. Please try again.");
            }

        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendEmailToRegisteredUsers(String message) {
        if (message == null || message.trim().isEmpty()) {
            System.err.println("Message cannot be empty.");
            return;
        }
    
        try {
            // Get the list of all registered users
            ArrayList<RegUser> allRegUsers = db.getListRegUsers(); // Assuming this method returns a list of registered users
            System.out.println("Total registered users fetched: " + allRegUsers.size());
    
            // Define a valid ticket ID (replace this with your actual logic to get a valid ticket ID)
            Integer defaultTicketId = null; // Replace with your actual method to get a valid ticket ID
    
            // Iterate through all registered users and send email
            for (RegUser regUser : allRegUsers) {
                int userId = regUser.getUserID(); // Get the user ID
                sendEmail(userId, defaultTicketId, message); // Send email to each registered user with a valid ticket ID
                System.out.println("Email sent to registered User ID: " + userId);
            }
    
        } catch (Exception e) {
            System.err.println("Error sending email to registered users: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

}

