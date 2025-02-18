package ceu.dam.proyectofct.gui;

import java.time.LocalDate;
import java.util.Optional;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField tfNumHours;
    
    @FXML
    private Label lblErrorMessage;

    @FXML
    void goBack(MouseEvent event) {
    	LocalDate selectedDate = dpDate.getValue();
        String hoursText = tfNumHours.getText().trim();
        String description = taDescription.getText().trim();

        boolean hasData = (selectedDate != null) || !hoursText.isEmpty() || !description.isEmpty();

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
    	String hoursText = tfNumHours.getText().trim();
    	String description = taDescription.getText().trim();
    	
    	// Validaciones
    	if (!isValidDate(selectedDate)) {
    		showErrorMessage("Invalid date. You can only select today or past dates.");
    		return;
    	}
    	
    	if (!isValidHours(hoursText)) {
    		showErrorMessage("Invalid hours. Enter a number between 0 and 24");
    		return;
    	}
    	
    	if (!isValidDescription(description)) {
    		showErrorMessage("Description must be at least 20 characters long.");
    		return;
    	}
    	
    	int hours = Integer.parseInt(hoursText);
    	PracticeRecord record = new PracticeRecord(AppController.getLoggedUser(), selectedDate, hours, description);
    	
    	try {
    		apiClient.createRecord(record);
    		showSuccessMessage("The record has been created successfully.");
    	} catch (Exception e) {
			showErrorMessage("Error saving record: " + e.getMessage());
		}
    	
    	MenuController menuRecords = (MenuController) changeScene(FXML_MENU);
    	menuRecords.seeRecords(null);
    }
    
    private boolean isValidDate(LocalDate date) {
    	return date != null && !date.isAfter(LocalDate.now());
    }
    
    private boolean isValidHours(String hoursText) {
    	if (hoursText.isEmpty() || !hoursText.matches("\\d+(\\.\\d+)?")) return false;
    	double hours = Double.parseDouble(hoursText);
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
        alert.setHeaderText(null); // Sin cabecera para que el mensaje sea más limpio
        alert.setContentText(message);
        alert.showAndWait();
    }


}
