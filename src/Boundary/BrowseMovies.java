package src.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseMovies extends JPanel {
    JButton movie1Button, movie2Button, movie3Button, movie4Button, movie5Button, movie6Button, movie7Button, movie8Button, movie9Button, movie10Button, movie11Button;
    
    public BrowseMovies(MovieTheatreApp app) {
        JLabel headerLabel = new JLabel("Browse Movies", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Optional: Set font and style
        headerLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Adjust header height
        add(headerLabel, BorderLayout.NORTH); // Add header at the top

        // Set the layout to GridLayout: 4 rows, 5 columns (can be adjusted)
        setLayout(new GridLayout(3, 5, 10, 10)); // 4 rows, 5 columns, with space between buttons

        // Movie 1
        movie1Button = new JButton("Oppenheimer");
        ImageIcon movie1Icon = new ImageIcon("src/Boundary/Images/Oppenheimer.jpg");
        Image movie1ScaledImage = movie1Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie1ScaledIcon = new ImageIcon(movie1ScaledImage);
        movie1Button.setIcon(movie1ScaledIcon); // Add movie image
        movie1Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie1Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie1Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie1Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie1Button);

        // Movie 2
        movie2Button = new JButton("Avengers: Endgame");
        ImageIcon movie2Icon = new ImageIcon("src/Boundary/Images/Endgame.jpg");
        Image movie2ScaledImage = movie2Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie2ScaledIcon = new ImageIcon(movie2ScaledImage);
        movie2Button.setIcon(movie2ScaledIcon); // Add movie image
        movie2Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie2Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie2Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie2Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie2Button);

        // Movie 3
        movie3Button = new JButton("Good Will Hunting");
        ImageIcon movie3Icon = new ImageIcon("src/Boundary/Images/WillHunting.jpg");
        Image movie3ScaledImage = movie3Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie3ScaledIcon = new ImageIcon(movie3ScaledImage);
        movie3Button.setIcon(movie3ScaledIcon); // Add movie image
        movie3Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie3Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie3Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie3Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie3Button);

        // Movie 4
        movie4Button = new JButton("Interstellar");
        ImageIcon movie4Icon = new ImageIcon("src/Boundary/Images/Interstellar.jpg");
        Image movie4ScaledImage = movie4Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie4ScaledIcon = new ImageIcon(movie4ScaledImage);
        movie4Button.setIcon(movie4ScaledIcon); // Add movie image
        movie4Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie4Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie4Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie4Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie4Button);

        // Movie 5
        movie5Button = new JButton("Harry Potter and The Deathly Hallows");
        ImageIcon movie5Icon = new ImageIcon("src/Boundary/Images/DeathlyHallows.jpg");
        Image movie5ScaledImage = movie5Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie5ScaledIcon = new ImageIcon(movie5ScaledImage);
        movie5Button.setIcon(movie5ScaledIcon); // Add movie image
        movie5Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie5Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie5Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie5Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie5Button);

        // Movie 6
        movie6Button = new JButton("Star Wars: Revenge of the Sith");
        ImageIcon movie6Icon = new ImageIcon("src/Boundary/Images/RevengeOfTheSith.jpg");
        Image movie6ScaledImage = movie6Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie6ScaledIcon = new ImageIcon(movie6ScaledImage);
        movie6Button.setIcon(movie6ScaledIcon); // Add movie image
        movie6Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie6Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie6Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie6Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie6Button);

        // Movie 7
        movie7Button = new JButton("Avengers: Infinity War");
        ImageIcon movie7Icon = new ImageIcon("src/Boundary/Images/InfinityWar.jpg");
        Image movie7ScaledImage = movie7Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie7ScaledIcon = new ImageIcon(movie7ScaledImage);
        movie7Button.setIcon(movie7ScaledIcon); // Add movie image
        movie7Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie7Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie7Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie7Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie7Button);

        // Movie 8
        movie8Button = new JButton("Spider-Man: No Way Home");
        ImageIcon movie8Icon = new ImageIcon("src/Boundary/Images/NoWayHome.jpg");
        Image movie8ScaledImage = movie8Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie8ScaledIcon = new ImageIcon(movie8ScaledImage);
        movie8Button.setIcon(movie8ScaledIcon); // Add movie image
        movie8Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie8Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie8Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie8Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie8Button);

        // Movie 9
        movie9Button = new JButton("How To Train Your Dragon");
        ImageIcon movie9Icon = new ImageIcon("src/Boundary/Images/HTTYD.jpg");
        Image movie9ScaledImage = movie9Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie9ScaledIcon = new ImageIcon(movie9ScaledImage);
        movie9Button.setIcon(movie9ScaledIcon); // Add movie image
        movie9Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie9Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie9Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie9Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie9Button);

        // Movie 10
        movie10Button = new JButton("Wolf of Wall Street");
        ImageIcon movie10Icon = new ImageIcon("src/Boundary/Images/WolfOfWallStreet.jpg");
        Image movie10ScaledImage = movie10Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie10ScaledIcon = new ImageIcon(movie10ScaledImage);
        movie10Button.setIcon(movie10ScaledIcon); // Add movie image
        movie10Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie10Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie10Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie10Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie10Button);

        // Movie 11
        movie11Button = new JButton("Attack on Titan: The Last Attack");
        ImageIcon movie11Icon = new ImageIcon("src/Boundary/Images/AOT.jpg");
        Image movie11ScaledImage = movie11Icon.getImage().getScaledInstance(120, 200, Image.SCALE_SMOOTH);
        ImageIcon movie11ScaledIcon = new ImageIcon(movie11ScaledImage);
        movie11Button.setIcon(movie11ScaledIcon); // Add movie image
        movie11Button.setHorizontalTextPosition(SwingConstants.CENTER); // Text below the image
        movie11Button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        movie11Button.setPreferredSize(new Dimension(120, 250)); // Adjust the size to fit image and text
        movie11Button.addActionListener(e -> app.switchToPage("TheatreSelection"));
        add(movie11Button);
    }
}