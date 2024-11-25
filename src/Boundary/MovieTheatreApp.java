package src.Boundary;
import java.awt.*;
import javax.swing.*;
import src.Entity.Movie;
import src.Entity.RegUser;
import src.Entity.Showtime;

public class MovieTheatreApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private RegUser currentUser = null;
    private HomePage homePage;
    private Movie selectedMovie;
    private ViewTicketsPage viewTicketsPage;
    private Showtime selectedShowtime;

    public MovieTheatreApp(){
        frame = new JFrame("Movie Theatre Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the window full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true); // removes border and title and controls, but cause you cant easily close window

        //CardLayout and Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        //Add different pages
        homePage = new HomePage(this);
        viewTicketsPage = new ViewTicketsPage(this);
        cardPanel.add(homePage, "Home");
        cardPanel.add(new BrowseMovies(this), "BrowseMovies"); 
        //cardPanel.add(new TheatreSelectionPage(this, selectedMovie), "TheatreSelection");
        cardPanel.add(new ShowtimesPage(this), "Showtimes");
        cardPanel.add(new BookingPage(this), "Booking");
        //cardPanel.add(new SeatMap(this), "SeatMap");
        cardPanel.add(new ConfirmationPage(this), "Confirmation");
        cardPanel.add(new LoginPage(this, "USER"), "Login");
        cardPanel.add(new LoginPage(this, "ADMIN"), "AdminLogin");
        cardPanel.add(new RegisterPage(this), "Register");
        cardPanel.add(new PaymentPage(this, "FEE"), "FeePayment");
        cardPanel.add(new PaymentPage(this, "TICKET"), "TicketPayment");
        cardPanel.add(viewTicketsPage, "ViewTickets");


        frame.add(cardPanel);
        frame.setVisible(true);
    }

    public void switchToPage(String pageName){
        if (pageName.equals("TheatreSelection")) {
            cardPanel.add(new TheatreSelectionPage(this, selectedMovie), "TheatreSelection");
        } 
        if (pageName.equals("Home")){
            homePage.refresh();
        } if (pageName.equals("ViewTickets")){
            viewTicketsPage.refreshTickets();
        }
        if (pageName.equals("SeatMap")){
            cardPanel.add(new SeatMap(this, selectedShowtime), "SeatMap");
        }
        cardLayout.show(cardPanel, pageName);
    }

    public void setCurrentUser(RegUser user) {
        this.currentUser = user;
        homePage.refresh();
    }

    public RegUser getCurrentUser() {
        return currentUser;
    }

    public void setSelectedMovie(Movie movie){
        this.selectedMovie = movie;
    }

    public Movie getSelectedMovie() {
            return selectedMovie;
        }

    public void setSelectedShowtime(Showtime showtime) {
        this.selectedShowtime = showtime;
    }
    
    public Showtime getSelectedShowtime() {
        return selectedShowtime;
    }
    public static void main(String[] args) {
        new MovieTheatreApp();
    }
}