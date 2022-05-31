package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import model.DAO.*;


import static controller.ControllerHelper.countryComboBoxChange;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointmentScreenController implements Initializable {

    private DAO<Appointment> appointmentDAO;
    private User currentUser;
    private Customer selectedCustomer;

    public AddAppointmentScreenController(DAO<Appointment> appointmentDAO, User currentUser, Customer selectedCustomer) {
        this.appointmentDAO = appointmentDAO;
        this.currentUser = currentUser;
        this.selectedCustomer = selectedCustomer;
    }

    @FXML
    private ComboBox<Contact> contactField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField endTimeHourField;

    @FXML
    private TextField endTimeMinuteField;

    @FXML
    private TextField startTimeHourField;

    @FXML
    private TextField startTimeMinutesField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField userIdField;



    @FXML
    void cancelButtonClicked(MouseEvent event) {
        Stage window = (Stage) (typeField.getScene().getWindow());
        window.close();
    }

    @FXML
    void saveButtonClicked(MouseEvent event) {
        Appointment appointment;
        try{
            ZonedDateTime date = dateField.getValue().atStartOfDay(ZoneId.systemDefault());

            ZonedDateTime startDateTime = date.plusHours(Integer.parseInt(startTimeHourField.getText()));
            startDateTime = startDateTime.plusMinutes(Integer.parseInt(startTimeMinutesField.getText()));
            Instant startInstant = startDateTime.toInstant();

            ZonedDateTime endDateTime = date.plusHours(Integer.parseInt(endTimeHourField.getText()));
            endDateTime = endDateTime.plusMinutes(Integer.parseInt(endTimeMinuteField.getText()));
            Instant endInstant = endDateTime.toInstant();


            appointment = new Appointment(0,
                    titleField.getText(),
                    descriptionField.getText(),
                    locationField.getText(),
                    typeField.getText(),
                    Timestamp.from(startInstant),
                    Timestamp.from(endInstant),
                    Timestamp.from(Instant.now()),
                    currentUser.getUserName(),
                    Timestamp.from(Instant.now()),
                    currentUser.getUserName(),
                    Integer.parseInt(customerIdField.getText()),
                    Integer.parseInt(userIdField.getText()),
                    contactField.getValue().getId()
            );

            if(ControllerHelper.checkAppointmentOverlap(appointment,false) && ControllerHelper.checkAppointmentTime(appointment.getStartTime(),appointment.getEndTime())){
                appointmentDAO.create(appointment);
                Stage window = (Stage) (titleField.getScene().getWindow());
                window.close();
            }

        }catch (NullPointerException e){
            System.out.println(e);
            ControllerHelper.createErrorDialog("Please make sure to fill in every field!");
        }catch (NumberFormatException e){
            ControllerHelper.createErrorDialog("Please make sure to enter numbers in numeric fields!");
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdField.setText(Integer.toString(currentUser.getId()));
        customerIdField.setText(Integer.toString(selectedCustomer.getId()));


        ObservableList<Contact> contactList = new ContactDAO().getAll();

        contactField.getItems().addAll(contactList);

    }
}
