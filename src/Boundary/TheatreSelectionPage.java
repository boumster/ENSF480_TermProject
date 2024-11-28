package src.Boundary;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import src.Control.ShowtimeControl;
import src.Control.TheatreControl;
import src.Entity.DateTime;
import src.Entity.Movie;
import src.Entity.Showtime;
import src.Entity.Theatre;
import src.Entity.Seat;


public class TheatreSelectionPage extends JPanel {
    private Movie selectedMovie; 
    private boolean isRegistered;
    private LocalDate selectedDate;
    private JPanel leftPanel;

    public LocalDateTime dt_t = LocalDateTime.now();

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
                    String formattedDateTime = showtime.getShowtime().format(formatter);
                
                    // create button
                    JButton showtimeButton = new JButton(formattedDateTime);
                    // switch page if not disabled
                    showtimeButton.addActionListener(e -> {
                        app.setSelectedShowtime(showtime);
                        if(app.getCurrentUser() != null){
                            app.switchToPage("SeatMap");
                        } else {
                            app.switchToPage("Login");
                        }
                    });
                
                    
                    // early booking sold-out logic
                    if(showtime.getShowtime().isAfter(dt_t.plusDays(10))){
                        if (showtime.getPercentOccupied() >= 10) {
                            showtimeButton.setText(formattedDateTime + " - EARLY BOOKING SOLD OUT");
                            showtimeButton.setEnabled(false);
                        }
                    }
                
                    // general sold-out logic
                    else if (showtime.getPercentOccupied() >= 100) {
                        showtimeButton.setText(formattedDateTime + " - SHOWING SOLD OUT");
                        showtimeButton.setEnabled(false);
                    }
                
                    
                
                    showtimesPanel.add(showtimeButton);
                    
                }

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
            updateShowtimes(app, selectedMovie, selectedDate);
            
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

    private void updateShowtimes(MovieTheatreApp app, Movie selectedMovie, LocalDate selectedDate) {
        System.out.println("Updating showtimes...");
        System.out.println("Selected Movie: " + selectedMovie.getTitle());
        System.out.println("Selected Date: " + selectedDate);
    
        // Clear previous content
        System.out.println("Cleared leftPanel.");
    
        ShowtimeControl showtimeControl = new ShowtimeControl();
        ArrayList<Theatre> theatres = TheatreControl.getAllTheatres();
    
        for (Theatre theatre : theatres) {
            System.out.println("Processing theatre: " + theatre.getName());
    
            // Add the theatre name
            JLabel theatreLabel = new JLabel(theatre.getName());
            theatreLabel.setFont(new Font("Arial", Font.BOLD, 18));
            theatreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            //leftPanel.add(Box.createVerticalStrut(10)); // Add spacing before theatre name
            leftPanel.add(theatreLabel);
    
            // Get showtimes for the current theatre
            ArrayList<Showtime> showtimes = showtimeControl.getShowtimesForMovieForTheatreAndDate(selectedMovie, theatre, selectedDate);
    
            if (showtimes.isEmpty()) {
                JLabel noShowtimesLabel = new JLabel("No showtimes available.");
                noShowtimesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                leftPanel.add(noShowtimesLabel);
                System.out.println("No showtimes found for theatre: " + theatre.getName());
            } else {
                // Panel to hold showtime buttons
                JPanel showtimesPanel = new JPanel();
                showtimesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                showtimesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
                for (Showtime showtime : showtimes) {
                    // Format the showtime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d, h:mm a");
                    String formattedDateTime = showtime.getShowtime().format(formatter);
                    System.out.println("Adding button for showtime: " + formattedDateTime);
    
                    // Create button for each showtime
                    JButton showtimeButton = new JButton(formattedDateTime);
                    // switch page if not disabled
                    showtimeButton.addActionListener(e -> {
                        app.setSelectedShowtime(showtime);
                        if(app.getCurrentUser() != null){
                            app.switchToPage("SeatMap");
                        } else {
                            app.switchToPage("Login");
                        }
                    });
                
                    
                    // early booking sold-out logic
                    if(showtime.getShowtime().isAfter(dt_t.plusDays(10))){
                        if (showtime.getPercentOccupied() >= 9.9) {
                            showtimeButton.setText(formattedDateTime + " - EARLY BOOKING SOLD OUT");
                            showtimeButton.setEnabled(false);
                        }
                    }
                
                    // general sold-out logic
                    else if (showtime.getPercentOccupied() >= 99.9) {
                        showtimeButton.setText(formattedDateTime + " - SHOWING SOLD OUT");
                        showtimeButton.setEnabled(false);
                    }
                    showtimesPanel.add(showtimeButton);
                    System.out.println("PERCENT OCCUPIED: " + showtime.getPercentOccupied());
                }
                
    
                leftPanel.add(showtimesPanel); // Add the panel to the left panel
            }
    
            // Add spacing after each theatre section
            leftPanel.add(Box.createVerticalStrut(20));
        }
    
        // Update the UI
        System.out.println("Revalidating and repainting leftPanel...");
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    
}
