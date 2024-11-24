package src.Control;

import src.DB.Database;
import src.Entity.RegUser;

public class RegisterControl {

    public static RegUser register(String username, String password, String email, String address, int paymentCard) {
        String query = "INSERT INTO user (Username, Email, Password, credits, Tickets, Address, PaymentInfo, IsRegisteredUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        if (checkUsername(username)) {
            return null;
        }
        try {
            int result = Database.getInstance().create(query, username, email, password, 0.00, 0, address, paymentCard, 1);
            if (result > 0) {
                System.out.println("User added successfully with ID: " + result);
                query = "SELECT * FROM user WHERE Username = '" + username + "'";
                RegUser newUser = Database.getRegUser(query);
                return newUser;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null; // Ensure the method always returns a value
    }

    private static boolean checkUsername(String username) {
        String query = "SELECT * FROM user WHERE Username = ?";
        try {
            return Database.getInstance().read(query, username).next();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}