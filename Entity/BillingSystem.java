package Entity;

public interface BillingSystem {
    public boolean makePayment(User user, double amount);
    public void refundPayment(User user);
}