import java.util.Date;

public class Showtime {
    private DateTime showtime;
    private Auditorium auditorium;
    private Movie movie;

    public Showtime(DateTime showtime, Auditorium auditorium, Movie movie) {
        this.showtime = showtime;
        this.auditorium = auditorium;
        this.movie = movie;
    }

    public DateTime getShowtime() {
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

    public void setShowtime(DateTime showtime) {
        this.showtime = showtime;
    }

    public String toString() {
        return "Showtime: " + showtime + "\nAuditorium: " + auditorium + "\nMovie: " + movie;
    }
}