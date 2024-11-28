package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import src.Control.LoginControl;
import src.Control.UserControl;
import src.Entity.RegUser;

public class LoginPage extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton showPasswordButton;
    private boolean isPasswordVisible = false;
    public LocalDate currDate = LocalDate.now();

    public LoginPage(MovieTheatreApp app, String type) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (type == "ADMIN") {
            JLabel headerLabel = new JLabel("Admin Login", SwingConstants.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
            headerLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(headerLabel);
        } else {
            JLabel headerLabel = new JLabel("Login", SwingConstants.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
            headerLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(headerLabel);
        }

        add(Box.createVerticalStrut(20)); // Add space

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(usernameLabel);

        // Username field
        usernameField = new JTextField(20);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        usernameField.setAlignmentX(CENTER_ALIGNMENT);
        add(usernameField);

        add(Box.createVerticalStrut(10)); // Add space

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(passwordLabel);

        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        passwordField.setAlignmentX(CENTER_ALIGNMENT);
        add(passwordField);

        // Show/Hide password button
        showPasswordButton = new JButton("Show");
        showPasswordButton.setAlignmentX(CENTER_ALIGNMENT);
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPasswordVisible) {
                    passwordField.setEchoChar('*');
                    showPasswordButton.setText("Show");
                } else {
                    passwordField.setEchoChar((char) 0);
                    showPasswordButton.setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
        add(showPasswordButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Login button

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean isAdminLogin = type.equals("ADMIN");

            RegUser user = LoginControl.login(email, password, isAdminLogin);
            if (user != null) {
                app.setCurrentUser(user);
                JOptionPane.showMessageDialog(this, "Login successful!");

                if (isAdminLogin) {
                    app.switchToPage("AdminPage");
                } else {
                    UserControl controller = new UserControl();
                    LocalDate currDate = LocalDate.now(); // Get the current date
                    if (currDate.getMonthValue() == 1 && currDate.getDayOfMonth() == 1) {
                        // It's January 1st
                        controller.deductCredit(user.getUserID(), user.getCredits().doubleValue());
                    }
                    app.switchToPage("Home");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Please check your credentials.");
            }
        });
        add(loginButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Register button
        if (type == "USER") {
            JButton registerButton = new JButton("Register");
            registerButton.setAlignmentX(CENTER_ALIGNMENT);
            registerButton.addActionListener(e -> app.switchToPage("Register"));
            add(registerButton);
            add(Box.createVerticalStrut(20)); // Add space
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> app.switchToPage("Home"));
        add(backButton);
    }
}