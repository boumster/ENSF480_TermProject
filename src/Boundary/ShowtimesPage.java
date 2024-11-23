package src.Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowtimesPage extends JPanel {
    public ShowtimesPage(MovieTheatreApp app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Available Showtimes:");
        label.setAlignmentX(CENTER_ALIGNMENT);

        JList<String> showtimesList = new JList<>(new String[]{
            "Movie 1 - 2:00 PM",
            "Movie 2 - 5:00 PM",
            "Movie 3 - 8:00 PM"
        });
        showtimesList.setAlignmentX(CENTER_ALIGNMENT);

        JButton bookButton = new JButton("Book Tickets");
        bookButton.setAlignmentX(CENTER_ALIGNMENT);
        bookButton.addActionListener(e -> app.switchToPage("Booking"));

        add(Box.createVerticalStrut(50)); // Add space
        add(label);
        add(Box.createVerticalStrut(20)); // Add space
        add(new JScrollPane(showtimesList));
        add(Box.createVerticalStrut(20)); // Add space
        add(bookButton);
    }
}

