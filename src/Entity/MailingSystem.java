package src.Entity;

public interface MailingSystem {
    public void sendBookingEmail(boolean type, User user, Booking booking);
    public void sendSMS(User user, String message);
    public void sendNewsletter(User user);
}