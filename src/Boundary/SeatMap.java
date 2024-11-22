package src.Boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.DB.Database;

public class SeatMap extends JFrame implements ActionListener {
    JButton confirmButton;
    Database db;
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
    public SeatMap() {
        // Set up the JFrame
        setTitle("Seat Map");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null); // We'll use absolute positioning sparingly here

        // Create the JLabel
        JLabel label = new JLabel("Acmeplex Cinemas");
        label.setBounds(150, 10, 200, 30); // Set position and size
        label.setForeground(Color.WHITE); // Make text visible on black background
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial",Font.BOLD, 20));
        add(label);

        // Create the JPanel for seat buttons
        JPanel panel = new JPanel();
        panel.setBounds(50, 50, 500, 400); // Set position and size
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new GridLayout(5, 10,2,2)); // GridLayout for seat arrangement

        // Add seat buttons to the panel
        for (int i = 1; i <= 50; i++) {
            String seatNumber = String.valueOf(i);
            JButton seatButton = new JButton(seatNumber); // Create a new button for each seat
            seatButton.setFocusable(false);
            seatButton.setBackground(Color.BLUE);
            seatButton.setForeground(Color.WHITE);
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
            // Initialize the database
            Database db = new Database();
            new SeatMap();
            
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
