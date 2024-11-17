package Entity;

import java.util.*;

public class Auditorium {
    private static int audCount = 0;
    private int audId;
    private int audNum;
    protected int numSeats;
    protected int numSeatsRemaining;    //bookSeat/cancelSeat should update this values
    private ArrayList<Seat> seats;

    public Auditorium(int audNum, int numSeats) {
        this.audId = audCount++ + 200;
        this.audNum = audNum;
        this.numSeats = numSeats;
        this.numSeatsRemaining = numSeats;
        this.seats = new ArrayList<Seat>();
        initSeats();
    }

    public void initSeats(){
        // To do: Implement initSeats Method
        for (int i = 0; i < numSeats; i++) {
            seats.add(new Seat(i + 1));
        }
    }

    public int getAudId() {
        return audId;
    }

    public int getAudNum() {
        return audNum;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public double getPercentOccupied(){
        return (1 - (double)numSeatsRemaining / numSeats) * 100;
    }

    public boolean bookSeat(int seatNumber){
        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Invalid seat number.");
            return false;
        }

        Seat seat = seats.get(seatNumber - 1);
        if (!seat.getStatus()) { 
            seat.bookSeat();    //if not already booked, now book
            numSeatsRemaining--;
            return true;
        } else {
            System.out.println("Seat already booked.");
            return false;
        }
    }

    public boolean cancelSeat(int seatNumber){
        if(seatNumber < 1 || seatNumber > numSeats){
            System.out.println("Invalid seat number.");
            return false;
        }

        Seat seat = seats.get(seatNumber - 1);
        if(seat.getStatus()){
            seat.cancelSeat();  //if already booked, cancel now
            numSeatsRemaining++;
            return true;
        } else {
            return false;
        }
    }
}