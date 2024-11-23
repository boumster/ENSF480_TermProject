package src.Entity;

import java.sql.SQLException;
import src.DB.Database;

public class User {
    protected int userID; //gets initialized in the DB
    protected String username;
    protected String email;
    protected Number credits;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.credits = 0.0;
    }

    public void addCredit(Number credits) {
        try{
            this.credits = this.credits.doubleValue() + credits.doubleValue();
            Database db = Database.getInstance();

            String query = "UPDATE user SET credits = ? WHERE userID = ?";
            db.update(query, this.credits,this.userID);
            db.close();
            System.out.println("Database updated for userID: " + this.userID + " with new credit: " + this.credits);
        }catch (SQLException e) {
        System.err.println("Error updating database for userID: " + this.userID);
        e.printStackTrace();
        }
    }

    public void deductCredit(Number credit) throws SQLException{
        try{
            this.credits = this.credits.doubleValue() - credits.doubleValue();
            Database db = Database.getInstance();
            String query = "UPDATE user SET credits = ? WHERE userID = ?";
            db.update(query, this.credits,this.userID);
            System.out.println("Database updated for userID: " + this.userID + " with new credit: " + this.credits);
    }catch (SQLException e) {
        System.err.println("Error updating database for userID: " + this.userID);
        e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Number getCredit() {
        return credits;
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

    // To do: Implement Booking Method
    public static void main(String[] args) {
        try {
            // Connect to the database
            Database db =  Database.getInstance();

            // Create a User object
            User john = new User("John Doe", "john.doe@example.com");
            john.setCredit(100.00); // Initialize credit
            System.out.println("User created: " + john);

            // Test adding a new user to the database
            String addUserQuery = "INSERT INTO user (Username, Email, Password, credits) VALUES (?, ?, ?, ?)";
            db.update(addUserQuery, john.getUsername(), john.getEmail(), "securepassword", john.getCredit());
            System.out.println("User added to the database.");

            // Deduct some credits
            System.out.println("Initial credit: " + john.getCredit());
            john.deductCredit(20.50); // Deduct 20.50 credits
            System.out.println("After deduction: " + john.getCredit());

            // Add some credits
            john.addCredit(50.00);
            System.out.println("After addition: " + john.getCredit());

            // Update the database with the new credit value
            String updateUserQuery = "UPDATE user SET credits = ? WHERE Email = ?";
            db.update(updateUserQuery, john.getCredit(), john.getEmail());
            System.out.println("Database updated with new credit value: " + john.getCredit());
            db.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
