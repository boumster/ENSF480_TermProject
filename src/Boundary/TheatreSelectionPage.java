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

        // Set vertical BoxLayout for vertical alignment of components
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (this.selectedMovie == null) {
            JLabel errorLabel = new JLabel("No movie selected. Please go back and select a movie.");
            errorLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(errorLabel);
            return;
        }

        JLabel label = new JLabel("Showtimes for " + selectedMovie.getTitle());
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10)); // Add spacing
        add(label);

        ShowtimeControl showtimeControl = new ShowtimeControl();

        // Fetch all theatres
        ArrayList<Theatre> theatres = TheatreControl.getAllTheatres();

        for (Theatre theatre : theatres) {
            ArrayList<Showtime> showtimes = showtimeControl.getShowtimesForMovieForTheatre(selectedMovie, theatre);

            // Add theatre name
            JLabel theatreLabel = new JLabel(theatre.getName());
            theatreLabel.setFont(new Font("Arial", Font.BOLD, 18));
            theatreLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(Box.createVerticalStrut(10)); // Add spacing
            add(theatreLabel);

            if (showtimes.isEmpty()) {
                JLabel noShowtimesLabel = new JLabel("No showtimes available.");
                noShowtimesLabel.setAlignmentX(CENTER_ALIGNMENT);
                add(noShowtimesLabel);
            } else {
                for (Showtime showtime : showtimes) {
                    JLabel showtimeLabel = new JLabel(showtime.getShowtime().toString());
                    showtimeLabel.setAlignmentX(CENTER_ALIGNMENT);
                    add(showtimeLabel);
                }
            }
            add(Box.createVerticalStrut(20)); // Add space after each theatre section
        }

        // Add the "Back" button to return to the movie selection screen
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> app.switchToPage("BrowseMovies"));
        add(Box.createVerticalStrut(20)); // Add spacing above the button
        add(backButton);
    }
}
