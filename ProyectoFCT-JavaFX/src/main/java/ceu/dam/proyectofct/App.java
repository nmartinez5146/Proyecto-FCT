package ceu.dam.proyectofct;

import ceu.dam.proyectofct.gui.AppController;
import ceu.dam.proyectofct.gui.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AppController appController = new AppController(primaryStage);
		MenuController controller = (MenuController) appController.changeScene(AppController.FXML_MENU);
		controller.loadSceneInto(AppController.FXML_USERHOME);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Records App");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}

}
