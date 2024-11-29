package src.Control;

import src.DB.Database;
import src.Entity.Movie;
import java.sql.SQLException;
import java.util.ArrayList;

public class movieController {
    private Database db;

    public movieController() {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Fetch all movies from the database
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            // Use the Database's getListMovies method
            movies = Database.getListMovies();
        } catch (Exception e) {
            System.err.println("Error fetching movies from database: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }
}
