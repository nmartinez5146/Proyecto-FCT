package ceu.dam.proyectofct.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ceu.dam.proyectofct.App;
import ceu.dam.proyectofct.apiclient.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AppController {
	
	public static final String FXML_LOGIN = "/app/login.fxml";
	public static final String FXML_CHNGPASS = "/app/changePass.fxml";
	public static final String FXML_USERHOME = "/app/userHomepage.fxml";
	public static final String FXML_RECORDS = "/app/recordsManage.fxml";
	public static final String FXML_NEWRECORD = "/app/newRecords.fxml";
	public static final String FXML_MENU = "/app/menu.fxml";
	
	private static Stage primaryStage;
	private static User loggedUser;

	public AppController() {
	}

	public AppController(Stage stage){
		primaryStage = stage;
	}

	public AppController changeScene(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);
			return loader.getController();
		}
		catch(IOException e) {
			throw new RuntimeException("Error cambiando escena.", e);
		}
	}

	public static Parent loadScene(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
			Scene scene = new Scene(loader.load());
			return scene.getRoot();
		}
		catch(IOException e) {
			throw new RuntimeException("Error cargando escena.", e);
		}
	}
	
	public void addParam(String key, Object param) {
		Map<String, Object> mapa = (Map<String, Object>) primaryStage.getUserData();
		if (mapa == null) {
			mapa = new HashMap<String, Object>();
			primaryStage.setUserData(mapa);
		}
		mapa.put(key, param);
	}
	
	public Object getParam(String key) {
		Map<String, Object> mapa = (Map<String, Object>) primaryStage.getUserData();
		if (mapa == null) {
	        mapa = new HashMap<>();  // Se inicializa si no existe
	        primaryStage.setUserData(mapa);
	    }
		return mapa.get(key);
	}
	
	// Guardar usuario logueado
    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    // Obtener usuario logueado
    public static User getLoggedUser() {
        return loggedUser;
    }

    // Comprobar si el usuario es estudiante basado en su perfil
    public static boolean isStudent() {
        return loggedUser != null && "STUDENT".equals(loggedUser.getProfile());
    }

    // Comprobar si el usuario es mentor basado en su perfil
    public static boolean isMentor() {
        return loggedUser != null && "MENTOR".equals(loggedUser.getProfile());
    }
	
	public static void exitApplication() {
        System.out.println("Exit the application");
        
        // Create confirmation alert
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit the aplication");
        alert.setHeaderText("Are you sure you want to exit the application?");
        alert.setContentText("The application will close.");

        // Show and wait the user awnser
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0); // Exit the app
        }
    }
}
