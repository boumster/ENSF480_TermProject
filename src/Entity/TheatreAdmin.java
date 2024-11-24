package src.Entity;

import java.sql.SQLException;
import java.time.LocalDateTime;
import src.DB.Database;

public class TheatreAdmin {

    private Database db;

    public TheatreAdmin(Database db) {
        this.db = db;
    }

    public void addShowtime(Movie movie, Showtime showTime) throws SQLException {
        // SQL query for inserting a showtime
        String query = "INSERT INTO showtimes (times,movieID) VALUES (?, ?)";
        
        // Insert the new showtime linked to the movie
        db.create(query, showTime.getShowtime().toString(), movie.getId() );
        
        System.out.println("Showtime added for movie ID: " + movie.getId());
    }
    

    public void removeShowtime(Movie movie, Showtime showTime) throws SQLException {
        String query = "DELETE FROM showtimes WHERE movieID = ? AND times = ?";
        
        db.delete(query, movie.getId(), showTime.getShowtime().toString());
        
        System.out.println("Showtime removed for movie ID: " + movie.getId());
    }
    

    public void updateShowtime(Movie movie, Showtime newShowTime, Showtime oldShowTime) throws SQLException {
        String query = "UPDATE showtimes SET times = ? WHERE movieID = ? AND times = ?";
        
        db.update(query, newShowTime.getShowtime().toString(), movie.getId(), oldShowTime.getShowtime());
        
        System.out.println("Showtime updated for movie ID: " + movie.getId());
    }
    

    public void addMovie(Movie movie) throws SQLException {
        // SQL query for inserting a movie
        String query = "INSERT INTO movies (Movie_name, Movie_description, Movie_Genre) VALUES (?, ?, ?)";
        
        // Use the create method to insert the movie and fetch the generated ID
        int movieID = db.create(query, movie.getTitle(), movie.getDesc(), movie.getGenre());
        
        movie.setId(movieID);
        
        System.out.println("Movie added with ID: " + movieID);
    }
    

    public void removeMovie(Movie movie) throws SQLException {
        String query = "DELETE FROM movies WHERE movieID = ?";
        
        db.delete(query, movie.getId());
        
        System.out.println("Movie removed with ID: " + movie.getId());
    }
    

    public void updateMovie(Movie movie) throws SQLException {
        // SQL query for updating movie details
        String query = "UPDATE movies SET Movie_name = ?, Movie_description = ?, Movie_Genre = ? WHERE movieID = ?";
        
        // Execute the update statement
        db.update(query, movie.getTitle(), movie.getDesc(), movie.getGenre(), movie.getId());
        
        System.out.println("Movie updated with ID: " + movie.getId());
    }
    

    // Example usage
    /* public static void main(String[] args) {
        try {
            // Initialize the database
            Database db =  Database.getInstance();
            
            // Create a movie
            Movie movie = new Movie("Interstellar","R", "A team of explorers travel through a wormhole in space.", "Sci-Fi", 123);
            
            // Create a TheatreAdmin
            TheatreAdmin admin = new TheatreAdmin(db);
            
            // Add the movie
            admin.addMovie(movie);

            //Auditorium one = new Auditorium(1,20,0);
            // Create a showtime
            LocalDateTime now = LocalDateTime.of(20,11,2024, 5, 34);
            //Showtime showtime = new Showtime(now, one, movie);
            
            // Add the showtime for the movie
            //admin.addShowtime(movie, showtime);
    
            // Close the database
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
    
}
