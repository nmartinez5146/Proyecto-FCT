package ceu.dam.proyectofct.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecordsManageController extends AppController {

	@FXML
	private Button btnApplyF;

	@FXML
	private Button btnCreateNew;
	
	@FXML
    private Button btnDeleteRecord;
	
	@FXML
    private Button btnCleanFields;

	@FXML
	private TableColumn<PracticeRecord, String> colDate;

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
	private ApiClient apiClient;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public RecordsManageController() {
		this.student = (Student) getParam("loggedStudent");
		this.apiClient = getApiClient();
	}

	@FXML
	void initialize() {
		colDate.setCellValueFactory(cellData -> {
	        if (cellData.getValue().getAssociatedDate() != null && cellData.getValue().getAssociatedDate().getDate() != null) {
	            String formattedDate = cellData.getValue().getAssociatedDate().getDate().format(formatter);
	            return new SimpleStringProperty(formattedDate);
	        } else {
	            return new SimpleStringProperty("No Date");
	        }
	    });

		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
		
		datos = FXCollections.observableArrayList();
		recordsTable.setItems(datos);
		
		List<PracticeRecord> practiceRecords = apiClient.getRecords(student.getId() , null, null, "");
		datos.setAll(practiceRecords);
		
		recordsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				mostrarDetalles(newSelection);
			}
		});
		
	}
	
	private void mostrarDetalles(PracticeRecord practiceRecord) {
	    lblDate.setText(practiceRecord.getAssociatedDate().getDate().format(formatter).toString());
	    lblDescription.setText(practiceRecord.getDescription());
	    lblHours.setText(String.valueOf(practiceRecord.getHours()));
	    btnDeleteRecord.setDisable(false);
	}

	@FXML
	void applyFilters(ActionEvent event) {
		RadioButton selectedRB = (RadioButton) dates.getSelectedToggle();
		LocalDate dateFrom = dpFrom.getValue();
		LocalDate dateTo = dpTo.getValue();

		String stateDate = "ALL";
		switch (selectedRB.getId()) {
		case "rbAllDates":
			stateDate = "ALL";
			break;
		case "rbCDates":
			stateDate = "COMPLETED";
			break;
		case "rbNCDates":
			stateDate = "NOT COMPLETED";
			break;
		}
		
		List<PracticeRecord> filteredRecords = apiClient.getRecords(student.getId(), dateFrom, dateTo, stateDate);
		datos.setAll(filteredRecords);
		
	}

	@FXML
	void createNewRecord(ActionEvent event) {
		changeScene(FXML_NEWRECORD);
	}
	
	@FXML
    void deleteRecord(ActionEvent event) {
		PracticeRecord selectedRecord = recordsTable.getSelectionModel().getSelectedItem();
	    if (selectedRecord != null) {
	        apiClient.deleteRecord(selectedRecord.getId());
	        datos.remove(selectedRecord);
	        limpiarDetalles();
	        btnDeleteRecord.setDisable(true);
	    }
    }
	
	@FXML
    void cleanFields(ActionEvent event) {
		dpFrom.setValue(null);
		dpTo.setValue(null);
		rbAllDates.setSelected(true);
		rbCDates.setSelected(false);
		rbNCDates.setSelected(false);
    }
	
	private void limpiarDetalles() {
	    lblDate.setText("");
	    lblDescription.setText("");
	    lblHours.setText("");
	}


}
