package ceu.dam.proyectofct.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MenuController extends AppController{

	@FXML
	private BorderPane panel;
	
	private static BorderPane sharedPanel;
	
	public void initialize() {
		sharedPanel = panel;
		panel.setCenter(loadScene(FXML_USERHOME));
	}
	
	public static void loadPage(String fxmlPath) {
		sharedPanel.setCenter(loadScene(fxmlPath));
	}
	
    @FXML
    void changePass(ActionEvent event) {
    	panel.setCenter(loadScene(FXML_CHNGPASS));
    }

    @FXML
    void logout(ActionEvent event) {
    	changeScene(FXML_LOGIN);
    }

    @FXML
    void seeRecords(ActionEvent event) {
    	panel.setCenter(loadScene(FXML_RECORDS));
    	
    }
    
    @FXML
    void homePage(ActionEvent event) {
    	panel.setCenter(loadScene(FXML_USERHOME));
    }
    
    @FXML
    void exitApp(ActionEvent event) {
    	AppController.exitApplication();
    }

}
