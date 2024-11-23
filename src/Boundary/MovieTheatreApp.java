package src.Boundary;
import javax.swing.*;

import src.Entity.RegUser;

import java.awt.*;

public class MovieTheatreApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private RegUser currentUser;

    public MovieTheatreApp(){
        currentUser = null;
        frame = new JFrame("Movie Theatre Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1200);

        //CardLayout and Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        //Add different pages
        cardPanel.add(new HomePage(this), "Home");
        cardPanel.add(new BrowseMovies(this), "BrowseMovies"); 
        cardPanel.add(new TheatreSelectionPage(this), "TheatreSelection");
        cardPanel.add(new ShowtimesPage(this), "Showtimes");
        cardPanel.add(new BookingPage(this), "Booking");
        //cardPanel.add(new SeatMap(this), "SeatMap");
        cardPanel.add(new ConfirmationPage(this), "Confirmation");
        cardPanel.add(new LoginPage(this, "USER", currentUser), "Login");
        cardPanel.add(new ConfirmationPage(this), "Guest");
        cardPanel.add(new ConfirmationPage(this), "AdminLogin");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    public void switchToPage(String pageName){
        cardLayout.show(cardPanel, pageName);
    }

    public static void main(String[] args){
        new MovieTheatreApp();
    }
}
