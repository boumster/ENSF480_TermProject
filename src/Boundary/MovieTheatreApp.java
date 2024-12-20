package src.Boundary;

import java.awt.*;
import java.util.ArrayList;
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
    private MailPage mailPage;
    private Showtime selectedShowtime;
    private ArrayList<String> selectedSeats;

    public MovieTheatreApp() {
        frame = new JFrame("Movie Theatre Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the window full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // CardLayout and Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add different pages
        homePage = new HomePage(this);
        viewTicketsPage = new ViewTicketsPage(this);
        mailPage = new MailPage(this);
        cardPanel.add(homePage, "Home");
        cardPanel.add(new AdminPage(this), "AdminHome");
        cardPanel.add(new BrowseMovies(this), "BrowseMovies");
        cardPanel.add(new LoginPage(this, "USER"), "Login");
        cardPanel.add(new LoginPage(this, "ADMIN"), "AdminLogin");
        cardPanel.add(new AdminPage(this), "AdminPage");
        cardPanel.add(new RegisterPage(this), "Register");
        cardPanel.add(new PaymentPage(this), "FeePayment");
        cardPanel.add(viewTicketsPage, "ViewTickets");
        cardPanel.add(mailPage, "MailPage");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    public void switchToPage(String pageName) {
        if (pageName.equals("TheatreSelection")) {
            cardPanel.add(new TheatreSelectionPage(this, selectedMovie), "TheatreSelection");
        }
        if (pageName.equals("Home")) {
            homePage.refresh();
        }
        if (pageName.equals("ViewTickets")) {
            viewTicketsPage.refreshTickets();
        } else if (pageName.equals("MailPage")) {
            mailPage.refreshMails();
        }
        if (pageName.equals("SeatMap")) {
            cardPanel.add(new SeatMap(this, selectedShowtime), "SeatMap");
        }
        if (pageName.equals("TicketPayment")) {
            cardPanel.add(new PaymentPage(this, selectedSeats), "TicketPayment");
        }
        if (pageName.equals("AdminPage")) {

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

    public void setSelectedMovie(Movie movie) {
        System.out.println("Selected movie: in MovieApp " + movie.getTitle());
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

    public void setSelectedSeats(ArrayList<String> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public ArrayList<String> getSelectedSeats() {
        return selectedSeats;
    }

    public static void main(String[] args) {
        new MovieTheatreApp();
    }
}
