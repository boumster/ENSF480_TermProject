package src.Boundary;

import javax.swing.*;

public class BookingPage extends JPanel {
    public BookingPage(MovieTheatreApp app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter Booking Details:");
        label.setAlignmentX(CENTER_ALIGNMENT);

        JTextField nameField = new JTextField(20);
        nameField.setMaximumSize(nameField.getPreferredSize());
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);

        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setAlignmentX(CENTER_ALIGNMENT);
        confirmButton.addActionListener(e -> app.switchToPage("Confirmation"));

        add(Box.createVerticalStrut(50)); // Add space
        add(label);
        add(Box.createVerticalStrut(20)); // Add space
        add(nameLabel);
        add(nameField);
        add(Box.createVerticalStrut(20)); // Add space
        add(confirmButton);
    }
}

