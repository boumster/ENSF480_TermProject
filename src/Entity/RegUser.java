package src.Entity;

public class RegUser extends User {
    private String address;
    private int paymentCard;

    public RegUser(int userID, String name, String email, String address, int paymentCard, Number numCredits,
            boolean IsRegisteredUser) {
        super(userID, name, email, numCredits, IsRegisteredUser);
        this.address = address;
        this.paymentCard = paymentCard;
    }

    public RegUser(int userID, String name, String email, Number credits, String address, int paymentCard) {
        super(userID, name, email, credits);
        this.address = address;
        this.paymentCard = paymentCard;
    }

    public String getAddress() {
        return address;
    }

    public int getPaymentCard() {
        return paymentCard;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPaymentCard(int paymentCard) {
        this.paymentCard = paymentCard;
    }

    public String toString() {
        return super.toString() + "\nAddress: " + address + "\nPayment Card: " + paymentCard;
    }
}