package src.Boundary;

import javax.swing.*;

public class HomePage extends JPanel {
    private JLabel welcomeLabel;
    private JLabel creditLabel;
    private JButton browseMoviesButton;
    private JButton loginButton;
    private JButton guestButton;
    private JButton adminLoginButton;
    private JButton registerUser;
    private JButton logoutButton;
    private JButton payFeeButton;
    private JButton viewTicketsButton;
    private MovieTheatreApp app;

    public HomePage(MovieTheatreApp app) {
        this.app = app;
        generateLayout();
    }

    public void refresh() {
        removeAll();
        generateLayout();
        revalidate();
        repaint();
    }

    public void generateLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        welcomeLabel = new JLabel("Welcome to AcmePlex!");
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        browseMoviesButton = new JButton("Browse Movies");
        browseMoviesButton.setAlignmentX(CENTER_ALIGNMENT);
        browseMoviesButton.addActionListener(e -> app.switchToPage("BrowseMovies"));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> app.switchToPage("Login"));
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(false);

        guestButton = new JButton("Continue as Guest");
        guestButton.setAlignmentX(CENTER_ALIGNMENT);
        guestButton.addActionListener(e -> app.switchToPage("BrowseMovies"));
        guestButton.setContentAreaFilled(false);
        guestButton.setBorderPainted(false);
        guestButton.setFocusPainted(false);
        guestButton.setOpaque(false);

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setAlignmentX(CENTER_ALIGNMENT);
        adminLoginButton.addActionListener(e -> app.switchToPage("AdminLogin"));
        adminLoginButton.setContentAreaFilled(false);
        adminLoginButton.setBorderPainted(false);
        adminLoginButton.setFocusPainted(false);
        adminLoginButton.setOpaque(false);

        registerUser = new JButton("Register");
        registerUser.setAlignmentX(CENTER_ALIGNMENT);
        registerUser.addActionListener(e -> app.switchToPage("Register"));
        registerUser.setContentAreaFilled(false);
        registerUser.setBorderPainted(false);
        registerUser.setFocusPainted(false);
        adminLoginButton.setOpaque(false);

        creditLabel = new JLabel("");
        creditLabel.setAlignmentX(CENTER_ALIGNMENT);

        if (app.getCurrentUser() != null) {
            welcomeLabel.setText("Welcome, " + app.getCurrentUser().getUsername() + "!");
            creditLabel.setText("Credits: " + app.getCurrentUser().getCredits());
            viewTicketsButton = new JButton("View Tickets");
            viewTicketsButton.setAlignmentX(CENTER_ALIGNMENT);
            viewTicketsButton.addActionListener(e -> app.switchToPage("ViewTickets"));

            logoutButton = new JButton("Logout");
            logoutButton.setAlignmentX(CENTER_ALIGNMENT);
            logoutButton.addActionListener(e -> {
                app.setCurrentUser(null);
                app.switchToPage("Home");
            });

            payFeeButton = new JButton("Pay Fee");
            payFeeButton.setAlignmentX(CENTER_ALIGNMENT);
            payFeeButton.addActionListener(e -> {
                app.switchToPage("FeePayment");
            });

            add(Box.createVerticalStrut(20));
            add(welcomeLabel);
            add(Box.createVerticalStrut(20));
            add(creditLabel);
            add(Box.createVerticalStrut(20));
            add(viewTicketsButton);
            add(Box.createVerticalStrut(20));
            add(browseMoviesButton);
            if (!app.getCurrentUser().getIsRegisteredUser()) {
                add(Box.createVerticalStrut(20));
                add(payFeeButton);
            }
            add(Box.createVerticalStrut(20));
            add(logoutButton);
        } else {
            add(Box.createVerticalStrut(20));
            add(welcomeLabel);
            add(Box.createVerticalStrut(20));
            add(creditLabel);
            add(Box.createVerticalStrut(20));
            add(loginButton);
            add(Box.createVerticalStrut(20));
            add(guestButton);
            add(Box.createVerticalStrut(20));
            add(adminLoginButton);
            add(Box.createVerticalStrut(20));
            add(registerUser);
        }
    }
}