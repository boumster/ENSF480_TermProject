package src.Boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JPanel{
    JButton browseMoviesButton;

    public HomePage(MovieTheatreApp app){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel welcomeLabel = new JLabel("Welcome to AcmePlex!");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        JButton startButton = new JButton("Browse Movies");
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        startButton.addActionListener(e-> app.switchToPage("BrowseMovies"));

        add(Box.createVerticalStrut(100)); // Add space
        add(welcomeLabel);
        add(Box.createVerticalStrut(20)); // Add space
        add(startButton);
    }

}


