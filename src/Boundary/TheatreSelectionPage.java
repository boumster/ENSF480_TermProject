package src.Boundary;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import src.Control.ShowtimeControl;
import src.Control.TheatreControl;
import src.Entity.DateTime;
import src.Entity.Movie;
import src.Entity.Showtime;
import src.Entity.Theatre;


public class TheatreSelectionPage extends JPanel {
    private Movie selectedMovie; 
    private boolean isRegistered;
    private LocalDate selectedDate;
    private JPanel leftPanel;

    public TheatreSelectionPage(MovieTheatreApp app, Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
        
        if (app.getCurrentUser() != null) {
            this.isRegistered = app.getCurrentUser().getIsRegisteredUser();
        } else this.isRegistered = false;

        // Set BorderLayout for main panel
        setLayout(new BorderLayout());

        // Header Panel (North)
        JLabel headerLabel = new JLabel("Showtimes for " + selectedMovie.getTitle(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Main Content Panel (Center)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Left Panel: Theatre names and showtimes
        leftPanel = new JPanel();
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
            LocalDate dt = LocalDate.now();

            ArrayList<Showtime> showtimes = showtimeControl.getShowtimesForMovieForTheatreAndDate(selectedMovie, theatre, dt);

            if (showtimes.isEmpty()) {
                JLabel noShowtimesLabel = new JLabel("No showtimes available.");
                noShowtimesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                leftPanel.add(noShowtimesLabel);
            } else {
                JPanel showtimesPanel = new JPanel();
                showtimesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                showtimesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                for (Showtime showtime : showtimes) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d, h:mm a");

                    // Format the LocalDateTime
                    String formattedDateTime = showtime.getShowtime().format(formatter);

                    // Create the button with the formatted date and time
                    JButton showtimeButton = new JButton(formattedDateTime);


                    showtimeButton.addActionListener(e ->{
                        app.setSelectedShowtime(showtime);
                        app.switchToPage("SeatMap");
                    });
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

        // Date Dropdown Menu
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JComboBox<String> dateDropdown = new JComboBox<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d");

        LocalDate today = LocalDate.now();
        int dayLimit = isRegistered ? 20 : 10;

        for (int i = 0; i <= dayLimit; i++) {
            LocalDate date = today.plusDays(i);
            dateDropdown.addItem(date.format(dateFormatter));
        }

        dateDropdown.addActionListener(e -> {
            selectedDate = today.plusDays(dateDropdown.getSelectedIndex());
            leftPanel.removeAll();
            for (Theatre theatre : theatres){
                updateShowtimes(app, selectedMovie, theatre, selectedDate);
            }
            
        });

        dropdownPanel.add(new JLabel("Select Date:"));
        dropdownPanel.add(dateDropdown);
        contentPanel.add(dropdownPanel, BorderLayout.NORTH);

        // Back Button (South)
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.switchToPage("BrowseMovies"));
        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);

        
    }

    private void updateShowtimes(MovieTheatreApp app, Movie selectedMovie, Theatre selectedTheatre, LocalDate selectedDate) {
    
        ShowtimeControl showtimeControl = new ShowtimeControl();
        ArrayList<Showtime> showtimes = showtimeControl.getShowtimesForMovieForTheatreAndDate(selectedMovie, selectedTheatre, selectedDate);
    
        if (showtimes.isEmpty()) {
            leftPanel.add(new JLabel("No showtimes available for " + selectedMovie.getTitle() + " on " + selectedDate));
        } else {
            // Populate the left panel with showtimes
            for (Showtime showtime : showtimes) {
                // Format the showtime (e.g., "Mar 5, 7:30 PM")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d, h:mm a");
                String formattedDateTime = showtime.getShowtime().format(formatter);
                JButton showtimeButton = new JButton(formattedDateTime);
                
                // Add listener to handle showtime selection
                showtimeButton.addActionListener(e -> {
                    app.setSelectedShowtime(showtime);
                    app.switchToPage("SeatMap");
                });
    
                leftPanel.add(showtimeButton);
            }
        }
    
        // Force layout updates
        leftPanel.revalidate();
        leftPanel.repaint();
    }
    
}
