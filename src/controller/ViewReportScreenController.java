package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import model.DAO.ContactDAO;
import model.DAO.DAO;
import model.DAO.UserDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Controller for the View Report Screen
 * @author Gavin Casey
 */
public class ViewReportScreenController implements Initializable {

    private DAO<Appointment> appointmentDAO;

    /**
     * Instantiates a new View report screen controller.
     *
     * @param appointmentDAO the appointment dao
     */
    public ViewReportScreenController(DAO<Appointment> appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @FXML
    private TableColumn<Appointment, Integer> contactColumn1;

    @FXML
    private TableColumn<Appointment, String> contactColumn2;

    @FXML
    private TableColumn<Appointment, String> contactColumn3;

    @FXML
    private TableColumn<Appointment, String> contactColumn4;

    @FXML
    private TableColumn<Appointment, Timestamp> contactColumn5;

    @FXML
    private TableColumn<Appointment, Timestamp> contactColumn6;

    @FXML
    private TableColumn<Appointment, Integer> contactColumn7;

    @FXML
    private ComboBox<Contact> contactDropdown;

    @FXML
    private RadioButton contactSortButton;


    @FXML
    private DatePicker loginDatePicker;

    @FXML
    private RadioButton loginSortButton;

    @FXML
    private TableColumn<LoginAttempt, String> loginColumn1;

    @FXML
    private TableColumn<LoginAttempt, Timestamp> loginColumn2;

    @FXML
    private TableView<LoginAttempt> loginTable;


    @FXML
    private TableView<NumberReport> reportTable;

    @FXML
    private TableView<Appointment> reportTable2;

    @FXML
    private TableColumn<NumberReport,String> typeColumn1;

    @FXML
    private TableColumn<NumberReport,Integer> typeColumn2;

    @FXML
    private ComboBox<Month> typeMonthDropdown;

    @FXML
    private RadioButton typeSortButton;

    /**
     * Runs When the Type DatPicker selection changes
     *
     * Used a lambda expression to simplify extracting a list of all Types from a list of all Appointments.
     *
     * @param event the event
     */
    @FXML
    void typeMonthDropdownChanged(ActionEvent event) {
        ObservableList<Appointment> appointmentList = appointmentDAO.getAll();
        ObservableList<NumberReport> numReportList = FXCollections.observableArrayList();
        if(typeSortButton.isSelected()){
            List<String> typeList = appointmentList.stream().map(appointment -> appointment.getType()).collect(Collectors.toList());
            typeList = typeList.stream().distinct().collect(Collectors.toList());

            for(String type : typeList){
                numReportList.add(new NumberReport(typeMonthDropdown.getValue(),type));
            }
        }
        reportTable.setItems(numReportList);
    }

    /**
     * Type sort selected.
     *
     * @param event the event
     */
    @FXML
    void typeSortSelected(MouseEvent event) {
        contactSortButton.setSelected(false);
        loginSortButton.setSelected(false);
        reportTable.setVisible(true);
        reportTable2.setVisible(false);
        loginTable.setVisible(false);
        loginDatePicker.getEditor().clear();
        contactDropdown.getSelectionModel().clearSelection();
    }

    /**
     * Contact sort button clicked.
     *
     * @param event the event
     */
    @FXML
    void contactSortButtonClicked(MouseEvent event) {
        typeSortButton.setSelected(false);
        loginSortButton.setSelected(false);
        typeMonthDropdown.getSelectionModel().clearSelection();
        loginDatePicker.getEditor().clear();
        reportTable.setVisible(false);
        reportTable2.setVisible(true);
        loginTable.setVisible(false);
    }

    /**
     * Runs When the Login DateSelector is changed
     *
     * @param event the event
     */
    @FXML
    void loginDateChanged(ActionEvent event) {
        if(loginSortButton.isSelected()) {
            loginTable.setItems(getLoginAttempts());
        }
    }

    /**
     * Runs when the Login Sort Button is clicked
     *
     * @param event the event
     */
    @FXML
    void loginSortButtonClicked(MouseEvent event) {
        contactSortButton.setSelected(false);
        typeSortButton.setSelected(false);
        typeMonthDropdown.getSelectionModel().clearSelection();
        contactDropdown.getSelectionModel().clearSelection();
        reportTable2.setVisible(false);
        reportTable.setVisible(false);
        loginTable.setVisible(true);
    }


    /**
     * Runs when the Contact Dropdown menu is changed
     *
     * Used a Lambda expression to simplify looping through the list of appointments and adding appointments containing the selected contact to the filtered list.
     *
     * @param event the event
     */
    @FXML
    void contactDropdownChanged(ActionEvent event) {
        ObservableList<Appointment> appointmentList = appointmentDAO.getAll();
        ObservableList<Appointment> filteredAppointmentList = FXCollections.observableArrayList();
        if(contactSortButton.isSelected()){
            appointmentList.forEach(appointment -> {
                if(appointment.getContactId() == contactDropdown.getValue().getId()){
                    filteredAppointmentList.add(appointment);
                }
            });
        }
        reportTable2.setItems(filteredAppointmentList);
    }

    /**
     * Gets list of all login attempts from the log file
     * @return list of LoginAttempts
     */
    private ObservableList<LoginAttempt> getLoginAttempts(){
        ObservableList<LoginAttempt> loginAttempts = FXCollections.observableArrayList();

        try {
            File myObj = new File("login_activity.txt");
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();

                    if(data.length() != 0 && data.charAt(data.length() -1) == 'e'){
                        String[] splits = data.split("\t", -1);
                        String userName = splits[0].split(":",-1)[1].replaceAll("\\s","");
                        String time = (splits[1].split(":",-1)[1] + ":" + splits[1].split(":",-1)[2] + ":" + splits[1].split(":",-1)[3]).substring(1);

                        int filterYear = loginDatePicker.getValue().getYear();
                        int filterDayOfYear = loginDatePicker.getValue().getDayOfYear();

                        Timestamp localTime = ControllerHelper.toLocalTimeZone(Timestamp.valueOf(time));

                        int year = localTime.toLocalDateTime().getYear();
                        int dayOfYear = localTime.toLocalDateTime().getDayOfYear();

                        if(year == filterYear && dayOfYear == filterDayOfYear){
                            loginAttempts.add(new LoginAttempt(userName,localTime));
                        }
                    }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return loginAttempts;
    }

    /**
     * Sets initial table and dropdown values
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeMonthDropdown.getItems().setAll(Month.values());
        typeColumn1.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn2.setCellValueFactory(new PropertyValueFactory<>("count"));


        ObservableList<Contact> contactList = new ContactDAO().getAll();
        contactDropdown.getItems().setAll(contactList);

        contactColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactColumn2.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactColumn3.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactColumn4.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactColumn5.setCellValueFactory(new PropertyValueFactory<>("localStartTime"));
        contactColumn6.setCellValueFactory(new PropertyValueFactory<>("localEndTime"));
        contactColumn7.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        loginColumn1.setCellValueFactory(new PropertyValueFactory<>("userName"));
        loginColumn2.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
}
