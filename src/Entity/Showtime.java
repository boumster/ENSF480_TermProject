package src.Entity;

import java.util.Date;
import java.time.LocalDateTime;

public class Showtime {
    private LocalDateTime showtime;
    private Auditorium auditorium;
    private Movie movie;

    public Showtime(LocalDateTime showtime, Auditorium auditorium, Movie movie) {
        this.showtime = showtime;
        this.auditorium = auditorium;
        this.movie = movie;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }

    public String toString() {
        return "Showtime: " + showtime + "\nAuditorium: " + auditorium + "\nMovie: " + movie;
    }
}