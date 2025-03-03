package ceu.dam.proyectofct.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

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
    	changeScene(FXML_LOGIN);
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
            panel.setCenter(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
