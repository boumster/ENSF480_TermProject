public class Seat {
    private int seatNumber;
    private boolean isBooked;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public boolean getStatus() {
        return isBooked;
    }

    public void bookSeat() {
        isBooked = true;
    }

    public void cancelSeat() {
        isBooked = false;
    }
}