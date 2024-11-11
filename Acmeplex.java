import java.lang.reflect.Array;
import java.util.*;

public class Acmeplex {
    private ArrayList<Theatre> theatres;
    private BillingSystem paymenSystem;
    private MailingSystem mailingSystem;

    public Acmeplex(List<Theatre> theatres) {
        this.theatres = new ArrayList<>(theatres);
        this.paymenSystem = new BillingSystem();
        this.mailingSystem = new MailingSystem();
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

    public void setPaymenSystem(BillingSystem paymenSystem) {
        this.paymenSystem = paymenSystem;
    }

    public void setMailingSystem(MailingSystem mailingSystem) {
        this.mailingSystem = mailingSystem;
    }

}