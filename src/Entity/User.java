package src.Entity;

import java.sql.SQLException;
import src.DB.Database;

public class User {
    protected int userID; // gets initialized in the DB
    protected String username;
    protected String email;
    protected Number credits;
    protected boolean IsRegisteredUser;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.credits = 0.0;
        this.IsRegisteredUser = false;
    }

    public User(int userID, String username, String email, Number credits, boolean IsRegisteredUser) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.credits = credits;
        this.IsRegisteredUser = IsRegisteredUser;
    }

    public User(int userID, String username, String email, Number credits) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.credits = credits;
    }

    public void addCredit(Number credits) {
        try {
            this.credits = this.credits.doubleValue() + credits.doubleValue();
            Database db = Database.getInstance();

            String query = "UPDATE user SET credits = ? WHERE userID = ?";
            db.update(query, this.credits, this.userID);
            setCredit(credits);
            db.close();
            System.out.println("Database updated for userID: " + this.userID + " with new credit: " + this.credits);
        } catch (SQLException e) {
            System.err.println("Error updating database for userID: " + this.userID);
            e.printStackTrace();
        }
    }

    public void deductCredit(Number credit) throws SQLException {
        try {
            this.credits = this.credits.doubleValue() - credits.doubleValue();
            Database db = Database.getInstance();
            String query = "UPDATE user SET credits = ? WHERE userID = ?";
            db.update(query, this.credits, this.userID);
            setCredit(credits);
            System.out.println("Database updated for userID: " + this.userID + " with new credit: " + this.credits);
        } catch (SQLException e) {
            System.err.println("Error updating database for userID: " + this.userID);
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public Number getCredits() {
        return credits;
    }

    public boolean getIsRegisteredUser() {
        return IsRegisteredUser;
    }

    public void registerUser() {
        IsRegisteredUser = true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredit(Number credit) {
        this.credits = credit;
    }

    public String toString() {
        return "username: " + username + "\nEmail: " + email + "\nCredit: " + credits;
    }
}
