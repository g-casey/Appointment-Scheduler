package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.DAO.ContactDAO;
import model.DAO.DAO;
import model.User;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class ModifyAppointmentScreenController implements Initializable {

    private DAO<Appointment> appointmentDAO;
    private User currentUser;
    private Appointment selectedAppointment;

    public ModifyAppointmentScreenController(DAO<Appointment> appointmentDAO, User currentUser, Appointment selectedAppointment) {
        this.appointmentDAO = appointmentDAO;
        this.currentUser = currentUser;
        this.selectedAppointment = selectedAppointment;
    }


    @FXML
    private TextField appointmentIdField;

    @FXML
    private ComboBox<Contact> contactField;

    @FXML
    private TextField customerIdField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField endTimeHourField;

    @FXML
    private TextField endTimeMinuteField;

    @FXML
    private TextField locationField;

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
        Stage window = ((Stage)titleField.getScene().getWindow());
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


            appointment = new Appointment(selectedAppointment.getId(),
                    titleField.getText(),
                    descriptionField.getText(),
                    locationField.getText(),
                    typeField.getText(),
                    Timestamp.from(startInstant),
                    Timestamp.from(endInstant),
                    selectedAppointment.getCreateDate(),
                    currentUser.getUserName(),
                    Timestamp.from(Instant.now()),
                    currentUser.getUserName(),
                    Integer.parseInt(customerIdField.getText()),
                    Integer.parseInt(userIdField.getText()),
                    contactField.getValue().getId()
            );


            if(ControllerHelper.checkAppointmentOverlap(appointment,true) && ControllerHelper.checkAppointmentTime(appointment.getStartTime(),appointment.getEndTime())){
                appointmentDAO.update(selectedAppointment.getId(),appointment);
                Stage window = (Stage) (titleField.getScene().getWindow());
                    window.close();
                }




        }catch (NullPointerException e){
            ControllerHelper.createErrorDialog("Please make sure to fill in every field!");
        }catch (NumberFormatException e){
            ControllerHelper.createErrorDialog("Please make sure to enter numbers in numeric fields!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactField.getItems().addAll(new ContactDAO().getAll());
        populateFields();
    }

    private void populateFields(){
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        typeField.setText(selectedAppointment.getType());
        Contact selectedContact = new ContactDAO().getAll().get(0);;

        for(Contact contact : new ContactDAO().getAll()){
            if(contact.getId() == selectedAppointment.getContactId()){
                selectedContact = contact;
            }
        }

        contactField.getSelectionModel().select(selectedContact);
        userIdField.setText(Integer.toString(selectedAppointment.getUserId()));
        appointmentIdField.setText(Integer.toString(selectedAppointment.getId()));
        customerIdField.setText(Integer.toString(selectedAppointment.getCustomerId()));
        LocalDate date = selectedAppointment.getStartTime().toLocalDateTime().toLocalDate();
        dateField.setValue(date);
        String startHours = Integer.toString(selectedAppointment.getStartTime().toLocalDateTime().getHour());
        startTimeHourField.setText(startHours);
        String startMinutes = Integer.toString(selectedAppointment.getStartTime().toLocalDateTime().getMinute());
        startTimeMinutesField.setText(startMinutes);
        String endHours = Integer.toString(selectedAppointment.getEndTime().toLocalDateTime().getHour());
        endTimeHourField.setText(endHours);
        String endMinutes = Integer.toString(selectedAppointment.getEndTime().toLocalDateTime().getMinute());
        endTimeMinuteField.setText(endMinutes);
    }
}
