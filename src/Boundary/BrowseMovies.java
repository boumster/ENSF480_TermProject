package src.Boundary;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import src.Control.movieController;
import src.Entity.Movie;

public class BrowseMovies extends JPanel {
    private movieController controller;

    public BrowseMovies(MovieTheatreApp app) {
        this.controller = new movieController();

        // Set the background color for the main panel
        setBackground(new Color(65, 65, 69));
        setLayout(new BorderLayout());

        // Create header panel with a label and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(65, 65, 69)); // Header background

        // Header label
        JLabel headerLabel = new JLabel("Browse Movies", SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE); // Text color
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Font style
        headerLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Header height

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(103, 103, 110));
        backButton.setForeground(Color.WHITE); // Text color
        backButton.setFocusable(false);
        backButton.setPreferredSize(new Dimension(80, 40)); // Optional: Set size for the button
        backButton.addActionListener(e -> app.switchToPage("Home")); // Action to go back to home

        // Create a panel for the back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButtonPanel.setBackground(new Color(65, 65, 69)); // Match background color
        backButtonPanel.add(backButton);

        // Add header label and back button to header panel
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(backButtonPanel, BorderLayout.EAST);

        // Add header panel to the main panel
        add(headerPanel, BorderLayout.NORTH);

        // Create a grid panel to display movies
        JPanel moviePanel = new JPanel(new GridLayout(4, 5, 10, 10)); // 5 columns, 4 rows
        moviePanel.setBackground(new Color(65, 65, 69)); // Match background color
        add(moviePanel, BorderLayout.CENTER);

        // Fetch movies from the controller
        ArrayList<Movie> movies = controller.getAllMovies();

        if (movies.isEmpty()) {
            // Display a message if no movies are available
            JLabel noMoviesLabel = new JLabel("No movies available", SwingConstants.CENTER);
            noMoviesLabel.setForeground(Color.WHITE); // Text color
            moviePanel.add(noMoviesLabel);
        } else {
            // Loop through the list of movies and create a button for each
            for (Movie movie : movies) {
                // Create a panel to hold the movie content (image and title)
                JPanel movieContentPanel = new JPanel(new BorderLayout());
                movieContentPanel.setBackground(new Color(65, 65, 69)); // Match background color
                movieContentPanel.setPreferredSize(new Dimension(130, 230)); // Adjust as needed

                // Create a button for the movie image
                JButton movieButton = new JButton();
                movieButton.setPreferredSize(new Dimension(120, 180));
                movieButton.setContentAreaFilled(false);
                movieButton.setBorderPainted(false);
                movieButton.setFocusPainted(false);
                movieButton.setOpaque(false);

                // Construct file paths for the movie image
                String imageName = movie.getTitle().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                String imagePathJpg = "src/Boundary/Images/" + imageName + ".jpg";
                String imagePathJpeg = "src/Boundary/Images/" + imageName + ".jpeg";

                File imageFile = new File(imagePathJpg);
                if (!imageFile.exists()) {
                    imageFile = new File(imagePathJpeg);
                }

                // Set the button's image if the file exists
                if (imageFile.exists()) {
                    ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                    Image scaledImage = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);

                    movieButton.setIcon(new ImageIcon(scaledImage));

                    // Set hover effect
                    movieButton.setRolloverEnabled(true);
                    Image hoverImage = icon.getImage().getScaledInstance(130, 190, Image.SCALE_SMOOTH);
                    movieButton.setRolloverIcon(new ImageIcon(hoverImage));
                }

                // Add action listener to the button
                movieButton.addActionListener(e -> {
                    System.out.println("Selected Movie: " + movie.getTitle());
                    app.setSelectedMovie(movie);
                    app.switchToPage("TheatreSelection");
                });

                // Add the button (image) to the top of the panel
                movieContentPanel.add(movieButton, BorderLayout.CENTER);

                // Create a label for the movie title
                JLabel titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
                titleLabel.setForeground(Color.WHITE); // Text color
                titleLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Optional: Customize font
                titleLabel.setPreferredSize(new Dimension(130, 30)); // Ensure consistent space for titles

                // Add the title label below the button
                movieContentPanel.add(titleLabel, BorderLayout.SOUTH);

                // Add the movie content panel to the movie grid
                moviePanel.add(movieContentPanel);
            }
        }
    }
}