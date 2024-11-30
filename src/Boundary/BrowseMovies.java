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



/* 
        // Movie 1
        movie1Button = new JButton("Oppenheimer");
        ImageIcon movie1Icon = new ImageIcon("src/Boundary/Images/Oppenheimer.jpg");
        Image movie1ScaledImage = movie1Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie1ScaledIcon = new ImageIcon(movie1ScaledImage);
        movie1Button.setIcon(movie1ScaledIcon); // Add movie image
        movie1Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie1Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie1Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie1Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie1Button);

        // Movie 2
        movie2Button = new JButton("Avengers: Endgame");
        ImageIcon movie2Icon = new ImageIcon("src/Boundary/Images/Endgame.jpg");
        Image movie2ScaledImage = movie2Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie2ScaledIcon = new ImageIcon(movie2ScaledImage);
        movie2Button.setIcon(movie2ScaledIcon); // Add movie image
        movie2Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie2Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie2Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie2Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie2Button);

        // Movie 3
        movie3Button = new JButton("Good Will Hunting");
        ImageIcon movie3Icon = new ImageIcon("src/Boundary/Images/WillHunting.jpg");
        Image movie3ScaledImage = movie3Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie3ScaledIcon = new ImageIcon(movie3ScaledImage);
        movie3Button.setIcon(movie3ScaledIcon); // Add movie image
        movie3Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie3Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie3Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie3Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie3Button);

        // Movie 4
        movie4Button = new JButton("Interstellar");
        ImageIcon movie4Icon = new ImageIcon("src/Boundary/Images/Interstellar.jpg");
        Image movie4ScaledImage = movie4Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie4ScaledIcon = new ImageIcon(movie4ScaledImage);
        movie4Button.setIcon(movie4ScaledIcon); // Add movie image
        movie4Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie4Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie4Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie4Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie4Button);

        // Movie 5
        movie5Button = new JButton("Harry Potter and The Deathly Hallows");
        ImageIcon movie5Icon = new ImageIcon("src/Boundary/Images/DeathlyHallows.jpg");
        Image movie5ScaledImage = movie5Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie5ScaledIcon = new ImageIcon(movie5ScaledImage);
        movie5Button.setIcon(movie5ScaledIcon); // Add movie image
        movie5Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie5Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie5Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie5Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie5Button);

        // Movie 6
        movie6Button = new JButton("Star Wars: Revenge of the Sith");
        ImageIcon movie6Icon = new ImageIcon("src/Boundary/Images/RevengeOfTheSith.jpg");
        Image movie6ScaledImage = movie6Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie6ScaledIcon = new ImageIcon(movie6ScaledImage);
        movie6Button.setIcon(movie6ScaledIcon); // Add movie image
        movie6Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie6Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie6Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie6Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie6Button);

        // Movie 7
        movie7Button = new JButton("Avengers: Infinity War");
        ImageIcon movie7Icon = new ImageIcon("src/Boundary/Images/InfinityWar.jpg");
        Image movie7ScaledImage = movie7Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie7ScaledIcon = new ImageIcon(movie7ScaledImage);
        movie7Button.setIcon(movie7ScaledIcon); // Add movie image
        movie7Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie7Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie7Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie7Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie7Button);

        // Movie 8
        movie8Button = new JButton("Spider-Man: No Way Home");
        ImageIcon movie8Icon = new ImageIcon("src/Boundary/Images/NoWayHome.jpg");
        Image movie8ScaledImage = movie8Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie8ScaledIcon = new ImageIcon(movie8ScaledImage);
        movie8Button.setIcon(movie8ScaledIcon); // Add movie image
        movie8Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie8Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie8Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie8Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie8Button);

        // Movie 9
        movie9Button = new JButton("How To Train Your Dragon");
        ImageIcon movie9Icon = new ImageIcon("src/Boundary/Images/HTTYD.jpg");
        Image movie9ScaledImage = movie9Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie9ScaledIcon = new ImageIcon(movie9ScaledImage);
        movie9Button.setIcon(movie9ScaledIcon); // Add movie image
        movie9Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie9Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie9Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie9Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie9Button);

        // Movie 10
        movie10Button = new JButton("Wolf of Wall Street");
        ImageIcon movie10Icon = new ImageIcon("src/Boundary/Images/WolfOfWallStreet.jpg");
        Image movie10ScaledImage = movie10Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie10ScaledIcon = new ImageIcon(movie10ScaledImage);
        movie10Button.setIcon(movie10ScaledIcon); // Add movie image
        movie10Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie10Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie10Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie10Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie10Button);

        // Movie 11
        movie11Button = new JButton("Attack on Titan: The Last Attack");
        ImageIcon movie11Icon = new ImageIcon("src/Boundary/Images/AOT.jpg");
        Image movie11ScaledImage = movie11Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie11ScaledIcon = new ImageIcon(movie11ScaledImage);
        movie11Button.setIcon(movie11ScaledIcon); // Add movie image
        movie11Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie11Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie11Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie11Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie11Button);
    }
}

*/