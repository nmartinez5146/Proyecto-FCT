package ceu.dam.proyectofct.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePassController extends AppController {

    @FXML
    private Button btnSave;

    @FXML
    private Label lblNewpass;

    @FXML
    private Label lblPass;

    @FXML
    private PasswordField pfNewpass;

    @FXML
    private PasswordField pfPass;

    @FXML
    private Label lblErrorMessage;

    @FXML
    void saveChanges(ActionEvent event) {
        String currentPass = pfPass.getText().trim();
        String newPass = pfNewpass.getText().trim();

        // Validaciones
        if (!isValidCurrentPassword(currentPass)) {
            showErrorMessage("Current password cannot be empty.");
            return;
        }

        if (!isValidNewPassword(newPass, currentPass)) {
            showErrorMessage(
                    "New password must be include one letter and one number, and be different from the current password.");
            return;
        }

        // TODO: Implementar lógica para guardar la nueva contraseña en la base de datos

        // Confirmación de cambio exitoso
        showConfirmation("Password changed successfully.");
        changeScene(FXML_MENU);
    }

    private boolean isValidCurrentPassword(String password) {
        return !password.isEmpty();
    }

    private boolean isValidNewPassword(String newPassword, String currentPassword) {
        return newPassword.matches(".*[a-zA-Z].*") &&
                newPassword.matches(".*\\d.*") &&
                !newPassword.equals(currentPassword);
    }

    private void showErrorMessage(String message) {
        if (lblErrorMessage != null) {
            lblErrorMessage.setText(message);
            lblErrorMessage.setStyle("-fx-text-fill: red;");
        } else {
            System.out.println("Error: " + message);
        }
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}