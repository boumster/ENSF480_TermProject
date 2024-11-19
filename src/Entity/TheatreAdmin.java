package src.Entity;

import java.sql.SQLException;

import src.DB.Database;

public class TheatreAdmin {

    private Database db;

    public TheatreAdmin(Database db) {
        this.db = db;
    }

    public void addShowtime(Theatre theatre, Movie movie, Showtime showTime) throws SQLException {
        theatre.addShowtime(movie, showTime);

        String query = "INSERT INTO showtimes (theatreId, movieId, time) VALUES (?, ?, ?)";
        db.update(query, theatre.getName(), movie.getTitle(), showTime.getShowtime());
    }

    public void removeShowtime(Theatre theatre, Movie movie, Showtime showTime) throws SQLException {
        theatre.removeShowtime(movie, showTime);

        String query = "DELETE FROM showtimes WHERE theatreId = ? AND movieId = ? AND time = ?";
        db.update(query, theatre.getName(), movie.getTitle(), showTime.getShowtime());
    }

    public void updateShowtime(Theatre theatre, Movie movie, Showtime newShowTime, Showtime oldShowTime) throws SQLException {
        theatre.updateShowtime(movie, oldShowTime, newShowTime);

        String query = "UPDATE showtimes SET time = ? WHERE theatreId = ? AND movieId = ? AND time = ?";
        db.update(query, newShowTime.getShowtime(), theatre.getName(), movie.getTitle(), oldShowTime.getShowtime());
    }

    public void addMovie(Theatre theatre, Movie movie) throws SQLException {
        theatre.addMovie(movie);

        String query = "INSERT INTO movies (theatreId, movieId, title, duration) VALUES (?, ?, ?, ?)";
        db.update(query, theatre.getName(), movie.getTitle(), movie.getTitle(), movie.getDuration());
    }

    public void removeMovie(Theatre theatre, Movie movie) throws SQLException {
        theatre.removeMovie(movie);

        String query = "DELETE FROM movies WHERE theatreId = ? AND movieId = ?";
        db.update(query, theatre.getName(), movie.getTitle());
    }

    public void updateMovie(Theatre theatre, Movie movie) throws SQLException {
        String query = "UPDATE movies SET title = ?, duration = ? WHERE theatreId = ? AND movieId = ?";
        db.update(query, movie.getTitle(), movie.getDuration(), theatre.getName(), movie.getTitle());
    }
}
