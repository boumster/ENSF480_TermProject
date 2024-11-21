package src.Entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import src.DB.Database;

public class User {
    protected int userID;
    protected String name;
    protected String email;
    protected Number credit;
    private Database db;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addCredit(Number credit) {
        this.credit = credit;
    }

    public void deductCredit(Number credit) throws SQLException{
        this.credit = this.credit.doubleValue() - credit.doubleValue();
        String query = "UPDATE user SET credits = ? WHERE userID = ?";
        db.update(query,this.credit,this.id());
        System.out.println("Credits updated: " + this.getCredit());
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