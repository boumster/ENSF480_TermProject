package src.Boundary;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.DB.Database;
import src.Entity.*;

public class SeatMap extends JFrame implements ActionListener {
    private JButton confirmButton;
    private Database db;
    private Showtime showtime;
 //to do: 
/*
 * show auditorium number somehow, 
 * make unavailable seats gray and disable the button for them
 * 
 * when you click/remove a seat put it into database for corresponding customer, figure out logic for multiple seats being selected
 * when you click continue button put a pop up confirming seat was selected.
 * 
 * 
 */
    public SeatMap(Showtime showtime, Database db) {
        // Set up the JFrame
        this.showtime = showtime;
        this.db = db;

        setTitle("Seat Map");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null); 

        // Create the JLabels
        JLabel movieLabel = new JLabel("Movie: " + showtime.getMovie().getTitle());
        movieLabel.setBounds(50,30,700,30);
        movieLabel.setForeground(Color.WHITE);
        movieLabel.setFont(new Font("Arial",Font.BOLD,16));
        add(movieLabel);

        JLabel auditoriumLabel = new JLabel("Auditorium: "+ showtime.getAuditorium().getAudId());
        auditoriumLabel.setBounds(50,50,700,30);
        auditoriumLabel.setForeground(Color.WHITE);
        auditoriumLabel.setFont(new Font("Arial",Font.PLAIN,14));
        add(auditoriumLabel);


        JLabel label = new JLabel("Acmeplex Cinemas");
        label.setBounds(150, 10, 200, 30); 
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial",Font.BOLD, 20));
        add(label);

        // Create the JPanel for seat buttons
        JPanel panel = new JPanel();
        panel.setBounds(50, 90, 600, 500); // Set position and size
        panel.setBackground(Color.LIGHT_GRAY);
        

        // Add seat buttons to the panel
        int seatCount= showtime.getAuditorium().getNumSeats();
        panel.setLayout(new GridLayout((int) Math.ceil(seatCount/10.0),10,5,5));

        for (int i = 1; i <= seatCount; i++) {
            String seatNumber = String.valueOf(i);
            JButton seatButton = new JButton(seatNumber); // Create a new button for each seat
            seatButton.setFocusable(false);
            seatButton.setBackground(Color.BLUE);
            seatButton.setForeground(Color.WHITE);
            ArrayList<Seat> seatarray = showtime.getAuditorium().getSeatArray();
            for (Seat seat : seatarray){
                if (seat.getStatus() && String.valueOf(seat.getSeatNumber()).equals(seatNumber)){
                    seatButton.setBackground(Color.GRAY);
                    seatButton.setEnabled(false);
                }
            }
            seatButton.setFont(new Font("Arial",Font.BOLD, 10));
            seatButton.addActionListener(this); // Attach the action listener
            panel.add(seatButton);
        }

        add(panel);

        // Create the confirm button
        confirmButton = new JButton("Confirm Seat Selection");
        confirmButton.setBounds(250, 500, 200, 50); // Set position and size
        confirmButton.addActionListener(this);
        confirmButton.setFocusable(false);
        add(confirmButton);

        // Make the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            System.out.println("Confirm Button Pressed");
        } else if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource(); // Identify the clicked button
            if(clickedButton.getBackground() == Color.GRAY){
                System.out.println("Seat "+ clickedButton.getText() + " unpressed");
                clickedButton.setBackground(Color.BLUE); // Change its color
                

            }
            else{
            System.out.println("Seat " + clickedButton.getText() + " Pressed");
            clickedButton.setBackground(Color.GRAY); // Change its color
            }
        }
    }

    public static void main(String[] args) {
         try {
            Database db = Database.getInstance();
            Theatre theatre = new Theatre("Test", 44);

            Auditorium auditorium = new Auditorium(1,50,theatre);
            Movie movie = new Movie("Interstellar","R", "A team of explorers travel through a wormhole in space.", "Sci-Fi", 123);
            Showtime showtime = new Showtime(LocalDateTime.now(), auditorium, movie);

            auditorium.bookSeat(10);
            auditorium.bookSeat(11);
            auditorium.bookSeat(12);
            auditorium.bookSeat(13);
            auditorium.bookSeat(14);
            auditorium.bookSeat(15);
            ArrayList<Seat> seats = auditorium.getSeatArray();
            for (Seat seat : seats) {
                System.out.println("Seat " + seat.getSeatNumber() + " status: " + (seat.getStatus() ? "Booked" : "Available"));
            }

            new SeatMap(showtime,db);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
