package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefaultView extends JFrame {

    public DefaultView() {
        // Set the title of the window
        setTitle("AcmePlex");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the window
        setSize(400, 300);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Add a label and text field for the username
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        userText.setPreferredSize(new Dimension(100, 20));
        panel.add(userLabel);
        panel.add(userText);

        // Add a label and password field for the password
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        passwordText.setPreferredSize(new Dimension(100, 20));
        panel.add(passwordLabel);
        panel.add(passwordText);

        // Add a submit button
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);

        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                // Handle the login logic here
                JOptionPane.showMessageDialog(null, "Username: " + username + "\nPassword: " + password);
            }
        });

        // Add the panel to the frame
        getContentPane().add(panel, BorderLayout.CENTER);

        // Center the window on the screen
        setLocationRelativeTo(null);
    }

    //public static void main(String[] args) {
    //    // Run the GUI code on the Event Dispatch Thread (EDT)
    //    SwingUtilities.invokeLater(new Runnable() {
    //        public void run() {
    //            // Create an instance of DefaultView and make it visible
    //            new DefaultView().setVisible(true);
    //        }
    //    });
    //}
}

/*
IDEAS on GUI layout: just going off what Ik is doable from the video I watch, down to workshop


 * Opens to movie browse menu, grid layout of all movies in movie table of DB, each associated with image
        *not entirely sure abt the association bit, would it instead be a bunch of JLabel objects that are somehow associated with DB key? 

 * movie panel/frame acts as button, opens new window with list of theatres playing said movie
 * click on desired theatre
 * open new window of list of showtimes
 * click on desired showtime
 * opens new window of seat map (blue seats are available, grey are not)
 * clicking on available seat changes colour to green and prints seat ID to screen
 * click some confirm button, opens window
        * login/enter info
            * if select login, open login window
            * if select enter info, enter info

 *  after either of these are entered, selected seats are updated to be marked as unavaialble in database
 *  can restart to browse?

    - might rework what we currently have entirely so that is uses CardLayout to create the sense of multiple pages
    - if so can ignore all the messy "open window"s above
 * 
 */