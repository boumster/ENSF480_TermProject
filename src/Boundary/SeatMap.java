package src.Boundary;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.*;
import src.Entity.*;

public class SeatMap extends JPanel implements ActionListener {
    private JButton confirmButton;
    private Showtime showtime;
    private ArrayList<String> SelectedSeats;
    private JPanel seatPanel;
    private MovieTheatreApp app;
    public LocalDateTime dt_t = LocalDateTime.now();
    public int bookedCounter = 0;

    public SeatMap(MovieTheatreApp app, Showtime showtime) {
        // Set up the JFrame
        this.app = app;
        this.showtime = showtime;
        this.SelectedSeats = new ArrayList<String>();
        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(65,65,69));

        // Create the JLabels
        JLabel movieLabel = new JLabel("Movie: " + showtime.getMovie().getTitle());
        movieLabel.setBounds(50, 30, 700, 30);
        movieLabel.setForeground(Color.WHITE);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(movieLabel);

        JLabel screenLabel = new JLabel("Screen");
        screenLabel.setBounds(50,50,700,30);
        screenLabel.setForeground(Color.WHITE);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(screenLabel);

        JLabel auditoriumLabel = new JLabel("Auditorium: " + showtime.getAuditorium().getAudId());
        auditoriumLabel.setBounds(0, 0, 700, 300);
        auditoriumLabel.setForeground(Color.WHITE);
        auditoriumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(auditoriumLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Create the JPanel for seat buttons
        seatPanel = new JPanel();
        seatPanel.setBackground(new Color(103, 103, 110));
        add(seatPanel, BorderLayout.CENTER);

        updateSeatPanel();

        JPanel footerPanel = new JPanel();
        // Create the confirm button
        confirmButton = new JButton("Confirm Seat Selection");
        confirmButton.addActionListener(this);
        confirmButton.setFocusable(false);
        footerPanel.add(confirmButton);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(103, 103, 110));
        backButton.setFocusable(false);
        backButton.setForeground(Color.WHITE);
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
        Image scaledImg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        availableIcon = new ImageIcon(scaledImg);

        ImageIcon bookedIcon = new ImageIcon("src/Boundary/Images/chair-booked.png");
        img = bookedIcon.getImage();
        scaledImg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        bookedIcon = new ImageIcon(scaledImg);

        ImageIcon selectedIcon = new ImageIcon("src/Boundary/Images/chair-selected.png");
        img = selectedIcon.getImage();
        scaledImg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        selectedIcon = new ImageIcon(scaledImg);

        for (int i = 1; i <= seatCount; i++) {
            String seatNumber = String.valueOf(i);
            JButton seatButton = new JButton(seatNumber);
            seatButton.setIcon(availableIcon);
            seatButton.setContentAreaFilled(false);
            seatButton.setFocusPainted(false);
            seatButton.setBorderPainted(false);
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
                    bookedCounter += 1;
                    break;
                }

            }

            if(showtime.getShowtime().isAfter(dt_t.plusDays(10)) && bookedCounter >= 5){
                seatButton.setEnabled(false);
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
            Image scaledImg = img.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
            availableIcon = new ImageIcon(scaledImg);

            ImageIcon selectedIcon = new ImageIcon("src/Boundary/Images/chair-selected.png");
            img = selectedIcon.getImage();
            scaledImg = img.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
            selectedIcon = new ImageIcon(scaledImg);

            JButton clickedButton = (JButton) e.getSource(); // Identify the clicked button
            if (clickedButton.getBackground().equals(green)) {
                System.out.println("Seat " + clickedButton.getText() + " unpressed");
                clickedButton.setBackground(new Color(100, 149, 237)); // Change its color
                SelectedSeats.remove(clickedButton.getText());
                clickedButton.setIcon(availableIcon);
            } else {
                if (showtime.getShowtime().isAfter(dt_t.plusDays(10)) && (bookedCounter + SelectedSeats.size()) >= 5) {
                int allowedSeats = 5 - bookedCounter;
                JOptionPane.showMessageDialog(this, 
                    "You can only book " + allowedSeats + " more seat(s) for this early showtime.",
                    "Booking Limit Reached",
                    JOptionPane.WARNING_MESSAGE);
                } else {
                    SelectedSeats.add(clickedButton.getText());
                    clickedButton.setBackground(green);
                    clickedButton.setIcon(selectedIcon);
                }
            }
        }
    }
}
