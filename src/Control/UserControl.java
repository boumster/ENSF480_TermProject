package src.Control;

import java.sql.ResultSet;
import java.util.ArrayList;
import src.DB.Database;
import src.Entity.User;

public class UserControl {
    private Database db;

    public UserControl() {
        try {
            db = Database.getInstance();
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Add a new user to the database
     */
    public boolean addUser(String username, String email) {
        if (username == null || username.isEmpty() || email == null || email.isEmpty() || checkUsername(username)) {
            System.out.println("Invalid username or email");
            return false;
        }

        try {
            String query = "INSERT INTO user (username, email, credits) VALUES (?, ?, ?)";
            int newUserId = db.create(query, username, email, 0); // Default credits = 0
            if (newUserId > 0) {
                System.out.println("User added successfully with ID: " + newUserId);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateUserEmail(User user, String newEmail) {
        if (user == null || newEmail == null || newEmail.isEmpty()) {
            System.out.println("Invalid user or email");
            return false;
        }

        try {
            String query = "UPDATE user SET email = ? WHERE userID = ?";
            int rowsAffected = db.update(query, newEmail, user.getUserID());
            if (rowsAffected > 0) {
                user.setEmail(newEmail);
                System.out.println("User email updated successfully");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteUser(User user) {
        if (user == null) {
            System.out.println("Invalid user");
            return false;
        }

        try {
            String query = "DELETE FROM user WHERE userID = ?";
            int rowsAffected = db.delete(query, user.getUserID());
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int userId) {
        try {
            // Get all users from the database
            ArrayList<User> users = Database.getListUsers();
            
            // Iterate through the list to find the user with the specified ID
            for (User user : users) {
                if (user.getUserID() == userId) {
                    System.out.println("User found: " + user.getUsername());
                    return user; // Return the matching user
                }
            }
    
            // If no user is found, print a message
            System.out.println("No user found with ID: " + userId);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return null; // Return null if no user is found or an exception occurs
    }

    /*
     *  movies = Database.getListMovies();
            for (Movie movie : movies) {
                // For each movie, get the showtimes
                System.out.println("Movie: " + movie.getTitle());
            }
     */

    public Double getCredit(int userId) {
        try {
            String query = "SELECT credits FROM user WHERE userID = ?";
            try (ResultSet rs = db.read(query, userId)) {
                if (rs.next()) {
                    return rs.getDouble("credits");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public boolean addCredit(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Amount to add must be greater than 0.");
            return false;
        }
    
        try {
            // Get current credit balance
            Double currentCredit = getCredit(userId);
            if (currentCredit == null) {
                System.out.println("User not found.");
                return false;
            }
    
            double newCredit = currentCredit + amount;
    
            // Update the credit in the database
            String query = "UPDATE user SET credits = ? WHERE userID = ?";
            int rowsAffected = db.update(query, newCredit, userId);
    
            if (rowsAffected > 0) {
                System.out.println("Credits successfully added. New balance: " + newCredit);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return false;
    }
    
    public boolean deductCredit(int userId, Double amount) {
        if (amount <= 0) {
            System.out.println("Amount to deduct must be greater than 0.");
            return false;
        }
    
        try {
            // Get current credit balance
            Double currentCredit = getCredit(userId);
            if (currentCredit == null) {
                System.out.println("User not found.");
                return false;
            }
    
            if (currentCredit < amount) {
                System.out.println("Insufficient credits. Current balance: " + currentCredit);
                return false;
            }
    
            double newCredit = currentCredit - amount;
    
    
            String query = "UPDATE user SET credits = ? WHERE userID = ?";
            int rowsAffected = db.update(query, newCredit, userId);
    
            if (rowsAffected > 0) {
                System.out.println("Credits successfully deducted. New balance: " + newCredit);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return false;
    }
    
      /*
     *  movies = Database.getListMovies();
            for (Movie movie : movies) {
                // For each movie, get the showtimes
                System.out.println("Movie: " + movie.getTitle());
            }
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
             users = Database.getListUsers();
            for (User user : users) {
                System.out.println("User: " + user.getUsername());
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return users;
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
