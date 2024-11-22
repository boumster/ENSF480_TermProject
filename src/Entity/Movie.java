package src.Entity;

import java.util.*;

public class Movie {
    // for database reasons this movie counter doesn't really work
    // private static int movieCounter = 0;
    private int id;
    private String title;
    private ArrayList<Showtime> showtimes;
    private static int movieCounter = 0;
    private Map<Theatre, ArrayList<Showtime>> showtimesByTheatre;
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
        this.id = movieCounter++ + 100;
        this.showtimesByTheatre = new HashMap<>();
    }

    public Movie(int id, String title, String desc, int duration) {
        this.title = title;
        this.desc = desc;
        this.duration = duration;
        this.id = id;
        this.showtimes = new ArrayList<Showtime>();
        this.showtimesByTheatre = new HashMap<>();
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

    public Map<Theatre, ArrayList<Showtime>> getShowtimesByTheatre() {
        return showtimesByTheatre;
    }

    public ArrayList<Showtime> getShowtimesForTheatre(Theatre theatre) {
        Map<Theatre, ArrayList<Showtime>> showtimesMap = getShowtimesByTheatre();
        ArrayList<Showtime> showtimes = showtimesMap.get(theatre);
        if (showtimes == null) {
            // Optionally handle missing showtimes for the given theatre
            System.out.println("No showtimes found for theatre: " + theatre.getName());
        }
        return showtimes;
    }
    

    public void addShowtime(Theatre theatre, ArrayList<Showtime> showtime) {
        if (showtime == null) {
            // Optionally handle null showtimes if needed
            System.out.println("Cannot add null showtime for theatre: " + theatre.getName());
            return;
        }
        showtimesByTheatre.put(theatre, showtime);
    }

    public void removeShowtime(Theatre theatre, ArrayList<Showtime> showtime) {
        showtimesByTheatre.remove(theatre, showtime);
    }
}