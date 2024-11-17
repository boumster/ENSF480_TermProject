package Entity;

public class TheatreAdmin {
    public void addShowtime(Theatre theatre, Movie movie, Showtime showTime){
        theatre.addShowtime(movie, showTime);
    }
    public void removeShowtime(Theatre theatre, Movie movie, Showtime showTime){
        theatre.removeShowtime(movie, showTime);
    }
    public void updateShowtime(Theatre theatre, Movie movie, Showtime newShowTime, Showtime oldShowtime){
        theatre.updateShowtime(movie, oldShowtime, newShowTime);
    }
    public void addMovie(Theatre theatre, Movie movie){
        theatre.addMovie(movie);
    }
    public void removeMovie(Theatre theatre, Movie movie){
        theatre.removeMovie(movie);
    }
    public void updateMovie(Theatre theatre, Movie movie){

    }
}