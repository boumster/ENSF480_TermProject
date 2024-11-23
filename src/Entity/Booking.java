package src.Entity;

public class Booking {
    private int bookingID;
    private int numTickets;
    private double totalPrice;
    private Showtime showtime;
    private User user;
    private Movie movie;

    public Booking(int bookingID, Showtime showtime, User user, Movie movie, int numTickets, double totalPrice) {
        this.bookingID = bookingID;
        this.showtime = showtime;
        this.user = user;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
    }

    public int getBookingID() {
        return bookingID;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public User getUser() {
        return user;
    }

    public Movie getmovie() {
        return movie;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public void setUser(RegUser user) {
        this.user = user;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String toString() {
        return "Booking ID: " + bookingID + "\nUser ID: " + user.getusername() + "\nNumber of Tickets: " + numTickets + "\nTotal Price: " + totalPrice;
    }

    public void updateBooking() {
        // To do: Implement updateBooking Method
    }
}
