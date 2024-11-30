package src.Entity;
import java.time.LocalDateTime;

public class Mail {

    private int mailID;        
    private User user;     
    private Ticket ticket;   
    private String message;     
    private LocalDateTime time; 

    public Mail(int mailID, User user, Ticket ticket, String message, LocalDateTime time) {
        this.mailID = mailID;
        this.user = user;
        this.ticket = ticket;
        this.message = message;
        this.time = time;
    }

    public int getMailID() {
        return mailID;
    }

    public void setEmailID(int mailID) {
        this.mailID = mailID;
    }

    public User getUser() {
        return user;
    }


    public Ticket getTicket() {
        return ticket;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
