package src.Control;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import src.Entity.Ticket;
import src.DB.Database;
import src.Entity.User;

public class TicketControl {

    public static ArrayList<Ticket> getTickets(int userId) {
        return Database.getUserTickets(userId);
    }

    public static boolean cancelTicket(Ticket ticket) {
        String query = "DELETE FROM tickets WHERE ID = ?";
        User user = ticket.getUser();
        String updateQuery = "UPDATE user SET credits = credits + ? WHERE userID = ?";

        LocalDateTime showtime = ticket.getShowtime().getShowtime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, showtime);

        if (duration.toDays() < 3) { // 3 days before showtime
            System.out.println("Cannot cancel ticket less than 3 days before showtime");
            return false;
        }

        try {
            int result = Database.getInstance().delete(query, ticket.getTicketId());
            if (result > 0) {
                System.out.println("Ticket cancelled successfully");
                // Update user credits
                double refund = user.getIsRegisteredUser() ? ticket.getPrice().doubleValue()
                        : ticket.getPrice().doubleValue() * 0.85;
                double newCredits = user.getCredits().doubleValue() + refund;
                Database.getInstance().update(updateQuery, refund, user.getUserID());
                user.setCredit(newCredits);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}