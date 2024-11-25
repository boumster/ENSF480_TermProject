package src.Control;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import src.DB.Database;
import src.Entity.Movie;
import src.Entity.User;

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
    
    
    
}

