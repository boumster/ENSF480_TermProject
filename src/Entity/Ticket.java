package src.Entity;

public class Ticket {
    private int ticketId;
    private Showtime showtime;
    private Number price;
    private Number seatNumber;
    private User user;

    public Ticket(int ticketId, Showtime showtime, Number price, Number seatNumber, User user) {
        this.ticketId = ticketId;
        this.showtime = showtime;
        this.price = price;
        this.seatNumber = seatNumber;
        this.user = user;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Number getPrice() {
        return price;
    }

    public Number getSeatNumber() {
        return seatNumber;
    }

    public User getUser() {
        return user;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public void setSeatNumber(Number seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "Ticket ID: " + ticketId + "\nShowtime: " + showtime + "\nPrice: " + price + "\nSeat Number: " + seatNumber + "\nUser: " + user;
    }
}
