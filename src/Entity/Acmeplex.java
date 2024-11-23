package src.Entity;

import java.util.*;

public class Acmeplex implements MailingSystem, BillingSystem{
    private ArrayList<Theatre> theatres;
    // These override func likely also need to update the db, if payment is recieved, the seat
    // needs to be marked as booked, if canceled it should be freed up again and so on.
    @Override
    public void sendBookingEmail(boolean type, User user, Booking booking) {
       String subject = type ? "Booking Confirmation" : "Cancellation Confirmation";

       String message = type 
               ? "Your booking for " + (booking.getmovie()).getTitle() + " at " + (booking.getShowtime()).getShowtime() + " is confirmed."
               : "Your booking for " + (booking.getmovie()).getTitle() + " at " + (booking.getShowtime()).getShowtime()  + " has been canceled.";
        System.out.println("Email sent to " + user.getEmail() + ": " + subject + "\n" + message); 
       
    }
    @Override
    public void sendNewsletter(User user) {
        System.out.println("Newsletter sent to " + user.getEmail());
    }

    @Override
    public boolean makePayment(User user, double amount) {
        System.out.println("Processing payment of $" + amount + " for " + user.getUsername());
        return true; // Need to add some way to see if payment goes through
    }

    @Override
    public void refundPayment(User user) {
        // need a way to check if the user is registered or not, also need pricing info (is every seat the same price?)
        //double refundAmount = isRegisteredUser ? amount : amount * 0.85; // Apply 15% admin fee if not a registered user
        //System.out.println("Refunding $" + refundAmount + " to " + user.getName());
    }

    public Acmeplex(List<Theatre> theatres) {
        this.theatres = new ArrayList<>(theatres);
    }

    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }

    public void removeTheatre(Theatre theatre) {
        theatres.remove(theatre);
    }

    public ArrayList<Theatre> getTheatres() {
        return theatres;
    }

    public Theatre getTheatre(String theatreName) {
        for (Theatre theatre : theatres) {
            if (theatre.getName().equals(theatreName)) {
                return theatre;
            }
        }
        return null;
    }

}