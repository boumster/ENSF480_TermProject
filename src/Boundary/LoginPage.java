package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoginPage extends JPanel {
    
    public LoginPage(MovieTheatreApp app) {
        JLabel headerLabel = new JLabel("Login", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Adjust header height
        add(headerLabel, BorderLayout.NORTH); // Add header at the top

        // Set the layout to GridLayout: 4 rows, 5 columns (can be adjusted)
        setLayout(new GridLayout(3, 5, 10, 10)); // 4 rows, 5 columns, with space between buttons

        
    }
}
