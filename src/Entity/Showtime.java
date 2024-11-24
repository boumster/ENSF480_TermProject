package src.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;

public class Showtime {
    private int showtimeId;
    private LocalDateTime showtime;
    private Auditorium auditorium;
    private Movie movie;

    public Showtime(int id, LocalDateTime showtime, Auditorium auditorium, Movie movie) {
        this.showtimeId = id;
        this.showtime = showtime;
        this.auditorium = auditorium;
        this.movie = movie;
        this.auditorium.addShowtime(this);
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public int getShowtimeId() {
        return showtimeId;
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

    public void bookSeat(int seatNumber) {
        auditorium.bookSeat(this, seatNumber);
    }

    public ArrayList<Seat> getSeats() {
        return auditorium.getSeats(this);
    }

    public String toString() {
        return "Showtime: " + showtime + "\nAuditorium: " + auditorium + "\nMovie: " + movie;
    }
}