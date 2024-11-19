package src.Entity;

public class RegUser extends User {
    private String address;
    private int paymentCard;

    public RegUser(String name, String email, String address, int paymentCard) {
        super(name, email);
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

    public void updateInfo(){
        // To do: Implement updateInfo Method
    }
}