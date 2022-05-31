package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import model.DAO.CountryDAO;
import model.DAO.DAO;
import model.DAO.FirstLevelDivisionDAO;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;

import static controller.ControllerHelper.countryComboBoxChange;


public class ModifyCustomerScreenController implements Initializable {

    private Customer selectedCustomer;
    private DAO<Customer> customerDAO;
    private User currentUser;

    public ModifyCustomerScreenController(DAO<Customer> customerDAO, Customer selectedCustomer, User currentUser) {
        this.customerDAO = customerDAO;
        this.selectedCustomer = selectedCustomer;
        this.currentUser = currentUser;
    }

    @FXML
    private TextField addressTextField;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private TextField idTextField;

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
    void saveButtonClicked(MouseEvent event) {
        Customer customer;
        try{
            customer = new Customer(selectedCustomer.getId(),
                    nameTextField.getText(),
                    addressTextField.getText(),
                    postalCodeTextField.getText(),
                    phoneNumberTextField.getText(),
                    selectedCustomer.getCreateDate(),
                    currentUser.getUserName(),
                    ControllerHelper.toUTC(Instant.now()),
                    currentUser.getUserName(),
                    stateComboBox.getValue().getId()
            );

            customerDAO.update(selectedCustomer.getId(),customer);

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
        populateFields();
    }

    private void populateFields(){
        TranslationHelper.initialize();
        ObservableList<FirstLevelDivision> divisionList = new FirstLevelDivisionDAO().getAll();

        Country selectedCountry = TranslationHelper.getCountryMap().get(selectedCustomer.getDivisionId());
        FirstLevelDivision selectedDivision = TranslationHelper.getDivisionMap().get(selectedCustomer.getDivisionId());

        idTextField.setText(Integer.toString(selectedCustomer.getId()));
        nameTextField.setText(selectedCustomer.getName());
        addressTextField.setText(selectedCustomer.getAddress());
        countryComboBox.getSelectionModel().select(selectedCountry);

        stateComboBox.getItems().addAll(TranslationHelper.getCountryDivisionMap().get(selectedCountry));

        stateComboBox.getSelectionModel().select(selectedDivision);
        postalCodeTextField.setText(selectedCustomer.getPostalCode());
        phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());
    }

}
