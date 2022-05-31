import controller.ControllerHelper;
import controller.LoginScreenController;
import database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Main Class
 * @author Gavin Casey
 */

public class Main extends Application {
    /**
     * Connects to the database and launches the application
     * @param args console arguments
     */
    public static void main(String[] args){
        DBConnection.makeConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * Launches the javafx application
     * @param stage the base stage of the application
     * @throws Exception when stage cannot be created
     */
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("controller.MessagesBundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScreen.fxml"), bundle);
        ControllerHelper.createNewWindow(609,390,loader, new LoginScreenController());

    }
}
