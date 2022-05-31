package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DAO.DAO;
import model.LoginAttempt;
import model.NumberReport;
import model.User;
import model.DAO.UserDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginScreenController implements Initializable {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label timeZoneLabel;

    private User currentUser;

    private ResourceBundle resourceBundle;


    @FXML
    void submitButtonClicked(MouseEvent event) throws IOException {

        String userName = usernameField.getText();
        String password = passwordField.getText();



        if(checkPassword(userName,password)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            ControllerHelper.createNewWindow(1358,800,loader, new MainScreenController(currentUser));
            Stage stage = (Stage) (usernameField.getScene().getWindow());
            stage.close();
        }else{
            ControllerHelper.createErrorDialog(resourceBundle.getString("loginerror"));
        }


    }

    private void logAttempt(String userName, boolean successful){
        try {
            FileWriter writer = new FileWriter("login_activity.txt",true);
            writer.write("\nLogin Name: " + userName + "\tTime(UTC): " + ControllerHelper.toUTC(Instant.now()) + "\tSuccessful: " + successful);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPassword(String userName, String password){
        DAO<User> userDAO = new UserDAO();
        ObservableList<User> userList = userDAO.getAll();

        for(User user : userList){
            if(userName.equals(user.getUserName()) && password.equals(user.getPassword())){
                currentUser = user;
                logAttempt(userName,true);
                return true;
            }
        }
        logAttempt(userName,false);
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        timeZoneLabel.setText(resourceBundle.getString("regionlabel") + " " +  ZoneId.systemDefault());
        System.out.println(Timestamp.from(Instant.now()));
    }
}
