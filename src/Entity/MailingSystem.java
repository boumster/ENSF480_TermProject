package src.Entity;

public interface MailingSystem {
    public void sendReceiptEmail(boolean type, User user, Ticket ticket);
    public void sendNewsletter(User user);
}