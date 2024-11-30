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
    private JButton mailboxButton;
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

        guestButton = new JButton("Continue as Guest");
        guestButton.setAlignmentX(CENTER_ALIGNMENT);
        guestButton.addActionListener(e -> app.switchToPage("BrowseMovies"));

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setAlignmentX(CENTER_ALIGNMENT);
        adminLoginButton.addActionListener(e -> app.switchToPage("AdminLogin"));

        registerUser = new JButton("Register");
        registerUser.setAlignmentX(CENTER_ALIGNMENT);
        registerUser.addActionListener(e -> app.switchToPage("Register"));

        creditLabel = new JLabel("");
        creditLabel.setAlignmentX(CENTER_ALIGNMENT);

        if (app.getCurrentUser() != null) {
            welcomeLabel.setText("Welcome, " + app.getCurrentUser().getUsername() + "!");
            creditLabel.setText("Credits: " + app.getCurrentUser().getCredits());
            viewTicketsButton = new JButton("View Tickets");
            viewTicketsButton.setAlignmentX(CENTER_ALIGNMENT);
            viewTicketsButton.addActionListener(e -> app.switchToPage("ViewTickets"));

            mailboxButton = new JButton("Mailbox");
            mailboxButton.setAlignmentX(CENTER_ALIGNMENT);
            mailboxButton.addActionListener(e -> app.switchToPage("MailPage"));

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
            add(Box.createVerticalStrut(20)); 
            add(mailboxButton);
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