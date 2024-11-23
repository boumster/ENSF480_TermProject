package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GuestPage extends JPanel {
    
    public GuestPage(MovieTheatreApp app) {
        JLabel headerLabel = new JLabel("Guest", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Adjust header height
        add(headerLabel, BorderLayout.NORTH); // Add header at the top

        JButton browseButton = new JButton("Browse Movies");
        browseButton.setAlignmentX(CENTER_ALIGNMENT);
        browseButton.addActionListener(e-> app.switchToPage("BrowseMovies"));

        
    }
}