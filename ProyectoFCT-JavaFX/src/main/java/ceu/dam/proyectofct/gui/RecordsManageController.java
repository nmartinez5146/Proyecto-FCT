package ceu.dam.proyectofct.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

public class RecordsManageController extends AppController {

    @FXML
    private Button btnApplyF;

    @FXML
    private Button btnCreateNew;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colHours;

    @FXML
    private ToggleGroup dates;

    @FXML
    private DatePicker dpFrom;

    @FXML
    private DatePicker dpTo;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblHours;

    @FXML
    private Label lblTitle;

    @FXML
    private RadioButton rbAllDates;

    @FXML
    private RadioButton rbCDates;

    @FXML
    private RadioButton rbNCDates;

    @FXML
    private TableView<?> recordsTable;

    @FXML
    void applyFilters(ActionEvent event) {
    	
    }

    @FXML
    void createNewRecord(ActionEvent event) {
    	changeScene(FXML_NEWRECORD);
    }

}
