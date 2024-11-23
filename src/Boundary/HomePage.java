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

        /* 
        JButton browseButton = new JButton("Browse Movies");
        browseButton.setAlignmentX(CENTER_ALIGNMENT);
        browseButton.addActionListener(e-> app.switchToPage("BrowseMovies"));
        */
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.addActionListener(e-> app.switchToPage("Login"));

        JButton guestButton = new JButton("Continue as Guest");
        guestButton.setAlignmentX(CENTER_ALIGNMENT);
        guestButton.addActionListener(e-> app.switchToPage("BrowseMovies"));

        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setAlignmentX(CENTER_ALIGNMENT);
        adminLoginButton.addActionListener(e-> app.switchToPage("AdminLogin"));


        add(Box.createVerticalStrut(100)); // Add space
        add(welcomeLabel);
        add(Box.createVerticalStrut(20)); // Add space
        add(loginButton);
        add(Box.createVerticalStrut(20)); // Add space
        add(guestButton);
        add(Box.createVerticalStrut(20)); // Add space
        add(adminLoginButton);
    }

}


