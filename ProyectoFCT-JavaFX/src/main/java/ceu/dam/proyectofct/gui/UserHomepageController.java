package ceu.dam.proyectofct.gui;

import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserHomepageController extends AppController {

	private Student student;

    public void setStudent(Student student) {
        this.student = student;
        initializeData();
    }

    @FXML
    private Label lblTitle, lblFullname, lblCourse, lblCourseYear, lblEvaluation, lblMentorName, lblCompanyName, lblTotalH, lblCompletedH, lblPendingH;

    @FXML
    public void initialize() {
    }

    private void initializeData() {
        if (student == null) return;

        System.out.println(student.getFullName());
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
        for (PracticeRecord record : student.getPracticeRecords()) {
            completedHours += record.getHours();
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
