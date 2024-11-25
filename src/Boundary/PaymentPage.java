package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import src.Entity.Ticket;

import src.Control.PaymentControl;

public class PaymentPage extends JPanel {
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private JButton payButton;
    private JLabel statusLabel;
    private PaymentControl paymentControl;
    private JPanel ticketsPanel;
    private ArrayList<String> seatsSelected;

    private MovieTheatreApp app;

    public PaymentPage(MovieTheatreApp app) {
        this.app = app;
        paymentControl = new PaymentControl();
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

    public PaymentPage(MovieTheatreApp app, ArrayList<String> seatsSelected) {
        this.seatsSelected = seatsSelected;
        paymentControl = new PaymentControl();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Ticket Payment");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        // Tickets panel
        ticketsPanel = new JPanel();
        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
        add(ticketsPanel);

        refreshTickets();

        add(Box.createVerticalStrut(20)); // Add space

        // Card Number label and field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(cardNumberLabel);

        cardNumberField = new JTextField(20);
        cardNumberField.setMaximumSize(cardNumberField.getPreferredSize());
        cardNumberField.setAlignmentX(CENTER_ALIGNMENT);
        add(cardNumberField);

        add(Box.createVerticalStrut(10)); // Add space

        // Card Holder label and field
        JLabel cardHolderLabel = new JLabel("Card Holder Name:");
        cardHolderLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(cardHolderLabel);

        cardHolderField = new JTextField(20);
        cardHolderField.setMaximumSize(cardHolderField.getPreferredSize());
        cardHolderField.setAlignmentX(CENTER_ALIGNMENT);
        add(cardHolderField);

        add(Box.createVerticalStrut(10)); // Add space

        // Expiry Date label and field
        JLabel expiryDateLabel = new JLabel("Expiry Date (MM/YY):");
        expiryDateLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(expiryDateLabel);

        expiryDateField = new JTextField(5);
        expiryDateField.setMaximumSize(expiryDateField.getPreferredSize());
        expiryDateField.setAlignmentX(CENTER_ALIGNMENT);
        add(expiryDateField);

        add(Box.createVerticalStrut(10)); // Add space

        // CVV label and field
        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(cvvLabel);

        cvvField = new JTextField(3);
        cvvField.setMaximumSize(cvvField.getPreferredSize());
        cvvField.setAlignmentX(CENTER_ALIGNMENT);
        add(cvvField);

        add(Box.createVerticalStrut(20)); // Add space

        // Pay button
        payButton = new JButton("Pay");
        payButton.setAlignmentX(CENTER_ALIGNMENT);
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle payment logic here
                String cardNumber = cardNumberField.getText();
                String cardHolder = cardHolderField.getText();
                String expiryDate = expiryDateField.getText();
                String cvv = cvvField.getText();

                if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                    statusLabel.setText("Please fill in all fields.");
                } else {
                    // Simulate payment processing
                    boolean paymentSuccess = processPayment(cardNumber, cardHolder, expiryDate, cvv);
                    if (paymentSuccess) {
                        statusLabel.setText("Payment successful! Thank you.");
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

    private void refreshTickets() {
        ticketsPanel.removeAll();
        for (String seat : seatsSelected) {
            JPanel ticketPanel = new JPanel();
            ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.X_AXIS));

            JLabel ticketLabel = new JLabel("Seat: " + seat);
            ticketPanel.add(ticketLabel);

            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seatsSelected.remove(seat);
                    refreshTickets();
                }
            });
            ticketPanel.add(Box.createHorizontalStrut(10)); // Add space between label and button
            ticketPanel.add(removeButton);

            ticketsPanel.add(ticketPanel);
            ticketsPanel.add(Box.createVerticalStrut(5)); // Add space between tickets
        }
        ticketsPanel.revalidate();
        ticketsPanel.repaint();
    }

    private boolean processPayment(String cardNumber, String cardHolder, String expiryDate, String cvv) {
        // Simulate payment processing logic
        // In a real application, you would integrate with a payment gateway here
        return true; // Simulate a successful payment
    }

    private boolean processPayment(Number cardNumber) {
        // Simulate payment processing logic
        // In a real application, you would integrate with a payment gateway here
        return true; // Simulate a successful payment
    }

}