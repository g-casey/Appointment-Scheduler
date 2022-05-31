package model.DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database Access Object for Appointments
 * @author Gavin Casey
 */

public class AppointmentDAO implements DAO<Appointment> {
    /**
     * List containing all the appointments
     */
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    /**
     * Database connection object
     */
    private Connection connection = DBConnection.getConnection();

    /**
     * Gets all of the appointments from the database
     */
    public AppointmentDAO(){
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments;");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Appointment appointment = new Appointment(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8),
                        rs.getString(9),
                        rs.getTimestamp(10),
                        rs.getString(11),
                        rs.getInt(12),
                        rs.getInt(13),
                        rs.getInt(14)
                );
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(appointmentList.get(8).getStartTime());
    }

    /**
     * Gets one appointment from the list
     * @param id index of the appointment
     * @return appointment
     */
    @Override
    public Appointment get(int id) {
        return appointmentList.get(id);
    }

    /**
     * Gets the appointment list
     * @return the list of appointments
     */
    @Override
    public ObservableList<Appointment> getAll() {
        return appointmentList;
    }

    /**
     * Creates a new appointment and adds it to the database
     * @param appointment to add
     */
    @Override
    public void create(Appointment appointment) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments( Title," +
                    "Description," +
                    "Location," +
                    "Type," +
                    "Start," +
                    "End," +
                    "Create_Date," +
                    "Created_By," +
                    "Last_Update," +
                    "Last_Updated_By," +
                    "Customer_ID," +
                    "User_ID,Contact_ID)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);"
            );
            statement.setString(1,appointment.getTitle());
            statement.setString(2,appointment.getDescription());
            statement.setString(3,appointment.getLocation());
            statement.setString(4,appointment.getType());
            statement.setTimestamp(5,appointment.getStartTime());
            statement.setTimestamp(6,appointment.getEndTime());
            statement.setTimestamp(7,appointment.getCreateDate());
            statement.setString(8,appointment.getCreatedBy());
            statement.setTimestamp(9,appointment.getLastUpdate());
            statement.setString(10,appointment.getLastUpdatedBy());
            statement.setInt(11,appointment.getCustomerId());
            statement.setInt(12,appointment.getUserId());
            statement.setInt(13,appointment.getContactId());

            statement.executeUpdate();



            ResultSet rs = connection.prepareStatement("SELECT Appointment_ID FROM appointments ORDER BY Appointment_ID DESC LIMIT 1;").executeQuery();

            rs.next();
            appointment.setId(rs.getInt("Appointment_ID"));

            appointmentList.add(appointment);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates existing item in the database
     * @param index of the appointment to update
     * @param appointment new appointment object
     */

    @Override
    public void update(int index, Appointment appointment) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE appointments SET Title =?," +
                    "Description =?," +
                    "Location=?," +
                    "Type=?," +
                    "Start=?," +
                    "End=?," +
                    "Create_Date=?," +
                    "Created_By=?," +
                    "Last_Update=?," +
                    "Last_Updated_By=?," +
                    "Customer_ID=?," +
                    "User_ID=?,Contact_ID=? WHERE Appointment_ID=?");

            statement.setString(1,appointment.getTitle());
            statement.setString(2,appointment.getDescription());
            statement.setString(3,appointment.getLocation());
            statement.setString(4,appointment.getType());
            statement.setTimestamp(5,appointment.getStartTime());
            statement.setTimestamp(6,appointment.getEndTime());
            statement.setTimestamp(7,appointment.getCreateDate());
            statement.setString(8,appointment.getCreatedBy());
            statement.setTimestamp(9,appointment.getLastUpdate());
            statement.setString(10,appointment.getLastUpdatedBy());
            statement.setInt(11,appointment.getCustomerId());
            statement.setInt(12,appointment.getUserId());
            statement.setInt(13,appointment.getContactId());
            statement.setInt(14,index);

            statement.executeUpdate();

            for(int i = 0; i < appointmentList.size(); i++){
                if(appointmentList.get(i).getId() == index){
                    appointmentList.set(i,appointment);
                }
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Removes appointment from the database
     * @param appointment to remove
     */
    @Override
    public void delete(Appointment appointment) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?; ");
            statement.setInt(1,appointment.getId());
            statement.executeUpdate();

            appointmentList.remove(appointment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
