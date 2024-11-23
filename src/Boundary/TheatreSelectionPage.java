package src.Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TheatreSelectionPage extends JPanel{
    public TheatreSelectionPage(MovieTheatreApp app){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Select a Theatre:");
        label.setAlignmentX(CENTER_ALIGNMENT);

        String[] theatres = {"Theatre 1", "Theatre 2", "Theatre 3"};
        JComboBox<String> theatreDropdown = new JComboBox<>(theatres);
        theatreDropdown.setAlignmentX(CENTER_ALIGNMENT);

        JButton nextButton = new JButton("Next");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> app.switchToPage("Showtimes"));

        add(Box.createVerticalStrut(50)); // Add space
        add(label);
        add(Box.createVerticalStrut(20)); // Add space
        add(theatreDropdown);
        add(Box.createVerticalStrut(20)); // Add space
        add(nextButton);
    }
}
