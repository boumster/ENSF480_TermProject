package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import src.Control.TicketControl;
import src.Control.UserControl;
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
        payButton.setBackground(new Color(65, 65, 69)); // Set background color
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
        backButton.setBackground(new Color(65, 65, 69)); // Set background color
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
        setForeground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(65, 65, 69));

        JLabel headerLabel = new JLabel("Ticket Payment");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setForeground(Color.WHITE); // Set text color to white
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        // Tickets panel
        ticketsPanel = new JPanel();
        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
        ticketsPanel.setBackground(new Color(65, 65, 69));
        add(ticketsPanel);

        refreshTickets();

        add(Box.createVerticalStrut(20)); // Add space

        if (app.getCurrentUser() != null) {
            creditsLabel = new JLabel("Credits: $" + app.getCurrentUser().getCredits());
            creditsLabel.setFont(new Font("Arial", Font.BOLD, 18));
            creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            creditsLabel.setForeground(Color.WHITE); // Set text color to white
            add(creditsLabel);

            payCreditsButton = new JButton("Pay with Credits");
            payCreditsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            payCreditsButton.setBackground(new Color(103, 103, 110)); // Set background color
            payCreditsButton.setForeground(Color.WHITE); // Set text color
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
                                    price, userID);
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

        add(Box.createVerticalStrut(20)); // Add space

        // Card Number label and field
        JPanel cardNumberPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardNumberPanel.setBackground(new Color(65, 65, 69));
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setForeground(Color.WHITE); // Set text color to white
        cardNumberPanel.add(cardNumberLabel);

        cardNumberField = new JTextField(10);
        cardNumberField.setMaximumSize(new Dimension(200, 10)); // Set maximum size
        cardNumberPanel.add(cardNumberField);
        add(cardNumberPanel);

        add(Box.createVerticalStrut(5)); // Add space

        // Card Holder label and field
        JPanel cardHolderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardHolderPanel.setBackground(new Color(65, 65, 69));
        JLabel cardHolderLabel = new JLabel("Card Holder Name:");
        cardHolderLabel.setForeground(Color.WHITE); // Set text color to white
        cardHolderPanel.add(cardHolderLabel);

        cardHolderField = new JTextField(10);
        cardHolderField.setMaximumSize(new Dimension(200, 5)); // Set maximum size
        cardHolderPanel.add(cardHolderField);
        add(cardHolderPanel);

        add(Box.createVerticalStrut(10)); // Add space

        // Expiry Date label and field
        JPanel expiryDatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        expiryDatePanel.setBackground(new Color(65, 65, 69));
        JLabel expiryDateLabel = new JLabel("Expiry Date:");
        expiryDateLabel.setForeground(Color.WHITE); // Set text color to white
        expiryDatePanel.add(expiryDateLabel);

        expiryDateField = new JTextField(5);
        expiryDateField.setMaximumSize(new Dimension(200, 5)); // Set maximum size
        expiryDatePanel.add(expiryDateField);
        add(expiryDatePanel);

        add(Box.createVerticalStrut(5)); // Add space

        // CVV label and field
        JPanel cvvPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cvvPanel.setBackground(new Color(65, 65, 69));
        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setForeground(Color.WHITE); // Set text color to white
        cvvPanel.add(cvvLabel);

        cvvField = new JTextField(5);
        cvvField.setMaximumSize(new Dimension(200, 5)); // Set maximum size
        cvvPanel.add(cvvField);
        add(cvvPanel);

        add(Box.createVerticalStrut(5)); // Add space

        // Pay button
        payButton = new JButton("Pay $" + calculateTotalPrice());
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        payButton.setBackground(new Color(103, 103, 110)); // Set background color
        payButton.setForeground(Color.WHITE); // Set text color
        payButton.addActionListener(e -> {
            if (cardNumberField.getText().isEmpty() || cardHolderField.getText().isEmpty()
                    || expiryDateField.getText().isEmpty() || cvvField.getText().isEmpty()) {
                statusLabel.setText("Please fill in all fields.");
            } else {
                boolean paymentSuccess = processPayment(cardNumberField.getText(), cardHolderField.getText(),
                        expiryDateField.getText(), cvvField.getText());
                if (paymentSuccess) {
                    JOptionPane.showMessageDialog(this, "Payment successful! Thank you.");
                    app.switchToPage("Home");
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
                }
            }
        });
        add(payButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Back Button
        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(103, 103, 110)); // Set background color
        backButton.setForeground(Color.WHITE); // Set text color
        backButton.addActionListener(e -> app.switchToPage("SeatMap"));
        add(backButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Status label
        statusLabel = new JLabel("");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.WHITE); // Set text color to white
        add(statusLabel);
    }

    private void refreshTickets() {
        ticketsPanel.removeAll();
        for (String seat : seatsSelected) {
            JPanel ticketPanel = new JPanel();
            ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.X_AXIS));
            ticketPanel.setBackground(new Color(65, 65, 69));

            JLabel ticketLabel = new JLabel("Seat: " + seat);
            ticketLabel.setForeground(Color.WHITE);
            ticketPanel.add(ticketLabel);

            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(e -> {
                seatsSelected.remove(seat);
                refreshTickets();
            });
            ticketPanel.add(Box.createHorizontalStrut(10));
            ticketPanel.add(removeButton);

            ticketsPanel.add(ticketPanel);
            ticketsPanel.add(Box.createVerticalStrut(5)); // Add space between tickets
        }
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
