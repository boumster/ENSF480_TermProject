package src.Boundary;

import javax.swing.*;

public class ConfirmationPage extends JPanel {
    public ConfirmationPage(MovieTheatreApp app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Booking Confirmed!");
        label.setAlignmentX(CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back to Home");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> app.switchToPage("Home"));

        add(Box.createVerticalStrut(100)); // Add space
        add(label);
        add(Box.createVerticalStrut(20)); // Add space
        add(backButton);
    }
}

