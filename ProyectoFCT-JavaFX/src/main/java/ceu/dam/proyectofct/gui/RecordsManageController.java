package ceu.dam.proyectofct.gui;

import java.time.LocalDate;
import java.util.List;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecordsManageController extends AppController {

	@FXML
	private Button btnApplyF;

	@FXML
	private Button btnCreateNew;

	@FXML
	private TableColumn<PracticeRecord, LocalDate> colDate;

	@FXML
	private TableColumn<PracticeRecord, String> colDescription;

	@FXML
	private TableColumn<PracticeRecord, Integer> colHours;

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
	private TableView<PracticeRecord> recordsTable;
	
	private ObservableList<PracticeRecord> datos;
	
	private Student student;
	
	public RecordsManageController() {
		this.student = (Student) getParam("loggedStudent");
	}

	@FXML
	void initialize() {
		// TODO: Recopiar todo los records del usuario loggeado y mostrarlos en la tabla
		colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
		
		datos = FXCollections.observableArrayList();
		recordsTable.setItems(datos);
		
		List<PracticeRecord> practiceRecords = student.getPracticeRecords();
		datos.setAll(practiceRecords);
		
	}

	@FXML
	void applyFilters(ActionEvent event) {
		RadioButton selectedRB = (RadioButton) dates.getSelectedToggle();
		LocalDate dateFrom = dpFrom.getValue();
		LocalDate dateTo = dpTo.getValue();
		
		// TODO: Meter en una lista el rango de fechas recibido de la api

		switch (selectedRB.getId()) {
		case "rbAllDates":
			// TODO: dejar la lista con todas las fechas
			break;
		case "rbCDates":
			// TODO: filtar lista por fechas
			break;
		case "rbNCDates":
			// TODO: filtrar lista por fechas
			break;
		}
		
	}

	@FXML
	void createNewRecord(ActionEvent event) {
		changeScene(FXML_NEWRECORD);
	}

}
