import java.util.*;

public class Auditorium {
    private int audNum;
    private int numSeats;
    private int numSeatsRemaining;
    private ArrayList<Seat> seats;

    public Auditorium(int audNum, int numSeats) {
        this.audNum = audNum;
        this.numSeats = numSeats;
        this.numSeatsRemaining = numSeats;
        this.seats = new ArrayList<Seat>();
    }

    public initSeats() {
        // To do: Implement initSeats Method
        for (int i = 0; i < numSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    public int getAudNum() {
        return audNum;
    }

    public int getNumSeats() {
        return numSeats;
    }
}