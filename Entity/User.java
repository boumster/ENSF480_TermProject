package Entity;

public class User {
    protected String name;
    protected String email;
    protected Number credit;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addCredit(Number credit) {
        this.credit = credit;
    }

    public void deductCredit(Number credit) {
        this.credit = this.credit.doubleValue() - credit.doubleValue();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Number getCredit() {
        return credit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredit(Number credit) {
        this.credit = credit;
    }

    public String toString() {
        return "Name: " + name + "\nEmail: " + email + "\nCredit: " + credit;
    }

    // To do: Implement Booking Method
}