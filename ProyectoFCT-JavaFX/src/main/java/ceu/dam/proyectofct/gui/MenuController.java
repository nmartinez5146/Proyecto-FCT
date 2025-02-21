package ceu.dam.proyectofct.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MenuController extends AppController{

	@FXML
	private BorderPane panel;
	
	public void initialize() {
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
    	panel.setCenter(new Pane());
    	panel.setCenter(loadScene(fxml));
    }

}
