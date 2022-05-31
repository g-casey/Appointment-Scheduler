package model;


import javafx.collections.ObservableList;
import model.DAO.AppointmentDAO;

import java.time.Month;

/**
 * Number Report
 * @author Gavin Casey
 */
public class NumberReport {
    /**
     * The Month.
     */
    Month month;
    /**
     * The Count.
     */
    int count;
    /**
     * The Type.
     */
    String type;

    /**
     * Gets month.
     *
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Instantiates a new Number report.
     *
     * @param month the month
     * @param type  the type
     */
    public NumberReport(Month month, String type){
        this.month = month;
        this.type = type;
        updateCount();
    }

    /**
     * Updates the count of Appointments per a specific type
     */
    private void updateCount(){
        ObservableList<Appointment> appointmentList = new AppointmentDAO().getAll();
        for(Appointment appointment : appointmentList){
            Month apptMonth = appointment.getStartTime().toLocalDateTime().getMonth();
            if(month == apptMonth && type.equals(appointment.getType())){
                count++;
            }
        }
    }
}
