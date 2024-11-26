package src.Control;

import java.sql.ResultSet;
import src.DB.Database;
import src.Entity.RegUser;

public class LoginControl {

    public static RegUser login(String email, String password, boolean isAdminLogin) {
        String query = "SELECT UserID, IsAdmin FROM user WHERE username = ? AND password = ?";
        
        try {
            // Fetch user data from the database
            ResultSet rs = Database.getInstance().read(query, email, password);

            if (rs.next()) {
                int userId = rs.getInt("UserID");
                int isAdmin = rs.getInt("IsAdmin");

                // Check if it's an admin login attempt
                if (isAdminLogin && isAdmin == 0) {
                    // Admin login failed if the user is a normal user
                    System.out.println("Admin login failed: User is not an admin.");
                    return null;
                }

                // Check if it's a normal login attempt
                if (!isAdminLogin && isAdmin == 1) {
                    // Normal login failed if the user is an admin
                    System.out.println("Normal user login failed: Admin user cannot log in here.");
                    return null;
                }

                // Return the user if login is successful
                return Database.getRegUser(userId);
            } else {
                System.out.println("No user found with the provided email and password.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}