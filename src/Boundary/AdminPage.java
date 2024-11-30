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
        setBackground(new Color(65, 65, 69)); // Set background color

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
        backButton.setBackground(new Color(65, 65, 69)); // Set background color
        backButton.setForeground(Color.WHITE); // Set text color
        backButton.addActionListener(e -> {
            movieTheatreApp.switchToPage("Home");
            movieTheatreApp.setCurrentUser(null);
        });
        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(65, 65, 69)); // Set background color
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.setBackground(new Color(65, 65, 69)); // Set background color

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.setBackground(new Color(65, 65, 69)); // Set background color
        addMovieButton.setForeground(Color.WHITE); // Set text color
        JButton deleteMovieButton = new JButton("Delete Movie");
        deleteMovieButton.setBackground(new Color(65, 65, 69)); // Set background color
        deleteMovieButton.setForeground(Color.WHITE); // Set text color
        JButton sendEmailButton = new JButton("Send Email");
        sendEmailButton.setBackground(new Color(65, 65, 69)); // Set background color
        sendEmailButton.setForeground(Color.WHITE); // Set text color

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
        addMoviePanel.setBackground(new Color(65, 65, 69)); // Set background color

        JLabel nameLabel = new JLabel("Movie Name:");
        nameLabel.setForeground(Color.WHITE); // Set text color
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Movie Description:");
        descriptionLabel.setForeground(Color.WHITE); // Set text color
        JTextField descriptionField = new JTextField();

        JLabel genreLabel = new JLabel("Movie Genre:");
        genreLabel.setForeground(Color.WHITE); // Set text color
        JTextField genreField = new JTextField();

        JLabel ratingLabel = new JLabel("Movie Rating:");
        ratingLabel.setForeground(Color.WHITE); // Set text color
        JTextField ratingField = new JTextField();

        JLabel durationLabel = new JLabel("Movie Duration (minutes):");
        durationLabel.setForeground(Color.WHITE); // Set text color
        JTextField durationField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(65, 65, 69)); // Set background color
        submitButton.setForeground(Color.WHITE); // Set text color
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(65, 65, 69)); // Set background color
        backButton.setForeground(Color.WHITE); // Set text color

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String genre = genreField.getText();
            String rating = ratingField.getText();
            String durationText = durationField.getText();

            if (name.isEmpty() || description.isEmpty() || genre.isEmpty() || rating.isEmpty() || durationText.isEmpty()) {
                JOptionPane.showMessageDialog(addMoviePanel, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int duration = Integer.parseInt(durationText);
                adminControl.addMovie(name, description, genre, rating, duration);
                JOptionPane.showMessageDialog(addMoviePanel, "Movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                nameField.setText("");
                descriptionField.setText("");
                genreField.setText("");
                ratingField.setText("");
                durationField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addMoviePanel, "Duration must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addMoviePanel, "Error adding movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        deleteMoviePanel.setBackground(new Color(65, 65, 69)); // Set background color
    
        JLabel movieLabel = new JLabel("Select Movie to Delete:");
        movieLabel.setForeground(Color.WHITE); // Set text color
        JComboBox<String> movieDropdown = new JComboBox<>();
        JButton submitButton = new JButton("Delete Movie");
        submitButton.setBackground(new Color(65, 65, 69)); // Set background color
        submitButton.setForeground(Color.WHITE); // Set text color
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(65, 65, 69)); // Set background color
        backButton.setForeground(Color.WHITE); // Set text color
    
        // Populate dropdown with movie names
        try {
            java.util.List<String> movieNames = adminControl.getAllMovieNames();
            if (movieNames.isEmpty()) {
                JOptionPane.showMessageDialog(deleteMoviePanel, "No movies available to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (String movieName : movieNames) {
                    movieDropdown.addItem(movieName);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(deleteMoviePanel, "Error fetching movies: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        submitButton.addActionListener(e -> {
            String selectedMovie = (String) movieDropdown.getSelectedItem();
    
            if (selectedMovie == null || selectedMovie.isEmpty()) {
                JOptionPane.showMessageDialog(deleteMoviePanel, "Please select a movie to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                adminControl.deleteMovie(selectedMovie);
                JOptionPane.showMessageDialog(deleteMoviePanel, "Movie deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    
                movieDropdown.removeAllItems();
                java.util.List<String> updatedMovieNames = adminControl.getAllMovieNames();
                for (String movieName : updatedMovieNames) {
                    movieDropdown.addItem(movieName);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(deleteMoviePanel, "Error deleting movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        JPanel sendEmailPanel = new JPanel(new BorderLayout());
        sendEmailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sendEmailPanel.setBackground(new Color(65, 65, 69)); // Set background color
        JTextArea emailTextArea = new JTextArea(10, 30);
        emailTextArea.setLineWrap(true);
        emailTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(emailTextArea);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(65, 65, 69)); // Set background color
        JButton sendButton = new JButton("Send Email");
        sendButton.setBackground(new Color(65, 65, 69)); // Set background color
        sendButton.setForeground(Color.WHITE); // Set text color
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(65, 65, 69)); // Set background color
        backButton.setForeground(Color.WHITE); // Set text color
    
        sendButton.addActionListener(e -> {
            String emailContent = emailTextArea.getText().trim();
            if (emailContent.isEmpty()) {
                JOptionPane.showMessageDialog(sendEmailPanel, "Email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(sendEmailPanel, "Email Sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
                emailTextArea.setText("");
            }
        });
    
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));
    
        buttonPanel.add(sendButton);
        buttonPanel.add(backButton);
        sendEmailPanel.add(new JLabel("Compose Email:"), BorderLayout.NORTH);
        sendEmailPanel.add(scrollPane, BorderLayout.CENTER);
        sendEmailPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        return sendEmailPanel;
    }
}
