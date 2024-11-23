package src.Control;

import src.DB.Database;
import src.Entity.RegUser;

public class LoginControl {
    
    public static RegUser login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
        try {
            RegUser user = Database.getRegUser(query);
            return user;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}