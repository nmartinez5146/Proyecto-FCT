package ceu.dam.proyectofct.gui;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends AppController {

	@FXML
	private Button btnExitApp;

	@FXML
	private Button btnLogin;

	@FXML
	private Label lblPass;

	@FXML
	private Label lblUsername;

	@FXML
	private PasswordField pfPass;

	@FXML
	private TextField tfUsername;

	@FXML
	private Label lblErrorMessage;

	@FXML
	void exitApp(ActionEvent event) {
		AppController.exitApplication();
	}

	@FXML
	void login(ActionEvent event) {
		ApiClient apiClient = new ApiClient();

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
			User user = apiClient.login(username, password);
			if (user != null) {
				AppController.setLoggedUser(user);
				System.out.println("Login successful: " + user.getUsername() + " | Role:" + user.getProfile());

				if (AppController.isStudent()) {
					addParam("loggedUser", user);
					changeScene(FXML_MENU);
				} else if (AppController.isMentor()) {
					// TODO: Implementar pagina ADMIN
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
		if (lblErrorMessage != null) {
			lblErrorMessage.setText(message);
		} else {
			System.out.println("Error: " + message);
		}
	}

}
