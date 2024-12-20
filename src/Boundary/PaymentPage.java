package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import src.Control.TicketControl;
import src.Control.UserControl;
import src.Control.AdminControl;
import src.Control.PaymentControl;

public class PaymentPage extends JPanel {
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private JButton payButton;
    private JButton payCreditsButton;
    private JLabel creditsLabel;
    private JLabel statusLabel;
    private PaymentControl paymentControl;
    private JPanel ticketsPanel;
    private ArrayList<String> seatsSelected;
    private JButton backButton;
    private JLabel totalPriceLabel;
    private MovieTheatreApp app;

    public PaymentPage(MovieTheatreApp app) {
        this.app = app;
        paymentControl = new PaymentControl();
        // Render the annual fee payment form
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(65, 65, 69));
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);

        JLabel headerLabel = new JLabel("Pay Annual Fee");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setForeground(Color.WHITE); // Set text color to white
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        // Card Number label and field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setAlignmentX(CENTER_ALIGNMENT);
        cardNumberLabel.setForeground(Color.WHITE); // Set text color to white
        add(cardNumberLabel);

        cardNumberField = new JTextField(10);
        cardNumberField.setMaximumSize(cardNumberField.getPreferredSize());
        cardNumberField.setAlignmentX(CENTER_ALIGNMENT);
        add(cardNumberField);

        add(Box.createVerticalStrut(20)); // Add space

        // Pay button
        payButton = new JButton("Pay $20");
        payButton.setAlignmentX(CENTER_ALIGNMENT);
        payButton.setBackground(new Color(103, 103, 110)); // Set background color
        payButton.setForeground(Color.WHITE); // Set text color
        payButton.addActionListener(e -> {
            // Handle payment logic here
            int cardNumber = Integer.parseInt(cardNumberField.getText());

            if (cardNumberField.getText().isEmpty()) {
                statusLabel.setText("Please fill in all fields.");
            } else {
                // Simulate payment processing
                boolean paymentSuccess = processPayment(cardNumber);
                if (paymentSuccess) {
                    JOptionPane.showMessageDialog(this, "Payment successful! Thank you.");
                    paymentControl.payAnnualFee(app.getCurrentUser(), 20.0, cardNumber);
                    app.switchToPage("Home");
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
                }
            }

        });
        add(payButton);

        add(Box.createVerticalStrut(20)); // Add space

        backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setBackground(new Color(103, 103, 110)); // Set background color
        backButton.setForeground(Color.WHITE); // Set text color
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToPage("Home");
            }
        });
        add(backButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Status label
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.WHITE); // Set text color to white
        add(statusLabel);
    }

    public PaymentPage(MovieTheatreApp app, ArrayList<String> seatsSelected) {
        this.seatsSelected = seatsSelected;
        this.app = app;
        paymentControl = new PaymentControl();
        setBackground(new Color(65, 65, 69));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setForeground(Color.WHITE);

        JLabel headerLabel = new JLabel("Ticket Payment");
        headerLabel.setForeground(Color.WHITE); // Set text color to white
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

        add(Box.createVerticalStrut(20));
        if (app.getCurrentUser() != null) {
            creditsLabel = new JLabel("Credits: $" + app.getCurrentUser().getCredits());
            creditsLabel.setFont(new Font("Arial", Font.BOLD, 18));
            creditsLabel.setForeground(Color.WHITE);
            creditsLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(creditsLabel);
            payCreditsButton = new JButton("Pay with Credits");
            payCreditsButton.setForeground(Color.WHITE);
            payCreditsButton.setBackground(new Color(103, 103, 110));
            payCreditsButton.setAlignmentX(CENTER_ALIGNMENT);

            payCreditsButton.addActionListener(e -> {
                // Check if user has enough credits to pay for the tickets
                if (app.getCurrentUser().getCredits().doubleValue() < calculateTotalPrice()) {
                    System.err.println("Insufficient Credits, please enter card details.");
                    statusLabel.setText("Insufficient credits. Please enter card details.");
                } else {
                    UserControl controller = new UserControl();
                    boolean creditsDeducted = controller.deductCredit(app.getCurrentUser().getUserID(),
                            calculateTotalPrice());
                    double newCredits = app.getCurrentUser().getCredits().doubleValue() - calculateTotalPrice();
                    app.getCurrentUser().setCredit(newCredits);

                    if (creditsDeducted) {
                        boolean allTicketsCreated = true;
                        for (String seat : seatsSelected) {
                            int movieID = app.getSelectedMovie().getId();
                            int showtimeID = app.getSelectedShowtime().getShowtimeId();
                            int seatNum = Integer.parseInt(seat);
                            double price = 10.0; // Assuming the price for a ticket is 10.0
                            Integer userID = app.getCurrentUser() != null ? app.getCurrentUser().getUserID()
                                    : null;

                            // Attempt to create each ticket
                            boolean ticketCreated = TicketControl.createTicket(movieID, showtimeID, seatNum,
                                    price,
                                    userID);
                            if (!ticketCreated) {
                                allTicketsCreated = false;
                                break;
                            }
                        }

                        // Final response after attempting to create tickets
                        if (allTicketsCreated) {
                            JOptionPane.showMessageDialog(this, "Payment successful! Thank you.");
                            app.switchToPage("Home");
                        } else {
                            JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
                    }
                }
            });
            add(payCreditsButton);

        }

        // Card Number label and field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setForeground(Color.WHITE);
        cardNumberLabel.setAlignmentX(CENTER_ALIGNMENT);
        if (app.getCurrentUser() != null && app.getCurrentUser().getIsRegisteredUser()) {
            cardNumberField = new JTextField(String.valueOf(app.getCurrentUser().getPaymentCard()), 20);
        } else {
            cardNumberField = new JTextField(20);
        }
        add(cardNumberLabel);

        cardNumberField.setMaximumSize(cardNumberField.getPreferredSize());
        cardNumberField.setAlignmentX(CENTER_ALIGNMENT);
        add(cardNumberField);

        add(Box.createVerticalStrut(10)); // Add space

        // Card Holder label and field
        JLabel cardHolderLabel = new JLabel("Card Holder Name:");
        cardHolderLabel.setForeground(Color.WHITE);
        cardHolderLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(cardHolderLabel);

        cardHolderField = new JTextField(20);
        cardHolderField.setMaximumSize(cardHolderField.getPreferredSize());
        cardHolderField.setAlignmentX(CENTER_ALIGNMENT);
        add(cardHolderField);

        add(Box.createVerticalStrut(10)); // Add space

        // Expiry Date label and field
        JLabel expiryDateLabel = new JLabel("Expiry Date (MM/YY):");
        expiryDateLabel.setForeground(Color.WHITE);
        expiryDateLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(expiryDateLabel);

        expiryDateField = new JTextField(5);
        expiryDateField.setMaximumSize(expiryDateField.getPreferredSize());
        expiryDateField.setAlignmentX(CENTER_ALIGNMENT);
        add(expiryDateField);

        add(Box.createVerticalStrut(10)); // Add space

        // CVV label and field
        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setForeground(Color.WHITE);
        cvvLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(cvvLabel);

        cvvField = new JTextField(3);
        cvvField.setMaximumSize(cvvField.getPreferredSize());
        cvvField.setAlignmentX(CENTER_ALIGNMENT);
        add(cvvField);

        add(Box.createVerticalStrut(20)); // Add space

        // Pay button
        payButton = new JButton("Pay");
        payButton.setBackground(new Color(103, 103, 110));
        payButton.setForeground(Color.WHITE);
        payButton.setAlignmentX(CENTER_ALIGNMENT);
        payButton.addActionListener(e -> {
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
                    boolean allTicketsCreated = true;
                    for (String seat : seatsSelected) {
                        int movieID = app.getSelectedMovie().getId();
                        int showtimeID = app.getSelectedShowtime().getShowtimeId();
                        int seatNum = Integer.parseInt(seat);
                        double price = 10.0;
                        Integer userID = app.getCurrentUser() != null ? app.getCurrentUser().getUserID() : null;

                            boolean ticketCreated = TicketControl.createTicket(movieID, showtimeID, seatNum, price,
                                    userID);
                            if (!ticketCreated) {
                                allTicketsCreated = false;
                                break;
                            }
                        }
                        if (allTicketsCreated) {
                            AdminControl adminControl = new AdminControl();
                            String emailMessage = "Your ticket(s) have been successfully booked.\n" +
                                                "Seats: " + seatsSelected.toString() + "\n" +
                                                "Total Price: $" + calculateTotalPrice() + "\n" +
                                                "Enjoy your movie!";
                            adminControl.sendEmail(app.getCurrentUser().getUserID(), null, emailMessage);

                            JOptionPane.showMessageDialog(this, "Payment successful! Thank you.");
                            app.switchToPage("Home");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
                    }
                }
            }
        );
        add(payButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Back button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(103, 103, 110));
        backButton.setForeground(Color.WHITE);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToPage("SeatMap");
            }
        });
        add(backButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Status label
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(statusLabel);
    }

    private void refreshTickets() {
        ticketsPanel.removeAll();
        ticketsPanel.setBackground(new Color(65, 65, 69));
        if (seatsSelected.isEmpty()) {
            app.switchToPage("SeatMap");
        }
        for (String seat : seatsSelected) {
            JPanel ticketPanel = new JPanel();
            ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.X_AXIS));
            ticketPanel.setBackground(new Color(65, 65, 69));
            ticketPanel.setForeground(Color.WHITE);
            JLabel ticketLabel = new JLabel("Seat: " + seat + ", Price: $10");
            ticketLabel.setBackground(new Color(103, 103, 110));
            ticketLabel.setForeground(Color.WHITE);
            ticketPanel.add(ticketLabel);

            JButton removeButton = new JButton("Remove");
            removeButton.setBackground(new Color(103, 103, 110));
            removeButton.setForeground(Color.WHITE);
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

        totalPriceLabel = new JLabel("Total Price: $" + calculateTotalPrice());
        totalPriceLabel.setForeground(Color.WHITE);
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalPriceLabel.setAlignmentX(CENTER_ALIGNMENT);
        ticketsPanel.add(totalPriceLabel);
        ticketsPanel.revalidate();
        ticketsPanel.repaint();
    }

    private double calculateTotalPrice() {
        return seatsSelected.size() * 10.0; // Assuming each ticket costs $10
    }

    private boolean processPayment(String cardNumber, String cardHolder, String expiryDate, String cvv) {
        return true; // Simulate a successful payment
    }

    private boolean processPayment(Number cardNumber) {
        return true; // Simulate a successful payment
    }
}