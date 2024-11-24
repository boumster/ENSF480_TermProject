package src.Control;

import src.DB.Database;
import src.Entity.RegUser;
import java.sql.ResultSet;

public class LoginControl {

    public static RegUser login(String email, String password) {
        String query = "SELECT UserID FROM user WHERE email = ? AND password = ?";
        try {
            ResultSet rs = Database.getInstance().read(query, email, password);
            if (rs.next()) {
                int userId = rs.getInt("UserID");
                RegUser user = Database.getRegUser(userId);
                return user;
            } else {
                System.out.println("No user found with the provided email and password.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}