package src.Entity;

import java.lang.reflect.Array;
import java.util.*;

public class Theatre {
    private static int counter = 0;
    private final String name;
    private ArrayList<Auditorium> auditoriums;
    private ArrayList<Movie> movies;
    private HashMap<Movie, ArrayList<Showtime>> showtimes;
    private int id;

    public Theatre(String name, ArrayList<Auditorium> auditoriums) {
        this.name = name;
        this.auditoriums = auditoriums;
        this.movies = new ArrayList<Movie>();
        this.id = counter++ + 1000;
    }

    public Theatre(String name, int id) {
        this.name = name;
        this.auditoriums = new ArrayList<Auditorium>();
        this.movies = new ArrayList<Movie>();
        this.showtimes = new HashMap<Movie, ArrayList<Showtime>>();
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void addAuditorium(Auditorium auditorium) {
        auditoriums.add(auditorium);
    }

    public void removeAuditorium(Auditorium auditorium) {
        auditoriums.remove(auditorium);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        movie.addShowtime(this, new ArrayList<Showtime>()); //* */
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
        movie.removeShowtime(this, movie.getShowtimesForTheatre(this));
    }

/*
    public void addShowtime(Movie movie, Showtime showtime) {
        if (!movie.showtimesByTheatre.containsKey(this)) {
            showtimes.put(movie, new ArrayList<Showtime>());
        }
        showtimes.get(movie).add(showtime);
    }

    public void removeShowtime(Movie movie, Showtime showtime) {
        showtimes.get(movie).remove(showtime);
    }

    public void setShowtimes(Movie movie, ArrayList<Showtime> showtimes) {
        this.showtimes.put(movie, showtimes);
    }

    public ArrayList<Showtime> getShowtimes(Movie movie) {
        return showtimes.get(movie);
    }

    public void updateShowtime(Movie movie, Showtime oldshowtime, Showtime newShowtime){
        this.removeShowtime(movie, oldshowtime);
        this.addShowtime(movie, newShowtime);
    }
*/
}