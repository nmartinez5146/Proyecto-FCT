package ceu.dam.proyectofct.gui;

import java.time.LocalDate;
import java.util.Optional;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.Date;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class NewRecordsController extends AppController {

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ImageView imgRow;

    @FXML
    private Label lblNewpass;

    @FXML
    private Label lblReppass;

    @FXML
    private Label lblUsername;

    @FXML
    private TextArea taDescription;

    @FXML
    private Spinner<Double> spNumHours;

    @FXML
    private Label lblErrorMessage;
    
    
    public void initialize() {
    	System.out.println("spNumHours: " + spNumHours);
        SpinnerValueFactory<Double> valueFactory = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 16.0, 0.0, 0.5);
        spNumHours.setValueFactory(valueFactory);
    }

    @FXML
    void goBack(MouseEvent event) {
        LocalDate selectedDate = dpDate.getValue();
        String description = taDescription.getText().trim();

        boolean hasData = (selectedDate != null) || !description.isEmpty();

        if (!hasData) {
            MenuController menuRecords = (MenuController) changeScene(FXML_MENU);
            menuRecords.seeRecords(null);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Unsaved Data Warning");
        alert.setContentText("If you leave, all entered data will be lost. Do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            MenuController menuRecords = (MenuController) changeScene(FXML_MENU);
            menuRecords.seeRecords(null);
        }
    }

    @FXML
    void saveRecord(ActionEvent event) {
        ApiClient apiClient = new ApiClient();

        LocalDate selectedDate = dpDate.getValue();
        Double hours = spNumHours.getValue();
        String description = taDescription.getText().trim();
        
        Student student = (Student) getParam("loggedStudent");

        // Validaciones
        if (!isValidDate(selectedDate)) {
            showErrorMessage("Invalid date. You can only select today or past dates.");
            return;
        }

        if (!isValidHours(hours)) {
            showErrorMessage("Invalid hours. Enter a number between 0 and 16");
            return;
        }

        if (!isValidDescription(description)) {
            showErrorMessage("Description must be at least 20 characters long.");
            return;
        }
        
        Date date = new Date();
        date.setCourseYear(student.getCourseYear());
        date.setEvaluation(student.getEvaluation());
        date.setDate(selectedDate);
        
        PracticeRecord record = new PracticeRecord();
        record.setAssociatedDate(date);
        record.setDescription(description);
        record.setHours(hours);
        
        System.out.println("PracticeRecord before sending: " + record);
        System.out.println("Date in PracticeRecord: " + record.getAssociatedDate());

        try {
            apiClient.createRecord(student.getId(), record);
            showSuccessMessage("The record has been created successfully.");
            MenuController menuRecords = (MenuController) changeScene(FXML_MENU);
            menuRecords.seeRecords(null);
        } catch (Exception e) {
            showErrorMessage("Error saving record: " + e.getLocalizedMessage());
        }
    }

    private boolean isValidDate(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }

    private boolean isValidHours(Double hours) {
        return hours > 0 && hours <= 24;
    }

    private boolean isValidDescription(String description) {
        return !description.isEmpty() || description.length() >= 20;
    }

    private void showErrorMessage(String message) {
        if (lblErrorMessage != null) {
            lblErrorMessage.setText(message);
        } else {
            System.out.println("Error: " + message);
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}