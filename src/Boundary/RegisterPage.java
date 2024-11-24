package src.Boundary;

import javax.swing.*;
import java.awt.*;
import src.Control.RegisterControl;
import src.Entity.RegUser;

public class RegisterPage extends JPanel {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField addressField;
    private JTextField paymentCardField;

    public RegisterPage(MovieTheatreApp app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Register", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(headerLabel);

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

        // Email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(emailLabel);

        // Email field
        emailField = new JTextField(20);
        emailField.setMaximumSize(emailField.getPreferredSize());
        emailField.setAlignmentX(CENTER_ALIGNMENT);
        add(emailField);

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

        add(Box.createVerticalStrut(10)); // Add space

        // Confirm Password label
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(confirmPasswordLabel);

        // Confirm Password field
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setMaximumSize(confirmPasswordField.getPreferredSize());
        confirmPasswordField.setAlignmentX(CENTER_ALIGNMENT);
        add(confirmPasswordField);

        add(Box.createVerticalStrut(10)); // Add space

        // Address label
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(addressLabel);

        // Address field
        addressField = new JTextField(20);
        addressField.setMaximumSize(addressField.getPreferredSize());
        addressField.setAlignmentX(CENTER_ALIGNMENT);
        add(addressField);
        add(Box.createVerticalStrut(30)); // Add space

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String address = addressField.getText();

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
                return;
            }

            RegUser newUser = RegisterControl.register(username, password, email, address);
            if (newUser != null) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                app.setCurrentUser(newUser);
                app.switchToPage("Home");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again with a different username.");
            }
        });
        add(registerButton);

        add(Box.createVerticalStrut(20)); // Add space

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> app.switchToPage("Home"));
        add(backButton);
    }
}