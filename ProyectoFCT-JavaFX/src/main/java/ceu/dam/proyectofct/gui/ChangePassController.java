package ceu.dam.proyectofct.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChangePassController extends AppController {

    @FXML
    private Button btnSave;

    @FXML
    private ImageView imgRow;

    @FXML
    private Label lblNewpass;

    @FXML
    private Label lblReppass;

    @FXML
    private Label lblUsername;

    @FXML
    private PasswordField pfNewpass;

    @FXML
    private PasswordField pfReppass;

    @FXML
    private TextField tfUsername;

    @FXML
    void goBack(MouseEvent event) {

    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}
