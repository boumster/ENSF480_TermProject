package src.Entity;

import java.lang.reflect.Array;
import java.util.*;

public class Auditorium {
    private int audId;
    private Theatre theatre;
    protected int numSeats;

    private Map<Showtime, ArrayList<Seat>> showtimeSeats;

    public Auditorium(int audId, int numSeats, Theatre theatre) {
        this.audId = audId;
        this.numSeats = numSeats;
        this.theatre = theatre;
        showtimeSeats = new HashMap<>();
    }

    public int getAudId() {
        return audId;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public double getPercentOccupied(Showtime showtime) {
        ArrayList<Seat> seats = showtimeSeats.get(showtime);
        int numSeatsRemaining = 0;
        for (Seat seat : seats) {
            if (!seat.getStatus()) {
                numSeatsRemaining++;
            }
        }
        return (1 - (double) numSeatsRemaining / numSeats) * 100;
    }

    public void bookSeat(Showtime showtime, int seatNumber) {
        ArrayList<Seat> seats = showtimeSeats.get(showtime);
        if (seats != null) {
            seats.get(seatNumber - 1).bookSeat(); // Assuming seat numbers start from 1
        }
    }

    public boolean cancelSeat(int seatNumber, Showtime showtime) {
        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Invalid seat number.");
            return false;
        }
        ArrayList<Seat> seats = showtimeSeats.get(showtime);

        Seat seat = seats.get(seatNumber - 1);
        if (seat.getStatus()) {
            seat.cancelSeat(); // if already booked, cancel now
            return true;
        } else {
            return false;
        }
    }

    public boolean isSeatBooked(Showtime showtime, int seatNumber) {
        ArrayList<Seat> seats = showtimeSeats.get(showtime);
        if (seats != null) {
            return seats.get(seatNumber - 1).getStatus(); // Assuming seat numbers start from 1
        }
        return false;
    }


    public void addShowtime(Showtime showtime) {
        ArrayList<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= numSeats; i++) {
            seats.add(new Seat(i)); // Assuming Seat constructor takes seat number and status
        }
        showtimeSeats.put(showtime, seats);
    }


    public ArrayList<Seat> getSeats(Showtime showtime) {
        return showtimeSeats.get(showtime);
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

}