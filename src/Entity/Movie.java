package src.Entity;

import java.util.*;

public class Movie {
    // for database reasons this movie counter doesn't really work
    // private static int movieCounter = 0;
    private int id;
    private String title;
    private ArrayList<Showtime> showtimes;
    private String rating;
    private String desc;
    private String genre;
    private int duration;

    public Movie(String title, String rating, String desc, String genre, int duration) {
        this.title = title;
        this.rating = rating;
        this.desc = desc;
        this.genre = genre;
        this.duration = duration;
        // See comment about counter
        //this.id = movieCounter++ + 100;
        this.showtimes = new ArrayList<Showtime>();
    }


    public int getId() {
        return id;
    }

    public void setId(int movieId){
        id = movieId;
    }
    
    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Showtime> getShowtimes() {
        return showtimes;
    }

    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    public void removeShowtime(Showtime showtime) {
        showtimes.remove(showtime);
    }

    public void setShowtimes(ArrayList<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}