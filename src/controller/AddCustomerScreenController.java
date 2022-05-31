package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.DAO.CountryDAO;
import model.DAO.CustomerDAO;
import model.DAO.DAO;
import model.DAO.FirstLevelDivisionDAO;
import model.FirstLevelDivision;
import model.User;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;

import static controller.ControllerHelper.countryComboBoxChange;

public class AddCustomerScreenController implements Initializable {

    private DAO<Customer> customerDAO;
    private User currentUser;

    public AddCustomerScreenController(DAO<Customer> customerDAO, User currentUser) {
        this.customerDAO = customerDAO;
        this.currentUser = currentUser;
    }

    @FXML
    private TextField addressTextField;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<FirstLevelDivision> stateComboBox;


    @FXML
    void cancelButtonClicked(MouseEvent event) {
        Stage window = (Stage) (nameTextField.getScene().getWindow());
        window.close();
    }

    @FXML
    public void saveButtonClicked(MouseEvent mouseEvent) {
        Customer customer;
        try{
            customer = new Customer(0,
                    nameTextField.getText(),
                    addressTextField.getText(),
                    postalCodeTextField.getText(),
                    phoneNumberTextField.getText(),
                    ControllerHelper.toUTC(Instant.now()),
                    currentUser.getUserName(),
                    ControllerHelper.toUTC(Instant.now()),
                    currentUser.getUserName(),
                    stateComboBox.getValue().getId()
            );

            customerDAO.create(customer);

            Stage window = (Stage) (nameTextField.getScene().getWindow());
            window.close();

        }catch (NullPointerException e){
            ControllerHelper.createErrorDialog("Please make sure to fill in every field");
        }
    }

    @FXML
    public void countryComboBoxSelected(ActionEvent mouseEvent) {
        countryComboBoxChange(stateComboBox, countryComboBox);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countryList = new CountryDAO().getAll();

        countryComboBox.getItems().addAll(countryList);

    }



}
