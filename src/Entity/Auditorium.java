package src.Entity;

import java.util.*;

public class Auditorium {
    private static int audCount = 0;
    private int audId;
    private int theatreId;
    protected int numSeats;
    protected int numSeatsRemaining; // bookSeat/cancelSeat should update this values
    private ArrayList<Seat> seats;

    public Auditorium(int audId, int numSeats, int theatreId) {
        this.audId = audId;
        this.numSeats = numSeats;
        this.numSeatsRemaining = numSeats;
        this.theatreId = theatreId;
        this.seats = new ArrayList<Seat>();
    }

    public void initSeats() {
        // To do: Implement initSeats Method
        for (int i = 0; i < numSeats; i++) {
            seats.add(new Seat(i + 1));
        }
    }

    public int getAudId() {
        return audId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public double getPercentOccupied() {
        return (1 - (double) numSeatsRemaining / numSeats) * 100;
    }

    public boolean bookSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Invalid seat number.");
            return false;
        }

        Seat seat = seats.get(seatNumber - 1);
        if (!seat.getStatus()) {
            seat.bookSeat(); // if not already booked, now book
            numSeatsRemaining--;
            return true;
        } else {
            System.out.println("Seat already booked.");
            return false;
        }
    }

    public boolean cancelSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Invalid seat number.");
            return false;
        }

        Seat seat = seats.get(seatNumber - 1);
        if (seat.getStatus()) {
            seat.cancelSeat(); // if already booked, cancel now
            numSeatsRemaining++;
            return true;
        } else {
            return false;
        }
    }
}