package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import src.Control.ShowtimeControl;
import src.Control.TheatreControl;
import src.Entity.Movie;
import src.Entity.Showtime;
import src.Entity.Theatre;

public class TheatreSelectionPage extends JPanel {
    private Movie selectedMovie; // Movie selected in browse page

    public TheatreSelectionPage(MovieTheatreApp app, Movie selectedMovie) {
        this.selectedMovie = selectedMovie;

        // Set BorderLayout for main panel
        setLayout(new BorderLayout());

        // Header Panel (North)
        JLabel headerLabel = new JLabel("Showtimes for " + selectedMovie.getTitle(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Main Content Panel (Center)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Left Panel: Theatre names and showtimes
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(leftPanel); // Add scroll for long lists
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Right Panel: Movie details
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(rightPanel, BorderLayout.EAST);

        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);

        // Populate right panel with movie details
        if (this.selectedMovie != null) {
            JLabel descriptionLabel = new JLabel("<html><b>Description:</b> " + selectedMovie.getDesc() + "</html>");
            JLabel genreLabel = new JLabel("<html><b>Genre:</b> " + selectedMovie.getGenre() + "</html>");
            JLabel ratingLabel = new JLabel("<html><b>Rating:</b> " + selectedMovie.getRating() + "</html>");
            JLabel durationLabel = new JLabel("<html><b>Duration:</b> " + selectedMovie.getDuration() + " mins</html>");

            descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            genreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            durationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            rightPanel.add(descriptionLabel);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(genreLabel);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(ratingLabel);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(durationLabel);
        }

        // Fetch all theatres and their showtimes
        ShowtimeControl showtimeControl = new ShowtimeControl();
        ArrayList<Theatre> theatres = TheatreControl.getAllTheatres();

        for (Theatre theatre : theatres) {
            // Theatre name
            JLabel theatreLabel = new JLabel(theatre.getName());
            theatreLabel.setFont(new Font("Arial", Font.BOLD, 18));
            theatreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(theatreLabel);

            ArrayList<Showtime> showtimes = showtimeControl.getShowtimesForMovieForTheatre(selectedMovie, theatre);

            if (showtimes.isEmpty()) {
                JLabel noShowtimesLabel = new JLabel("No showtimes available.");
                noShowtimesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                leftPanel.add(noShowtimesLabel);
            } else {
                JPanel showtimesPanel = new JPanel();
                showtimesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                showtimesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                for (Showtime showtime : showtimes) {
                    JButton showtimeButton = new JButton(showtime.getShowtime().toString());
                    showtimeButton.addActionListener(e -> app.switchToPage("Home"));
                    showtimesPanel.add(showtimeButton);
                }

                // Do not constrain size; let layout managers handle it
                leftPanel.add(showtimesPanel);
            }

            leftPanel.add(Box.createVerticalStrut(20)); // Add space after each theatre section
        }

        // Force layout updates
        leftPanel.revalidate();
        leftPanel.repaint();

        // Back Button (South)
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.switchToPage("BrowseMovies"));
        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }
}
