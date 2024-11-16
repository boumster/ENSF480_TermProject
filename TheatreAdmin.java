public class TheatreAdmin {
    
    private Database db;
    
    public TheatreAdmin(Database db){
        this.db = db;
    }

    public void addShowtime(Theatre theatre, Movie movie, Showtime showTime){
        theatre.addShowtime(movie, showTime);
        // Maybe we should do by theatre? idk, also movie id is the title or what
        db.executeQuery("INSERT INTO showtimes (movieId, time) VALUES ('" + movie.getTitle() + "', '" + showTime.getShowtime() + "')");
    }
    public void removeShowtime(Theatre theatre, Movie movie, Showtime showTime) {
        theatre.removeShowtime(movie, showTime);
        // I think it needs to be for the specific theatre, not every theatre will want to remove the same showtimes
        db.executeQuery("DELETE FROM showtimes WHERE theatreId='" 
                + theatre.getName() + "' AND movieId='" + movie.getTitle() 
                + "' AND time='" + showTime.getShowtime() + "'");
    }

    public void updateShowtime(Theatre theatre, Movie movie, Showtime newShowTime, Showtime oldShowtime) {
        theatre.updateShowtime(movie, oldShowtime, newShowTime);
        db.executeQuery("UPDATE showtimes SET time='" 
                + newShowTime.getShowtime() + "' WHERE theatreId='" 
                + theatre.getName() + "' AND movieId='" + movie.getTitle() 
                + "' AND time='" + oldShowtime.getShowtime() + "'");
    }

    public void addMovie(Theatre theatre, Movie movie) {
        theatre.addMovie(movie);
        // If this is how we want to do it, maybe movies should have id keys, easy fix
        db.executeQuery("INSERT INTO Movies (theatreId, movieId, title, duration) VALUES ('" 
                + theatre.getName() + "', '" + movie.getTitle() + "', '" 
                + movie.getTitle() + "', '" + movie.getDuration() + "')");
    }

    public void removeMovie(Theatre theatre, Movie movie) {
        theatre.removeMovie(movie);
        db.executeQuery("DELETE FROM Movies WHERE theatreId='" 
                + theatre.getName() + "' AND movieId='" + movie.getTitle() + "'");
    }

    public void updateMovie(Theatre theatre, Movie movie) {
        db.executeQuery("UPDATE Movies SET title='" + movie.getTitle() 
                + "', duration='" + movie.getDuration() + "' WHERE theatreId='" 
                + theatre.getName() + "' AND movieId='" + movie.getTitle() + "'");
    }
}