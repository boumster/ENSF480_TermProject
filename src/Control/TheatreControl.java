package src.Control;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import src.DB.Database;
import src.Entity.Auditorium;
import src.Entity.Movie;
import src.Entity.Theatre;

public class TheatreControl {

    private Database db;

    public TheatreControl() {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList<Theatre> getAllTheatres() {
        ArrayList<Theatre> theatres = new ArrayList<>();
        try {
            // Use the Database's getListMovies method
            theatres = Database.getListTheatres();
            for (Theatre theatre : theatres) {
                // For each movie, get the showtimes
                System.out.println("Movie: " + theatre.getName());
            }
        } catch (Exception e) {
            System.err.println("Error fetching theatres from database: " + e.getMessage());
            e.printStackTrace();
        }
        return theatres;
    }

    public static ArrayList<Auditorium> getAudAtTheatre(int theatre_id) {
        ArrayList<Auditorium> auditoriums = new ArrayList<>();
        String query = "SELECT * FROM auditorium WHERE theatre_id = ?";
        try {
            Database db = Database.getInstance();
            try (ResultSet rs = db.read(query, theatre_id)) {
                while (rs.next()) {
                    int auditoriumId = rs.getInt("auditorium_id");
                    int auditoriumCapacity = rs.getInt("capacity");
                    String theatreQuery = "SELECT * FROM theatre WHERE theatre_id = ?";
                    try {
                        Database db1 = Database.getInstance();
                        try (ResultSet rs1 = db1.read(theatreQuery, theatre_id)) {
                            while (rs1.next()) {
                                String theatre_name = rs1.getString("theatre_id");
                                Theatre theatre = new Theatre(theatre_name, auditoriumId);
                                Auditorium auditorium = new Auditorium(auditoriumId,auditoriumCapacity,theatre); 
                                auditoriums.add(auditorium);
                            
                            }
                        }

                        
                    }catch (SQLException e) {
                        e.printStackTrace(); 
                    }
                }
            }

        }catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
        }

        return auditoriums; // Return the list of auditoriums
    }

}
