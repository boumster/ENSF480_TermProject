package src.Control;

import src.DB.Database;
import src.Entity.Showtime;
import src.Entity.Movie;
import src.Entity.Theatre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShowtimeControl {
    private Database db;

    public ShowtimeControl() {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Showtime> getAllShowtimes() {
        ArrayList<Showtime> showtimes = new ArrayList<>();
        try {
            // Use the Database's getListMovies method
            showtimes = Database.getListShowtimes();
            for (Showtime showtime : showtimes) {
                // For each movie, get the showtimes
                System.out.println("Showtime: " + showtime.getShowtime());
            }
        } catch (Exception e) {
            System.err.println("Error fetching showtimes from database: " + e.getMessage());
            e.printStackTrace();
        }
        return showtimes;
    }

    public ArrayList<Showtime> getShowtimesForMovieForTheatre(Movie movie, Theatre theatre) {
        ArrayList<Showtime> showtimes = new ArrayList<>();
        ArrayList<Showtime> showtimesForMovieForTheatre = new ArrayList<>();
        try {
            // Use the Database's getListShowtimes method
            showtimes = Database.getListShowtimes();
            for (Showtime showtime : showtimes) {
                if (showtime.getMovie().getTitle().equals(movie.getTitle())
                        && showtime.getAuditorium().getTheatre().getName().equals(theatre.getName())) {
                    showtimesForMovieForTheatre.add(showtime);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching showtimes for movie for theatre from database: " + e.getMessage());
            e.printStackTrace();
        }
        return showtimesForMovieForTheatre;
    }
    public ArrayList<Showtime> getShowtimesForMovieForTheatreAndDate(Movie movie, Theatre theatre, LocalDate date) {
        ArrayList<Showtime> allShowtimes = new ArrayList<>();
        ArrayList<Showtime> filteredShowtimes = new ArrayList<>();
    
        try {
            allShowtimes = Database.getListShowtimes();
            for (Showtime showtime : allShowtimes) {
                // Check that the movie, theatre, and date match
                if (showtime.getMovie().getTitle().equals(movie.getTitle()) &&
                    showtime.getAuditorium().getTheatre().equals(theatre) &&
                    showtime.getShowtime().toLocalDate().getDayOfMonth() == date.getDayOfMonth()) {
                    filteredShowtimes.add(showtime);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching showtimes for movie, theatre, and date from database: " + e.getMessage());
            e.printStackTrace();
        }
    
        return filteredShowtimes;
    }

    public void addShowtime(String movieTitle, String theatreName, String auditoriumName, String showtime) throws SQLException {
        try {
            String query = "INSERT INTO showtimes (movie_title, theatre_name, auditorium_name, showtime) VALUES (?, ?, ?, ?)";
            int rowsAffected = db.update(query, movieTitle, theatreName, auditoriumName, showtime);
            if (rowsAffected > 0) {
                System.out.println("Showtime added successfully.");
            } else {
                System.out.println("Failed to add showtime.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding showtime: " + e.getMessage());
            throw e;
        }
    }

    public void removeShowtime(String movieTitle, String theatreName, String showtime) throws SQLException {
        try {
            String query = "DELETE FROM showtimes WHERE movie_title = ? AND theatre_name = ? AND showtime = ?";
            int rowsAffected = db.update(query, movieTitle, theatreName, showtime);
            if (rowsAffected > 0) {
                System.out.println("Showtime removed successfully.");
            } else {
                System.out.println("No matching showtime found.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing showtime: " + e.getMessage());
            throw e;
        }
    }

    public void updateShowtime(String movieTitle, String theatreName, String oldShowtime, String newShowtime) throws SQLException {
        try {
            String query = "UPDATE showtimes SET showtime = ? WHERE movie_title = ? AND theatre_name = ? AND showtime = ?";
            int rowsAffected = db.update(query, newShowtime, movieTitle, theatreName, oldShowtime);
            if (rowsAffected > 0) {
                System.out.println("Showtime updated successfully.");
            } else {
                System.out.println("No matching showtime found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating showtime: " + e.getMessage());
            throw e;
        }
    }

}
