package src.Boundary;
import javax.swing.*;

import src.Entity.RegUser;

import java.awt.*;

public class MovieTheatreApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private RegUser currentUser = null;
    private HomePage homePage;

    public MovieTheatreApp(){
        frame = new JFrame("Movie Theatre Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1200);

        //CardLayout and Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        //Add different pages
        homePage = new HomePage(this);
        cardPanel.add(homePage, "Home");
        cardPanel.add(new BrowseMovies(this), "BrowseMovies"); 
        cardPanel.add(new TheatreSelectionPage(this), "TheatreSelection");
        cardPanel.add(new ShowtimesPage(this), "Showtimes");
        cardPanel.add(new BookingPage(this), "Booking");
        //cardPanel.add(new SeatMap(this), "SeatMap");
        cardPanel.add(new ConfirmationPage(this), "Confirmation");
        cardPanel.add(new LoginPage(this, "USER"), "Login");
        cardPanel.add(new LoginPage(this, "ADMIN"), "AdminLogin");
        cardPanel.add(new RegisterPage(this), "Register");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    public void switchToPage(String pageName){
        cardLayout.show(cardPanel, pageName);
    }

    public void setCurrentUser(RegUser user) {
        this.currentUser = user;
        homePage.refresh();
    }

    public RegUser getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        new MovieTheatreApp();
    }
}