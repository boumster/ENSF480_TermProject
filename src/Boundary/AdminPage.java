package src.Boundary;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import src.Control.AdminControl;

public class AdminPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private AdminControl adminControl;

    public AdminPage(MovieTheatreApp movieTheatreApp) {
        this.adminControl = new AdminControl();
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel mainPanel = createMainPanel();
        JPanel addMoviePanel = createAddMoviePanel();
        JPanel deleteMoviePanel = createDeleteMoviePanel();
        JPanel sendEmailPanel = createSendEmailPanel();

        // Add panels to cardPanel
        cardPanel.add(mainPanel, "Main");
        cardPanel.add(addMoviePanel, "AddMovie");
        cardPanel.add(deleteMoviePanel, "DeleteMovie");
        cardPanel.add(sendEmailPanel, "SendEmail");

        // Add cardPanel to the AdminPage
        add(cardPanel, BorderLayout.CENTER);
        // Back Button (South)
        JButton backButton = new JButton("Logout");
        backButton.addActionListener(e -> {
            movieTheatreApp.switchToPage("Home");
            movieTheatreApp.setCurrentUser(null);
        });
        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton addMovieButton = new JButton("Add Movie");
        JButton deleteMovieButton = new JButton("Delete Movie");
        JButton sendEmailButton = new JButton("Send Email");

        addMovieButton.addActionListener(e -> cardLayout.show(cardPanel, "AddMovie"));
        deleteMovieButton.addActionListener(e -> cardLayout.show(cardPanel, "DeleteMovie"));
        sendEmailButton.addActionListener(e -> cardLayout.show(cardPanel, "SendEmail"));

        mainPanel.add(addMovieButton);
        mainPanel.add(deleteMovieButton);
        mainPanel.add(sendEmailButton);

        return mainPanel;
    }

    private JPanel createAddMoviePanel() {
        JPanel addMoviePanel = new JPanel(new GridLayout(7, 2, 10, 10));
        addMoviePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Movie Name:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Movie Description:");
        JTextField descriptionField = new JTextField();

        JLabel genreLabel = new JLabel("Movie Genre:");
        JTextField genreField = new JTextField();

        JLabel ratingLabel = new JLabel("Movie Rating:");
        JTextField ratingField = new JTextField();

        JLabel durationLabel = new JLabel("Movie Duration (minutes):");
        JTextField durationField = new JTextField();

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String genre = genreField.getText();
            String rating = ratingField.getText();
            String durationText = durationField.getText();

            if (name.isEmpty() || description.isEmpty() || genre.isEmpty() || rating.isEmpty()
                    || durationText.isEmpty()) {
                JOptionPane.showMessageDialog(addMoviePanel, "All fields are required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int duration = Integer.parseInt(durationText);
                adminControl.addMovie(name, description, genre, rating, duration);
                JOptionPane.showMessageDialog(addMoviePanel, "Movie added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                nameField.setText("");
                descriptionField.setText("");
                genreField.setText("");
                ratingField.setText("");
                durationField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addMoviePanel, "Duration must be a number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addMoviePanel, "Error adding movie: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));

        addMoviePanel.add(nameLabel);
        addMoviePanel.add(nameField);
        addMoviePanel.add(descriptionLabel);
        addMoviePanel.add(descriptionField);
        addMoviePanel.add(genreLabel);
        addMoviePanel.add(genreField);
        addMoviePanel.add(ratingLabel);
        addMoviePanel.add(ratingField);
        addMoviePanel.add(durationLabel);
        addMoviePanel.add(durationField);
        addMoviePanel.add(submitButton);
        addMoviePanel.add(backButton);

        return addMoviePanel;
    }

    private JPanel createDeleteMoviePanel() {
        JPanel deleteMoviePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        deleteMoviePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel movieLabel = new JLabel("Select Movie to Delete:");
        JComboBox<String> movieDropdown = new JComboBox<>();
        JButton submitButton = new JButton("Delete Movie");
        JButton backButton = new JButton("Back");

        // Populate dropdown with movie names
        try {
            java.util.List<String> movieNames = adminControl.getAllMovieNames();
            if (movieNames.isEmpty()) {
                JOptionPane.showMessageDialog(deleteMoviePanel, "No movies available to delete.", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (String movieName : movieNames) {
                    movieDropdown.addItem(movieName);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(deleteMoviePanel, "Error fetching movies: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        submitButton.addActionListener(e -> {
            String selectedMovie = (String) movieDropdown.getSelectedItem();

            if (selectedMovie == null || selectedMovie.isEmpty()) {
                JOptionPane.showMessageDialog(deleteMoviePanel, "Please select a movie to delete.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                adminControl.deleteMovie(selectedMovie);
                JOptionPane.showMessageDialog(deleteMoviePanel, "Movie deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                movieDropdown.removeAllItems();
                java.util.List<String> updatedMovieNames = adminControl.getAllMovieNames();
                for (String movieName : updatedMovieNames) {
                    movieDropdown.addItem(movieName);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(deleteMoviePanel, "Error deleting movie: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));

        deleteMoviePanel.add(movieLabel);
        deleteMoviePanel.add(movieDropdown);
        deleteMoviePanel.add(submitButton);
        deleteMoviePanel.add(backButton);

        return deleteMoviePanel;

    }

    private JPanel createSendEmailPanel() {
        JPanel sendEmailPanel = new JPanel();
        sendEmailPanel.setLayout(new BoxLayout(sendEmailPanel, BoxLayout.Y_AXIS));
        sendEmailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for more control
    
        // User ID label and field
        JLabel userIdLabel = new JLabel("User ID (leave blank for all registered users):");
        JTextField userIdField = new JTextField(10); // Adjusted size to 10 columns
        userIdField.setMaximumSize(new Dimension(200, 20)); // Set maximum size
    
        // Add components to the input panel
        inputPanel.add(userIdLabel);
        inputPanel.add(Box.createVerticalStrut(5)); // Add space between label and text field
        inputPanel.add(userIdField);
    
        // Email label and text area
        JLabel emailLabel = new JLabel("Compose Email:");
        JTextArea emailTextArea = new JTextArea(10, 30);
        emailTextArea.setLineWrap(true);
        emailTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(emailTextArea);
    
        // Add components to the input panel
        inputPanel.add(Box.createVerticalStrut(10)); // Add space between sections
        inputPanel.add(emailLabel);
        inputPanel.add(Box.createVerticalStrut(5)); // Add space between label and text area
        inputPanel.add(scrollPane);
    
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton sendButton = new JButton("Send Email");
        JButton backButton = new JButton("Back");
    
        AdminControl adminControl = new AdminControl();
    
        sendButton.addActionListener(e -> {
            String emailContent = emailTextArea.getText().trim();
            String userIdText = userIdField.getText().trim();
    
            if (emailContent.isEmpty()) {
                JOptionPane.showMessageDialog(sendEmailPanel, "Email cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // If User ID is provided, send to the specific user
            if (!userIdText.isEmpty()) {
                int userId = -1;
                try {
                    userId = Integer.parseInt(userIdText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(sendEmailPanel, "User ID must be numeric.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Send email to the specific user
                adminControl.sendEmail(userId, null, emailContent); // Send to specific user (no ticket ID required)
                JOptionPane.showMessageDialog(sendEmailPanel, "Email Sent to User ID: " + userId, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // If User ID is not provided, send to all registered users
                adminControl.sendEmailToRegisteredUsers(emailContent); // Use the method for registered users
                JOptionPane.showMessageDialog(sendEmailPanel, "Email Sent to All Registered Users!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
    
            // Clear the fields after sending the email
            emailTextArea.setText(""); // Clear the text area
            userIdField.setText(""); // Clear the User ID field
        });
    
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main")); // Go back to main page
    
        buttonPanel.add(sendButton);
        buttonPanel.add(backButton);
    
        // Add components to the main panel
        sendEmailPanel.add(inputPanel);
        sendEmailPanel.add(Box.createVerticalStrut(20)); // Add space between input panel and buttons
        sendEmailPanel.add(buttonPanel);
    
        return sendEmailPanel;
    }
}
