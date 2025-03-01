package ceu.dam.proyectofct.gui;

import ceu.dam.proyectofct.apiclient.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserHomepageController extends AppController {

    @FXML
    private Label lblCompanyName;

    @FXML
    private Label lblCompletedH;

    @FXML
    private Label lblCourse;

    @FXML
    private Label lblCourseYear;

    @FXML
    private Label lblEvaluation;

    @FXML
    private Label lblFullname;

    @FXML
    private Label lblMentorName;

    @FXML
    private Label lblPendingH;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblTotalH;

    void initialize() {
        User user = (User) getParam("loggedUser");

        lblTitle.setText("Welcome, " + user.getUsername());
        // TODO: Se debe actualizar el texto dinámicamente con los datos del usuario

    }
}
