package ceu.dam.proyectofct.gui;

import java.util.List;

import ceu.dam.proyectofct.apiclient.ApiClient;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserHomepageController extends AppController {

    @FXML
    private Label lblTitle, lblFullname, lblCourse, lblCourseYear, lblEvaluation, lblMentorName, lblCompanyName, lblTotalH, lblCompletedH, lblPendingH;

    public void initialize() {
    	
    	Student student = (Student) getParam("loggedStudent");
    	
    	if (student == null) {
            System.err.println("Error: loggedStudent es null en UserHomepageController.");
            return; // Evita que la aplicación falle si el estudiante es nulo
        }

        lblTitle.setText("Welcome, " + student.getUsername());
        lblFullname.setText(student.getFullName());
        lblCourse.setText(student.getCourse().toString());
        lblCourseYear.setText(String.valueOf(student.getCourseYear()));
        lblEvaluation.setText(student.getEvaluation().toString());
        lblMentorName.setText(student.getMentor().getFullName());
        lblCompanyName.setText(student.getCompany().getName());

        // Internship hours
        int totalHours = 370;
        int completedHours = 0;
        
        ApiClient apiClient = getApiClient();
        List<PracticeRecord> records = apiClient.getRecords(student.getId(), null, null, "");
        System.out.println(records);
        
        for (PracticeRecord practiceRecord : records) {
            completedHours += practiceRecord.getHours();
        }
        int pendingHours = totalHours - completedHours;

        lblTotalH.setText(String.valueOf(totalHours));
        lblCompletedH.setText(String.valueOf(completedHours) + " | " + calculatePercentage(completedHours, totalHours) + "%");
        lblPendingH.setText(String.valueOf(pendingHours) + " | " + calculatePendingPercentage(completedHours, totalHours) + "%");
    }
    
    public double calculatePercentage(int completedHours, int totalHours) {
        if (totalHours == 0) return 0.0;

        double percentage = ((double) completedHours / totalHours) * 100;
        return Math.round(percentage * 100.0) / 100.0;
    }
    
    public double calculatePendingPercentage(int completedHours, int totalHours) {
        if (totalHours == 0) return 0.0;

        double pendingPercentage = ((double) (totalHours - completedHours) / totalHours) * 100;
        return Math.round(pendingPercentage * 100.0) / 100.0;
    }


}
