package src.Control;

import java.awt.image.DataBuffer;
import src.DB.Database;
import src.Entity.Auditorium;
import src.Entity.Theatre;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuditoriumControl {
    private Database db;

    public AuditoriumControl() {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

    public boolean addAuditorium(Theatre theatre, int capacity){
        if(theatre == null || capacity <= 0){
            System.out.println("Invalid theatre or capacity");
            return false;
        }
        try {
                String query = "INSERT INTO auditorium (theare_id, capacity) VALUES (?,?)";
                int newAudID = db.create(query, theatre.getId(), capacity);
                if (newAudID > 0){
                    Auditorium auditorium = new Auditorium(newAudID,capacity,theatre);
                    theatre.addAuditorium(auditorium);
                    System.out.println("Auditorium added successfully  with ID: " + newAudID);
                    return true;
                }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAuditoriumCapacity(Auditorium auditorium, int newCapacity){
        if(auditorium == null || newCapacity <=0){
            System.out.println("Invalid Auditorium or capacity");
            return false;
        }
        try{
            String query = "UPDATE auditorium SET capacity = ? Where auditorium_id = ?";
            int rowsAffected = db.update(query,newCapacity,auditorium.getAudId());
            if(rowsAffected >0){
                auditorium.numSeats = newCapacity;
                auditorium.numSeatsRemaining = newCapacity;
                auditorium.initSeats();
                System.out.println("Auditorium Capacity Updated Succesfully");
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    return false;
    }
    
    public boolean deleteAuditorium(Auditorium auditorium){
        if(auditorium == null){
            System.out.println("invalid Auditorium");
            return false;
            }
        try {
            String query = "DELETE FROM auditorium WHERE auditorium_id = ?";
            int rowsAffected = Database.getInstance().delete(query,auditorium.getAudId());
            if(rowsAffected >0){
                Theatre theatre = auditorium.getTheatre();
                theatre.getAuditoriums().remove(auditorium);
                System.out.println("Auditorium Deleted Successfully");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        }

        public ArrayList<Auditorium> getAuditoriumsForTheatre(Theatre theatre){
            return theatre.getAuditoriums();
        }
    }
