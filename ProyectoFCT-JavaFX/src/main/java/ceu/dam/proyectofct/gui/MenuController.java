package ceu.dam.proyectofct.gui;

import java.io.IOException;

import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MenuController extends AppController{

	@FXML
	private BorderPane panel;
	
	public void initialize() {
		loadSceneInto(FXML_USERHOME);
	}
	
    @FXML
    void changePass(ActionEvent event) {
    	loadSceneInto(FXML_CHNGPASS);
    }

    @FXML
    void logout(ActionEvent event) {
    	loadSceneInto(FXML_LOGIN);
    }

    @FXML
    void seeRecords(ActionEvent event) {
    	loadSceneInto(FXML_RECORDS);
    }
    
    @FXML
    void homePage(ActionEvent event) {
    	loadSceneInto(FXML_USERHOME);
    }
    
    @FXML
    void exitApp(ActionEvent event) {
    	AppController.exitApplication();
    }
    
    public void loadSceneInto(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent newScene = loader.load();
            
            // Si la escena es UserHome, pasarle los parámetros adecuados
            if (FXML_USERHOME.equals(fxml)) {
                UserHomepageController userHomeController = loader.getController();
                Student loggedStudent = (Student) getParam("loggedStudent"); // Obtiene el estudiante logueado
                userHomeController.setStudent(loggedStudent); // Método para pasar datos antes de que se renderice
            }

            panel.setCenter(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
