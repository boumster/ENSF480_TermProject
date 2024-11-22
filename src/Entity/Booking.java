package src.Entity;

public class Booking {
    private int bookingID;
    private int showtimeID;
    private int userID;
    private int numTickets;
    private double totalPrice;

    public Booking(int bookingID, int showtimeID, int userID, int numTickets, double totalPrice) {
        this.bookingID = bookingID;
        this.showtimeID = showtimeID;
        this.userID = userID;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getShowtimeID() {
        return showtimeID;
    }

    public int getUserID() {
        return userID;
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

    public void setShowtimeID(int showtimeID) {
        this.showtimeID = showtimeID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String toString() {
        return "Booking ID: " + bookingID + "\nShowtime ID: " + showtimeID + "\nUser ID: " + userID + "\nNumber of Tickets: " + numTickets + "\nTotal Price: " + totalPrice;
    }

    public void updateBooking() {
        // To do: Implement updateBooking Method
    }
}
