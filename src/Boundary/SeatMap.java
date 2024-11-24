package src.Boundary;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.*;
import src.DB.Database;
import src.Entity.*;
import src.Boundary.CartPage;

public class SeatMap extends JFrame implements ActionListener {
    private JButton confirmButton;
    private Database db;
    private Showtime showtime;
    private ArrayList<String> SelectedSeats;
    private JPanel seatPanel;

    // to do:
    /*
     * 
     * when you click/remove a seat put it into database for corresponding customer,
     * figure out logic for multiple seats being selected
     * when you click continue button put a pop up confirming seat was selected.
     * 
     * 
     */
    public SeatMap(Showtime showtime, Database db) {
        // Set up the JFrame
        this.showtime = showtime;
        this.db = db;
        this.SelectedSeats = new ArrayList<String>();

        setTitle("Seat Map");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        // Create the JLabels
        JLabel movieLabel = new JLabel("Movie: " + showtime.getMovie().getTitle());
        movieLabel.setBounds(50, 30, 700, 30);
        movieLabel.setForeground(Color.WHITE);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(movieLabel);

        JLabel auditoriumLabel = new JLabel("Auditorium: " + showtime.getAuditorium().getAudId());
        auditoriumLabel.setBounds(50, 50, 700, 30);
        auditoriumLabel.setForeground(Color.WHITE);
        auditoriumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(auditoriumLabel);

        JLabel label = new JLabel("Acmeplex Cinemas");
        label.setBounds(150, 10, 200, 30);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label);

        // Create the JPanel for seat buttons
        seatPanel = new JPanel();
        seatPanel.setBounds(50, 90, 600, 500); // Set position and size
        seatPanel.setBackground(Color.LIGHT_GRAY);

        updateSeatPanel();

        add(seatPanel);

        // Create the confirm button
        confirmButton = new JButton("Confirm Seat Selection");
        confirmButton.setBounds(250, 600, 200, 50); // Set position and size
        confirmButton.addActionListener(this);
        confirmButton.setFocusable(false);
        add(confirmButton);

        // Make the frame visible
        setVisible(true);
    }

    private void updateSeatPanel() {
        seatPanel.removeAll();

        int seatCount = showtime.getAuditorium().getNumSeats();
        seatPanel.setLayout(new GridLayout((int) Math.ceil(seatCount / 10.0), 10, 5, 5));

        // Load the chair images
        ImageIcon availableIcon = new ImageIcon("src/Boundary/Images/chair-available.png");
        Image img = availableIcon.getImage();
        Image scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
        availableIcon = new ImageIcon(scaledImg);

        ImageIcon bookedIcon = new ImageIcon("src/Boundary/Images/chair-booked.png");
        img = bookedIcon.getImage();
        scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
        bookedIcon = new ImageIcon(scaledImg);

        ImageIcon selectedIcon = new ImageIcon("src/Boundary/Images/chair-selected.png");
        img = selectedIcon.getImage();
        scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
        selectedIcon = new ImageIcon(scaledImg);

        for (int i = 1; i <= seatCount; i++) {
            String seatNumber = String.valueOf(i);
            JButton seatButton = new JButton(seatNumber); // Create a new button for each seat with text
            seatButton.setFocusable(false);
            seatButton.setBackground(new Color(100, 149, 237));
            seatButton.setToolTipText(seatNumber); // Set the seat number as a tooltip
            seatButton.setFont(new Font("Arial", Font.BOLD, 10));

            // Adjust text position
            seatButton.setHorizontalTextPosition(SwingConstants.CENTER);
            seatButton.setVerticalTextPosition(SwingConstants.BOTTOM);

            ArrayList<Seat> seatArray = showtime.getAuditorium().getSeatArray();
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

            seatButton.addActionListener(this); // Attach the action listener
            seatPanel.add(seatButton);
        }

        // Refresh the panel to reflect changes
        seatPanel.revalidate();
        seatPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            System.out.println("Confirm Button Pressed");
            for (String x : SelectedSeats) {
                System.out.println(x + "Selected");
                showtime.getAuditorium().bookSeat(Integer.parseInt(x));
            }
            updateSeatPanel();

            CartPage cartpage = new CartPage(showtime, SelectedSeats);
            SelectedSeats.clear();
            cartpage.setVisible(true);
            this.dispose();
        } else if (e.getSource() instanceof JButton) {
            // Load the chair images
            ImageIcon availableIcon = new ImageIcon("src/Boundary/Images/chair-available.png");
            Image img = availableIcon.getImage();
            Image scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            availableIcon = new ImageIcon(scaledImg);

            ImageIcon selectedIcon = new ImageIcon("src/Boundary/Images/chair-selected.png");
            img = selectedIcon.getImage();
            scaledImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
            selectedIcon = new ImageIcon(scaledImg);

            JButton clickedButton = (JButton) e.getSource(); // Identify the clicked button
            if (clickedButton.getBackground() == Color.GRAY) {
                System.out.println("Seat " + clickedButton.getText() + " unpressed");
                clickedButton.setBackground(Color.BLUE); // Change its color
                SelectedSeats.remove(clickedButton.getText());
                clickedButton.setIcon(availableIcon);

            } else {
                System.out.println("Seat " + clickedButton.getText() + " Pressed");
                clickedButton.setBackground(Color.GREEN); // Change its color
                SelectedSeats.add(clickedButton.getText());
                clickedButton.setIcon(selectedIcon);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Database db = Database.getInstance();
            Theatre theatre = new Theatre("test", 1);
            Auditorium auditorium = new Auditorium(1, 50, theatre);
            Movie movie = new Movie(1, "Interstellar", "R", "A team of explorers travel through a wormhole in space.",
                    "Sci-Fi", 123);
            
            Showtime showtime = new Showtime(LocalDateTime.now(), auditorium, movie);

            auditorium.bookSeat(10);
            auditorium.bookSeat(11);
            auditorium.bookSeat(12);
            auditorium.bookSeat(13);
            auditorium.bookSeat(14);
            auditorium.bookSeat(15);
            ArrayList<Seat> seats = auditorium.getSeatArray();
            for (Seat seat : seats) {
                System.out.println(
                        "Seat " + seat.getSeatNumber() + " status: " + (seat.getStatus() ? "Booked" : "Available"));
            }

            new SeatMap(showtime, db);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
