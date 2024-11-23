package src.Boundary;
import javax.swing.*;
import java.awt.*;

public class MovieTheatreApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MovieTheatreApp(){
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
