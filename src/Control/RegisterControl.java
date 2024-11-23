package src.Control;

import src.DB.Database;
import src.Entity.RegUser;

public class RegisterControl {

    public static boolean register(RegUser user, String password) {
        String query = "INSERT INTO user (Username, Email, Password, credits, Tickets, Address, PaymentInfo, IsRegisteredUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        if (checkUsername(user.getUsername())) {
            return false;
        }
        try {
            int result = Database.getInstance().create(query, user.getUsername(), user.getEmail(), password, 0.00, 0, user.getAddress(), user.getPaymentCard(), 1);
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
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