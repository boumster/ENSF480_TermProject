package src.Boundary;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import src.Entity.*;

public class SeatMap extends JPanel implements ActionListener {
    private JButton confirmButton;
    private Showtime showtime;
    private ArrayList<String> SelectedSeats;
    private JPanel seatPanel;
    private MovieTheatreApp app;

    // to do:
    /*
     * 
     * when you click/remove a seat put it into database for corresponding customer,
     * figure out logic for multiple seats being selected
     * when you click continue button put a pop up confirming seat was selected.
     * 
     * 
     */
    public SeatMap(MovieTheatreApp app, Showtime showtime) {
        // Set up the JFrame
        this.app = app;
        this.showtime = showtime;
        this.SelectedSeats = new ArrayList<String>();

        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(Color.BLACK);

        // Create the JLabels
        JLabel movieLabel = new JLabel("Movie: " + showtime.getMovie().getTitle());
        movieLabel.setBounds(50, 30, 700, 30);
        movieLabel.setForeground(Color.WHITE);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(movieLabel);

        JLabel auditoriumLabel = new JLabel("Auditorium: " + showtime.getAuditorium().getAudId());
        auditoriumLabel.setBounds(50, 50, 700, 30);
        auditoriumLabel.setForeground(Color.WHITE);
        auditoriumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(auditoriumLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Create the JPanel for seat buttons
        seatPanel = new JPanel();
        seatPanel.setBackground(Color.LIGHT_GRAY);
        add(seatPanel, BorderLayout.CENTER);

        updateSeatPanel();

        JPanel footerPanel = new JPanel();
        // Create the confirm button
        confirmButton = new JButton("Confirm Seat Selection");
        confirmButton.addActionListener(this);
        confirmButton.setFocusable(false);
        footerPanel.add(confirmButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            app.switchToPage("TheatreSelection");
        });
        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void updateSeatPanel() {
        seatPanel.removeAll();

        int seatCount = showtime.getAuditorium().getNumSeats();
        seatPanel.setLayout(new GridLayout((int) Math.ceil(seatCount / 10.0), 10, 5, 5));

        ImageIcon availableIcon = new ImageIcon("src/Boundary/Images/chair-available.png");
        Image img = availableIcon.getImage();
        Image scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        availableIcon = new ImageIcon(scaledImg);

        ImageIcon bookedIcon = new ImageIcon("src/Boundary/Images/chair-booked.png");
        img = bookedIcon.getImage();
        scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        bookedIcon = new ImageIcon(scaledImg);

        ImageIcon selectedIcon = new ImageIcon("src/Boundary/Images/chair-selected.png");
        img = selectedIcon.getImage();
        scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        selectedIcon = new ImageIcon(scaledImg);

        for (int i = 1; i <= seatCount; i++) {
            String seatNumber = String.valueOf(i);
            JButton seatButton = new JButton(seatNumber);
            seatButton.setBackground(new Color(100, 149, 237));
            seatButton.setFocusable(false);
            seatButton.setFont(new Font("Arial", Font.BOLD, 10));

            ArrayList<Seat> seatArray = showtime.getSeats();
            boolean isBooked = false;
            for (Seat seat : seatArray) {
                if (seat.getStatus() && String.valueOf(seat.getSeatNumber()).equals(seatNumber)) {
                    seatButton.setBackground(Color.GRAY);
                    seatButton.setEnabled(false);
                    seatButton.setIcon(bookedIcon);
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) {
                seatButton.setIcon(availableIcon);
            }

            seatButton.addActionListener(this);
            seatPanel.add(seatButton);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            app.setSelectedShowtime(showtime);
            app.setSelectedSeats(SelectedSeats);
            if(SelectedSeats.size() == 0){
                JOptionPane.showMessageDialog(this, "No Seats Selected");
            } else {
                app.switchToPage("TicketPayment");
            }
            
        } else if (e.getSource() instanceof JButton) {
            // Load the chair images
            Color green = new Color(144, 238, 144);
            ImageIcon availableIcon = new ImageIcon("src/Boundary/Images/chair-available.png");
            Image img = availableIcon.getImage();
            Image scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            availableIcon = new ImageIcon(scaledImg);

            ImageIcon selectedIcon = new ImageIcon("src/Boundary/Images/chair-selected.png");
            img = selectedIcon.getImage();
            scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            selectedIcon = new ImageIcon(scaledImg);

            JButton clickedButton = (JButton) e.getSource(); // Identify the clicked button
            if (clickedButton.getBackground().equals(green)) {
                System.out.println("Seat " + clickedButton.getText() + " unpressed");
                clickedButton.setBackground(new Color(100, 149, 237)); // Change its color
                SelectedSeats.remove(clickedButton.getText());
                clickedButton.setIcon(availableIcon);
            } else {
                SelectedSeats.add(clickedButton.getText());
                clickedButton.setBackground(green);
                clickedButton.setIcon(selectedIcon);
            }
        }
    }
}
/*
 * public static void main(String[] args) {
 * try {
 * Database db = Database.getInstance();
 * Theatre theatre = new Theatre("test", 1);
 * Auditorium auditorium = new Auditorium(1, 50, theatre);
 * Movie movie = new Movie(1, "Interstellar", "R",
 * "A team of explorers travel through a wormhole in space.",
 * "Sci-Fi", 123);
 * 
 * Showtime showtime = new Showtime(1, LocalDateTime.now(), auditorium, movie);
 * 
 * showtime.bookSeat(10);
 * showtime.bookSeat(11);
 * showtime.bookSeat(12);
 * showtime.bookSeat(13);
 * showtime.bookSeat(14);
 * showtime.bookSeat(15);
 * ArrayList<Seat> seats = showtime.getSeats();
 * for (Seat seat : seats) {
 * System.out.println(
 * "Seat " + seat.getSeatNumber() + " status: " + (seat.getStatus() ? "Booked" :
 * "Available"));
 * }
 * 
 * new SeatMap(showtime, db);
 * db.close();
 * } catch (SQLException e) {
 * e.printStackTrace();
 * }
 * }
 * 
 * }
 */