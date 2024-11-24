package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Control.PaymentControl;

public class PaymentPage extends JPanel {
    private JTextField cardNumberField;
    private JButton payButton;
    private JLabel statusLabel;

    private PaymentControl paymentControl;

    public PaymentPage(MovieTheatreApp app, String type) {
        paymentControl = new PaymentControl();
        if (type == "FEE") {
            renderAnnualFeePayment(app);
        } else {
            // Render other payment types here
        }
    }

    private boolean processPayment(Number cardNumber) {
        // Simulate payment processing logic
        // In a real application, you would integrate with a payment gateway here
        return true; // Simulate a successful payment
    }

    private void renderAnnualFeePayment(MovieTheatreApp app) {
        // Render the annual fee payment form
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Pay Annual Fee");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        // Card Number label and field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(cardNumberLabel);

        cardNumberField = new JTextField(20);
        cardNumberField.setMaximumSize(cardNumberField.getPreferredSize());
        cardNumberField.setAlignmentX(CENTER_ALIGNMENT);
        add(cardNumberField);

        add(Box.createVerticalStrut(20)); // Add space

        // Pay button
        payButton = new JButton("Pay $20");
        payButton.setAlignmentX(CENTER_ALIGNMENT);
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle payment logic here
                int cardNumber = Integer.parseInt(cardNumberField.getText());

                if (cardNumberField.getText().isEmpty()) {
                    statusLabel.setText("Please fill in all fields.");
                } else {
                    // Simulate payment processing
                    boolean paymentSuccess = processPayment(cardNumber);
                    if (paymentSuccess) {
                        statusLabel.setText("Payment successful! Thank you.");
                        paymentControl.payAnnualFee(app.getCurrentUser(), 20.0, cardNumber);
                        app.switchToPage("Home");
                    } else {
                        statusLabel.setText("Payment failed. Please try again.");
                    }
                }
            }
        });
        add(payButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Status label
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(statusLabel);
    }
}