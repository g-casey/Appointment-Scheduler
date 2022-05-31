package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;
import model.DAO.AppointmentDAO;
import model.DAO.CountryDAO;
import model.DAO.FirstLevelDivisionDAO;
import model.FirstLevelDivision;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

public class ControllerHelper {
    public static <Controller> void createNewWindow(int width, int height, FXMLLoader loader, Controller controller) throws IOException {
        loader.setControllerFactory(c -> controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root, width,height));
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.show();
    }

    public static void createErrorDialog(String message){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Error");
        ButtonType button = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(button);

        dialog.showAndWait();
    }

    public static void countryComboBoxChange(ComboBox<FirstLevelDivision> stateComboBox, ComboBox<Country> countryComboBox) {
        stateComboBox.getItems().clear();
        ObservableList<FirstLevelDivision> firstLevelDivisionList = new FirstLevelDivisionDAO().getAll();
        ObservableList<Country> countryList = new CountryDAO().getAll();

        if(countryComboBox.getValue() != null){
            for(FirstLevelDivision division : firstLevelDivisionList){
                if(division.getCountryId() == countryComboBox.getValue().getId()){
                    stateComboBox.getItems().add(division);
                }
            }
        }
    }

    public static Timestamp toUTC(Instant instant){
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        return Timestamp.valueOf(zdt.toLocalDateTime());
    }

    public static Timestamp toLocalTimeZone(Timestamp timestamp){
        Instant instant = timestamp.toLocalDateTime().toInstant(ZoneOffset.UTC);
        return Timestamp.from(instant);
    }

    public static Timestamp toEST(Instant instant){
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("EST", ZoneId.SHORT_IDS));
        return Timestamp.valueOf(zdt.toLocalDateTime());
    }

    public static boolean checkAppointmentTime(Timestamp startTime, Timestamp endTime){
        startTime = toEST(startTime.toInstant());
        endTime = toEST(endTime.toInstant());

        if(startTime.toLocalDateTime().getHour() >= 8 && startTime.toLocalDateTime().getHour() < 22 && endTime.toLocalDateTime().getHour() >= 8 && endTime.toLocalDateTime().getHour() < 22){
            return true;
        }else {
            createErrorDialog("Please schedule your appointment inside the \nbusiness hours of 8 - 22 EST!");
            return false;
        }
    }

    public static boolean checkAppointmentOverlap(Appointment appointment, boolean modifying){
        LocalDateTime startTime = appointment.getStartTime().toLocalDateTime();
        LocalDateTime endTime = appointment.getEndTime().toLocalDateTime();
        int count = 0;

        for(Appointment compareAppt : new AppointmentDAO().getAll()){
            LocalDateTime cmpStartTime = compareAppt.getStartTime().toLocalDateTime();
            LocalDateTime cmpEndTime = compareAppt.getEndTime().toLocalDateTime();

            if(startTime.getYear() == cmpStartTime.getYear() &&  startTime.getMonthValue() == cmpStartTime.getMonthValue() && startTime.getDayOfMonth() == cmpStartTime.getDayOfMonth()){
                if(startTime.compareTo(cmpStartTime) >= 0  && startTime.compareTo(cmpEndTime) <= 0|| endTime.compareTo(cmpEndTime) <= 0 && endTime.compareTo(cmpStartTime) >= 0){
                    count ++;
                }

            }
        }
        if(count == 0 || modifying && count ==1){
            return true;
        }
        createErrorDialog("There is already an appointment scheduled during this time\nPlease reschedule!");
        return false;
    }
}
