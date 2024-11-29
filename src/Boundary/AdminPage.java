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
        JPanel sendEmailPanel = createPlaceholderPanel("Send Email Page");

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
    
        JLabel movieLabel = new JLabel("Select Movie to Delete:");
        JComboBox<String> movieDropdown = new JComboBox<>();
        JButton submitButton = new JButton("Delete Movie");
        JButton backButton = new JButton("Back");
    
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

    private JPanel createPlaceholderPanel(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

   /*  public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Admin Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);

            AdminPage adminPage = new AdminPage(new MovieTheatreApp());
            frame.add(adminPage);

            frame.setVisible(true);
        });
    } */
}
