package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

import src.Control.AdminControl;
import src.Control.TicketControl;
import src.Entity.Ticket;
import src.Entity.RegUser;

public class ViewTicketsPage extends JPanel {
    private MovieTheatreApp app;
    private JPanel ticketsPanel;

    public ViewTicketsPage(MovieTheatreApp app) {
        this.app = app;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Your Tickets");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setBackground(new Color(103, 103, 110));
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setForeground(Color.WHITE); // Set header color for better readability
        setBackground(new Color(103, 103, 110));
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        ticketsPanel = new JPanel();
        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
        ticketsPanel.setBackground(new Color(65, 65, 69)); // Set background color for tickets panel
        add(ticketsPanel);

        refreshTickets();

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setBackground(new Color(103, 103, 110)); // Neutral button background
        backButton.setForeground(Color.WHITE); // Button text color
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.switchToPage("Home"); // Switch to the home page or previous page
            }
        });
        add(Box.createVerticalStrut(20)); // Add space
        add(backButton);
    }

    public void refreshTickets() {
        ticketsPanel.removeAll();

        RegUser currentUser = app.getCurrentUser();
        if (currentUser != null) {
            ArrayList<Ticket> tickets = TicketControl.getTickets(currentUser.getUserID());
            for (Ticket ticket : tickets) {
                JPanel ticketPanel = new JPanel();
                ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.X_AXIS));
                ticketPanel.setBackground(new Color(75, 75, 79)); // Slightly darker background for each ticket
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d, h:mm a");

                // Format the LocalDateTime
                String formattedDateTime = ticket.getShowtime().getShowtime().format(formatter);

                JLabel ticketLabel = new JLabel(
                        "Ticket ID: " + ticket.getTicketId() + ", Movie: " + ticket.getShowtime().getMovie().getTitle()
                                + ", Showtime: " + formattedDateTime + ", Seat: " + ticket.getSeatNumber()
                                + ", Price: $" + ticket.getPrice());
                ticketLabel.setForeground(Color.WHITE); // Set label text color for readability

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(new Color(120, 0, 0)); // Red background for cancel button
                cancelButton.setForeground(Color.WHITE); // White text for better visibility
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean success = TicketControl.cancelTicket(ticket, app);
                        if (success) {
                            double refundAmount = ticket.getPrice().doubleValue();
                            if (!currentUser.getIsRegisteredUser()) {
                                refundAmount *= 0.85;
                            }
                            JOptionPane.showMessageDialog(null,
                                    "Ticket canceled successfully. Refund amount: $" + refundAmount, "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            AdminControl adminControl = new AdminControl();
                            String emailMessage = "Your ticket has been successfully cancelled.\n" +
                                                "Seat: " + ticket.getSeatNumber().toString() + "\n" +
                                                "Refunded Amount: $" + ticket.getPrice() + "\n" +
                                                "Hope to see you again soon!";
                            adminControl.sendEmail(app.getCurrentUser().getUserID(), null, emailMessage);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Cannot cancel ticket less than 72 hours before showtime.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        refreshTickets();
                    }
                });

                // Create a horizontal box to hold the label and button
                Box horizontalBox = Box.createHorizontalBox();
                horizontalBox.add(ticketLabel);
                horizontalBox.add(Box.createHorizontalStrut(5));
                horizontalBox.add(cancelButton);

                ticketPanel.add(horizontalBox);

                ticketsPanel.add(ticketPanel);
                ticketsPanel.add(Box.createVerticalStrut(5));
            }
        } else {
            JLabel noTicketsLabel = new JLabel("No tickets found.");
            noTicketsLabel.setAlignmentX(CENTER_ALIGNMENT);
            noTicketsLabel.setForeground(Color.LIGHT_GRAY); // Subtle text color for no tickets found
            ticketsPanel.add(noTicketsLabel);
        }

        ticketsPanel.revalidate();
        ticketsPanel.repaint();
    }
}
