package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import model.DAO.AppointmentDAO;
import model.DAO.CustomerDAO;
import model.DAO.DAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.Timer;

public class MainScreenController implements Initializable {

    private User currentUser;

    public MainScreenController(User currentUser) {
        this.currentUser = currentUser;
    }

    private DAO<Customer> customerDAO = new CustomerDAO();
    private DAO<Appointment> appointmentDAO = new AppointmentDAO();

    @FXML
    private TableColumn<Customer,Integer> customerColumn1;

    @FXML
    private TableColumn<Customer,String> customerColumn2;

    @FXML
    private TableColumn<Customer,String> customerColumn3;

    @FXML
    private TableColumn<Customer,String> customerColumn4;

    @FXML
    private TableColumn<Customer,String> customerColumn5;

    @FXML
    private TableColumn<Customer,Integer> customerColumn6;

    @FXML
    private Label userInfo;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentColumn1;

    @FXML
    private TableColumn<Appointment, String> appointmentColumn2;

    @FXML
    private TableColumn<Appointment, String > appointmentColumn3;

    @FXML
    private TableColumn<Appointment, String> appointmentColumn4;

    @FXML
    private TableColumn<Appointment, String> appointmentColumn5;

    @FXML
    private TableColumn<Appointment,Integer> appointmentColumn6;

    @FXML
    private TableColumn<Appointment, Integer> appointmentColumn7;

    @FXML
    private TableColumn<Appointment, Integer> appointmentColumn8;

    @FXML
    private TableColumn<Appointment, Timestamp> appointmentColumn9;

    @FXML
    private TableColumn<Appointment, Timestamp> appointmentColumn10;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private RadioButton monthFilter;

    @FXML
    private RadioButton weekFilter;


    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton allFilter;

    @FXML
    private Label alertText;


    @FXML
    void dateSelected(ActionEvent event) {
        filterAppointments();
    }


    @FXML
    void viewReportsButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewReportsScreen.fxml"));
        ControllerHelper.createNewWindow(937,502,loader,new ViewReportScreenController(appointmentDAO));
    }


    @FXML
    void allFilterClicked(MouseEvent event) {
        monthFilter.setSelected(false);
        weekFilter.setSelected(false);
        appointmentTable.setItems(appointmentDAO.getAll());
        datePicker.getEditor().clear();
    }

    @FXML
    void monthFilterClicked(MouseEvent event) {
        if(weekFilter.isSelected()){
            weekFilter.setSelected(false);
            filterAppointments();
        }
            allFilter.setSelected(false);
    }

    @FXML
    void weekFilterClicked(MouseEvent event) {
        if(monthFilter.isSelected()) {
            monthFilter.setSelected(false);
            filterAppointments();
        }
            allFilter.setSelected(false);
    }

    @FXML
    void customerAddButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCustomerScreen.fxml"));
        ControllerHelper.createNewWindow(519,459,loader,new AddCustomerScreenController(customerDAO,currentUser));
    }

    @FXML
    void customerDeleteButtonClicked(MouseEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        int apptcount = 0;

        for(Appointment appointment : appointmentDAO.getAll()){
            if(appointment.getCustomerId() == selectedCustomer.getId()){
                apptcount++;
            }
        }

        if(apptcount == 0) {
            customerDAO.delete(selectedCustomer);
            ControllerHelper.createErrorDialog("Customer has been successfully deleted!");
        }else{
            ControllerHelper.createErrorDialog("You must first delete all of the customers appointments before deleting the customer!");
        }

    }

    @FXML
    void customerModifyButtonClicked(MouseEvent event) throws IOException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
        ControllerHelper.createNewWindow(519,459,loader,new ModifyCustomerScreenController(customerDAO,selectedCustomer,currentUser));
    }

    @FXML
    void appointmentAddButtonClicked(MouseEvent event) throws IOException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            ControllerHelper.createErrorDialog("You must first select a customer to schedule an appointment!");
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAppointmentScreen.fxml"));
            ControllerHelper.createNewWindow(519,487,loader,new AddAppointmentScreenController(appointmentDAO,currentUser,selectedCustomer));
        }
    }

    @FXML
    void appointmentDeleteButtonClicked(MouseEvent event) {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        appointmentDAO.delete(selectedAppointment);
        ControllerHelper.createErrorDialog("Appointment of type " + selectedAppointment.getType() + " with ID " + selectedAppointment.getId() + " has been successfully deleted!");
    }

    @FXML
    void appointmentModifyButtonClicked(MouseEvent event) throws IOException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            ControllerHelper.createErrorDialog("You must first select an appointment to modify!");
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
            ControllerHelper.createNewWindow(519,487,loader,new ModifyAppointmentScreenController(appointmentDAO,currentUser,selectedAppointment));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userInfo.setText("Logged in as: " + currentUser.getUserName());
        allFilter.setSelected(true);
        populateCustomerTable();
        populateAppointmentTable();

        for(Appointment appointment : appointmentDAO.getAll()){
            Timestamp currentTime = ControllerHelper.toUTC(Instant.now());

            long difference = appointment.getStartTime().getTime() - currentTime.getTime();
            long minuteDifference = (difference / 1000) / 60;

            if(Math.abs(minuteDifference) <= 15){
                alertText.setText("Alert: Appointment with id number " + appointment.getId() + " is starting soon\n" + appointment.getStartTime());
            }else{
                alertText.setText("No appointments are starting soon");
            }
        }
    }

    private void filterAppointments(){
        ObservableList<Appointment> filteredWeekList = FXCollections.observableArrayList();
        ObservableList<Appointment> filteredMonthList = FXCollections.observableArrayList();
        ZonedDateTime date = datePicker.getValue().atStartOfDay(ZoneId.systemDefault());
        DayOfWeek dow = date.getDayOfWeek();
        int filterMonth = date.getMonthValue();
        int filterYear = date.getYear();

        int dom = date.getDayOfMonth();
        int startDay = dom - dow.getValue() + 1;
        int endDay = startDay + 6;

        for(Appointment appointment : appointmentDAO.getAll()){
            int day = appointment.getStartTime().toLocalDateTime().getDayOfMonth();
            int month = appointment.getStartTime().toLocalDateTime().getMonthValue();
            int year = appointment.getStartTime().toLocalDateTime().getYear();

            if(weekFilter.isSelected() && day >= startDay && day <= endDay && month == filterMonth && year == filterYear){
                filteredWeekList.add(appointment);
                appointmentTable.setItems(filteredWeekList);
            }else if(monthFilter.isSelected() && month == filterMonth && year == filterYear){
                filteredMonthList.add(appointment);
                appointmentTable.setItems(filteredMonthList);
            }
        }
        if(filteredMonthList.isEmpty() && filteredWeekList.isEmpty()){
            appointmentTable.setItems(FXCollections.observableArrayList());
        }

    }


    private void populateCustomerTable(){
        customerColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerColumn3.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerColumn4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerColumn5.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerColumn6.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customerTable.setItems(customerDAO.getAll());
    }

    private void populateAppointmentTable(){
        appointmentColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentColumn2.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentColumn3.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentColumn4.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentColumn5.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentColumn6.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentColumn7.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentColumn8.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentColumn9.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentColumn10.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointmentTable.setItems(appointmentDAO.getAll());
    }
}
