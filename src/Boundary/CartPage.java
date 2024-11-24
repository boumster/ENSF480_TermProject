package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import src.Entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CartPage extends JFrame {
    private Showtime showtime;
    private ArrayList<String> selectedSeats;

    public CartPage(Showtime showtime, ArrayList<String> selectedSeats) {
        this.showtime = showtime;
        this.selectedSeats = selectedSeats;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d, h:mm a");

        // Format the LocalDateTim
        String formattedDateTime = showtime.getShowtime().format(formatter);
        // Set up the JFrame
        setTitle("Cart");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a label displaying movie details
        JLabel movieDetailsLabel = new JLabel("Movie: " + showtime.getMovie().getTitle() + 
                                              " | Showtime: " + formattedDateTime + " | Selected Seats: " + getSelectedSeatsText(), JLabel.CENTER);
        movieDetailsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movieDetailsLabel.setForeground(Color.BLACK);
        add(movieDetailsLabel, BorderLayout.NORTH);

        // Create a panel to display selected seats
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(selectedSeats.size(), 1)); // Each seat on a new line

        for (String seat : selectedSeats) {
            JLabel seatLabel = new JLabel("Seat " + seat, JLabel.CENTER);
            seatLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            seatPanel.add(seatLabel);
        }

        add(seatPanel, BorderLayout.CENTER);

        // Add a button to confirm the purchase or go back
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm Purchase");
        JButton backButton = new JButton("Back");

        // Action listeners for buttons
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Seats confirmed!");
            dispose(); // Close CartPage after confirming
        });

        backButton.addActionListener(e -> {
            this.dispose(); // Close CartPage to go back to SeatMap
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    private String getSelectedSeatsText() {
        if(selectedSeats.isEmpty()){
            return "None";
        }
        return String.join(", ",selectedSeats);
    }

    
}
