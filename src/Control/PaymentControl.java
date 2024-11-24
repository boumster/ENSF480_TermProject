package src.Control;

import src.DB.Database;
import src.Entity.RegUser;

public class PaymentControl {
    public RegUser payAnnualFee(RegUser user, double fee, int paymentCard) {
        String query = "UPDATE user SET PaymentInfo = ?, IsRegisteredUser = 1 WHERE UserID = ?";
        try {
            int userID = user.getUserID();
            int result = Database.getInstance().update(query, paymentCard, userID);
            if (result > 0) {
                System.out.println("User credits updated successfully.");
                user.setPaymentCard(paymentCard);
                user.registerUser();
                return user;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
