package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import src.Control.movieController;
import src.Entity.Movie;

public class BrowseMovies extends JPanel {
    private movieController controller;
    //JButton movie1Button, movie2Button, movie3Button, movie4Button, movie5Button, movie6Button, movie7Button, movie8Button, movie9Button, movie10Button, movie11Button;
    


    public BrowseMovies(MovieTheatreApp app) {
        this.controller = new movieController();

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Browse Movies", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Adjust header height

        // back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 40)); // Optional: Set size for the button
        backButton.addActionListener(e -> app.switchToPage("Home"));

        // panel to hold the back button and add it to the top-right
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Right alignment for the button
        backButtonPanel.add(backButton);

        // Add the header LABEL  and back button to the header PANEL
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(backButtonPanel, BorderLayout.EAST);

        // Add the header panel to the top of the main panel
        add(headerPanel, BorderLayout.NORTH);

        // Movie grid
        JPanel moviePanel = new JPanel(new GridLayout(4, 5, 10, 10)); // Dynamic grid with 5 columns
        add(moviePanel, BorderLayout.CENTER);

        //Fetch movies from database
        ArrayList<Movie> movies = controller.getAllMovies();

        if(movies.isEmpty()){
            JLabel noMoviesJLabel = new JLabel("No movies available", SwingConstants.CENTER);
            moviePanel.add(noMoviesJLabel);
        }else{
            for (Movie movie : movies) {
                // Create a panel to hold the movie content (image and title)
                JPanel movieContentPanel = new JPanel();
                movieContentPanel.setLayout(new BorderLayout());
                movieContentPanel.setPreferredSize(new Dimension(130, 230)); // Adjust as needed
                movieContentPanel.setOpaque(false); // Ensure transparent background
                
                // Create a button for the movie image
                JButton movieButton = new JButton();
                movieButton.setPreferredSize(new Dimension(120, 180));
                movieButton.setContentAreaFilled(false);
                movieButton.setBorderPainted(false);
                movieButton.setFocusPainted(false);
                movieButton.setOpaque(false);
            
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
            
                // Add action listener
                movieButton.addActionListener(e -> {
                    System.out.println("Selected Movie: " + movie.getTitle());
                    app.setSelectedMovie(movie);
                    app.switchToPage("TheatreSelection");
                });
            
                // Add the button (image) to the top of the panel
                movieContentPanel.add(movieButton, BorderLayout.CENTER);
            
                // Create a label for the title
                JLabel titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Optional: Customize font
                titleLabel.setPreferredSize(new Dimension(130, 30)); // Ensure consistent space for titles
            
                // Add the title below the image
                movieContentPanel.add(titleLabel, BorderLayout.SOUTH);
            
                // Add the panel to the movie grid
                moviePanel.add(movieContentPanel);
            }
            
            
        }
    }
}