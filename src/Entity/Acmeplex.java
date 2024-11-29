package src.Entity;

import java.util.*;

public class Acmeplex implements MailingSystem, BillingSystem{
    private ArrayList<Theatre> theatres;
    @Override
    public void sendReceiptEmail(boolean type, User user, Ticket ticket) {
       
    }
    @Override
    public void sendNewsletter(User user) {
    }

    @Override
    public boolean makePayment(User user, double amount) {
        System.out.println("Processing payment of $" + amount + " for " + user.getUsername());
        return true; 
    }

    @Override
    public void refundPayment(User user) {
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