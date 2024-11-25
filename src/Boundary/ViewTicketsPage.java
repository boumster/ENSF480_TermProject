package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        ticketsPanel = new JPanel();
        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
        add(ticketsPanel);

        refreshTickets();

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d, h:mm a");

                // Format the LocalDateTime
                String formattedDateTime = ticket.getShowtime().getShowtime().format(formatter);

                JLabel ticketLabel = new JLabel(
                        "Ticket ID: " + ticket.getTicketId() + ", Movie: " + ticket.getShowtime().getMovie().getTitle()
                                + ", Showtime: " + formattedDateTime + ", Seat: " + ticket.getSeatNumber()
                                + ", Price: $" + ticket.getPrice());

                JButton cancelButton = new JButton("Cancel");
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
            ticketsPanel.add(noTicketsLabel);
        }

        ticketsPanel.revalidate();
        ticketsPanel.repaint();
    }
}