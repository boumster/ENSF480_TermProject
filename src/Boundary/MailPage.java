package src.Boundary;

import src.Entity.Mail;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import src.Entity.RegUser;
import src.Control.AdminControl;

public class MailPage extends JPanel {
    private MovieTheatreApp app;
    private JList<String> mailList;
    private DefaultListModel<String> mailListModel;
    private ArrayList<Mail> userMails; // Store the list of mails for the current user
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
        mailList.setVisibleRowCount(10); // Display a maximum of 10 mails at a time

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
        backButton.addActionListener(e -> app.switchToPage("Home"));
        add(Box.createVerticalStrut(20));
        add(backButton);

        refreshMails();
    }

    public void refreshMails() {
        System.out.println("Refreshing mails..."); // Debugging line

        RegUser currentUser = app.getCurrentUser();
        if (currentUser != null) {
            userMails = getUserMails(currentUser.getUserID()); // store fetched mails in userMails
            System.out.println("Fetched " + userMails.size() + " mails for user ID: " + currentUser.getUserID());

            mailListModel.clear();
            for (Mail mail : userMails) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d, h:mm a");
                String formattedDateTime = mail.getTime().format(formatter);
                String mailSummary = "Time: " + formattedDateTime + " | Message: " + mail.getMessage();
                boolean exists = false;
                for (int i = 0; i < mailListModel.size(); i++) {
                    if (mailListModel.get(i).equals(mailSummary)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    mailListModel.addElement(mailSummary); // Only add unique mails
                }
            }
        } else {
            mailListModel.addElement("Please log in to view your mailbox.");
        }
    }

    // fetch mails for specified user
    private ArrayList<Mail> getUserMails(int userId) {
        ArrayList<Mail> userSpecificMails = new ArrayList<>();
        System.out.println("Fetching mails for user ID: " + userId); // Debugging line

        for (Mail mail : adminControl.getUsersMail(userId)) {
            if (mail.getUser() != null && mail.getUser().getUserID() == userId) {
                userSpecificMails.add(mail);
            }
        }

        System.out.println("Found " + userSpecificMails.size() + " mails for user ID: " + userId); // Debugging line
        return userSpecificMails;
    }

    private void showMailDetail(int selectedIndex) {
        if (selectedIndex != -1 && selectedIndex < userMails.size()) {
            System.out.println("Showing mail details for index: " + selectedIndex); // Debugging line
            Mail selectedMail = userMails.get(selectedIndex);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d, h:mm a");
            String formattedDateTime = selectedMail.getTime().format(formatter);
            JOptionPane.showMessageDialog(this,
                    "\nMessage: " + selectedMail.getMessage() +
                            "\nTime: " + formattedDateTime,
                    "Mail Details",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method to trigger mail refresh after actions like booking or refunding
    public void triggerMailUpdate() {
        refreshMails();
    }
}
