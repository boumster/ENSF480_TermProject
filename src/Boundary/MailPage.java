package src.Boundary;

import src.Entity.Mail;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import src.Entity.RegUser;
import src.Control.AdminControl;

public class MailPage extends JPanel {
    private MovieTheatreApp app;
    private JList<String> mailList;
    private DefaultListModel<String> mailListModel;
    private ArrayList<Mail> userMails;  // Store the list of mails for the current user
    private AdminControl adminControl;

    public MailPage(MovieTheatreApp app) {
        this.app = app;
        this.adminControl = new AdminControl();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Your Mailbox");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(headerLabel);

        add(Box.createVerticalStrut(20)); // Add space

        // Add mail list panel
        mailListModel = new DefaultListModel<>();
        mailList = new JList<>(mailListModel);
        mailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mailList.setVisibleRowCount(10);  // Display a maximum of 10 mails at a time

        // Wrap the JList in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(mailList);
        add(scrollPane);

        // Add view mail button
        JButton viewMailButton = new JButton("View Selected Mail");
        viewMailButton.setAlignmentX(CENTER_ALIGNMENT);
        viewMailButton.addActionListener(e -> showMailDetail(mailList.getSelectedIndex()));
        add(Box.createVerticalStrut(20)); // Add space before button
        add(viewMailButton);

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> app.switchToPage("Home")); // Go back to home page
        add(Box.createVerticalStrut(20)); // Add space before button
        add(backButton);

        // Fetch user's mails when the page is loaded
        refreshMails(); // This will clear the list and load the mails
    }

    // Method to refresh mails when the page is opened or when an action occurs
    public void refreshMails() {
        System.out.println("Refreshing mails..."); // Debugging line

        RegUser currentUser = app.getCurrentUser(); // Access current user
        if (currentUser != null) {
            // Fetch the user's mails
            userMails = getUserMails(currentUser.getUserID());  // Store fetched mails in userMails
            System.out.println("Fetched " + userMails.size() + " mails for user ID: " + currentUser.getUserID());

            // Clear previous mails and add new mails to the list model
            mailListModel.clear();  // Clear the existing mail list before adding new mails
            for (Mail mail : userMails) {
                String mailSummary = "Time: " + mail.getTime() + " | Message: " + mail.getMessage();
                // Check if the mail is already in the list to avoid duplicates
                boolean exists = false;
                for (int i = 0; i < mailListModel.size(); i++) {
                    if (mailListModel.get(i).equals(mailSummary)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    mailListModel.addElement(mailSummary);  // Only add unique mails
                }
            }
        } else {
            // If no user is logged in, show a message
            mailListModel.addElement("Please log in to view your mailbox.");
        }
    }

    // Method to fetch mails for a specific user
    private ArrayList<Mail> getUserMails(int userId) {
        ArrayList<Mail> userSpecificMails = new ArrayList<>();
        System.out.println("Fetching mails for user ID: " + userId); // Debugging line

        // Filter mails based on user ID
        for (Mail mail : adminControl.getUsersMail(userId)) {
            if (mail.getUser() != null && mail.getUser().getUserID() == userId) {
                userSpecificMails.add(mail);
            }
        }

        System.out.println("Found " + userSpecificMails.size() + " mails for user ID: " + userId); // Debugging line
        return userSpecificMails;
    }

    // Show detailed information about the selected mail
    private void showMailDetail(int selectedIndex) {
        if (selectedIndex != -1 && selectedIndex < userMails.size()) {
            // Access the selected mail from the userMails list
            Mail selectedMail = userMails.get(selectedIndex);
            JOptionPane.showMessageDialog(this,
                    "\nMessage: " + selectedMail.getMessage() +
                    "\nTime: " + selectedMail.getTime(),
                    "Mail Details",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method to trigger mail refresh after actions like booking or refunding
    public void triggerMailUpdate() {
        refreshMails();  // Refresh the mail list after an update
    }
}
