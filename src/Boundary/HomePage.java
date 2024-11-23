package src.Boundary;

import javax.swing.*;
import java.awt.*;
import src.Entity.RegUser;

public class HomePage extends JPanel {
    private JLabel welcomeLabel;
    private JButton browseMoviesButton;
    private JButton loginButton;
    private JButton guestButton;
    private JButton adminLoginButton;
    private JButton registerUser;
    private MovieTheatreApp app;

    public HomePage(MovieTheatreApp app) {
        this.app = app;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        welcomeLabel = new JLabel("Welcome to AcmePlex!");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        browseMoviesButton = new JButton("Browse Movies");
        browseMoviesButton.setAlignmentX(CENTER_ALIGNMENT);
        browseMoviesButton.addActionListener(e -> app.switchToPage("BrowseMovies"));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> app.switchToPage("Login"));

        guestButton = new JButton("Continue as Guest");
        guestButton.setAlignmentX(CENTER_ALIGNMENT);
        guestButton.addActionListener(e-> app.switchToPage("BrowseMovies"));

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setAlignmentX(CENTER_ALIGNMENT);
        adminLoginButton.addActionListener(e -> app.switchToPage("AdminLogin"));

        registerUser = new JButton("Register");
        registerUser.setAlignmentX(CENTER_ALIGNMENT);
        registerUser.addActionListener(e -> app.switchToPage("Register"));

        add(Box.createVerticalStrut(100)); // Add space
        add(welcomeLabel);
        add(Box.createVerticalStrut(20)); // Add space
        add(loginButton);
        add(Box.createVerticalStrut(20)); // Add space
        add(guestButton);
        add(Box.createVerticalStrut(20)); // Add space
        add(adminLoginButton);
        add(Box.createVerticalStrut(20)); // Add space
        add(registerUser);
    }

    public void refresh() {
        RegUser currentUser = app.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");

            remove(loginButton);
            remove(guestButton);
            remove(adminLoginButton);
            remove(registerUser);
            add(browseMoviesButton);
        } else {
            welcomeLabel.setText("Welcome to AcmePlex!");
            add(loginButton);
            add(guestButton);
            add(adminLoginButton);
            add(registerUser);
            remove(browseMoviesButton);
        }
        revalidate();
        repaint();
    }
}