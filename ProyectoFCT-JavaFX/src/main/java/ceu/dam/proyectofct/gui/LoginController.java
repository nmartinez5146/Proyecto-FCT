package ceu.dam.proyectofct.gui;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.Mentor;
import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends AppController {

	@FXML
	private Button btnExitApp;

	@FXML
	private Button btnLogin;

	@FXML
	private PasswordField pfPass;

	@FXML
	private TextField tfUsername;

	@FXML
	void exitApp(ActionEvent event) {
		AppController.exitApplication();
	}

	@FXML
	void login(ActionEvent event) {
		ApiClient apiClient = getApiClient();

		String username = tfUsername.getText().trim();
		String password = pfPass.getText().trim();

		// Validaciones
		if (!isValidUsername(username)) {
			showErrorMessage("Invalid username. It must be at least 4 characters.");
			return;
		}

		if (!isValidPassword(password)) {
			showErrorMessage(
					"Invalid password. It must be at least 8 characters and contain at least one letter and one number.");
			return;
		}

		try {
			Object user = apiClient.login(username, password);
			if (user != null) {
				if (user instanceof Student) {
		            Student student = (Student) user;
		            System.out.println("LoggedStudent: " + student);
		            addParam("loggedStudent", student);
		            changeScene(FXML_MENU);
		            
		        } else if (user instanceof Mentor) {
		            Mentor mentor = (Mentor) user;
		            System.out.println("Mentor logueado: " + mentor.getFullName());
		            // TODO: Implementar lógica de pantallas para ADMINS
		        } else {
					showErrorMessage("Uknown user role.");
				}
			} else {
				showErrorMessage("Invalid username or password.");
			}
		} catch (Exception e) {
			showErrorMessage("Login error. " + e.getMessage());
			System.out.println("Login error: " + e.getMessage());
		}
	}

	private boolean isValidUsername(String username) {
		return username.length() >= 4;
	}

	private boolean isValidPassword(String password) {
		return password.matches(".*[a-zA-Z].*") && password.matches(".*\\d*.");
	}

	private void showErrorMessage(String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    
	    // Mostrar el alert y esperar a que el usuario lo cierre
	    alert.showAndWait();
	}

}
