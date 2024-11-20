package src.Boundary;
import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MovieTheatreApp(){
        frame = new JFrame("Movie Theatre Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        //CardLayout and Panel
        cardLayout = new Card Layout();
        cardPanel = new JPanel(cardLayout);
        
    }
}
