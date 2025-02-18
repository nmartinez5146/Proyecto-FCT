package ceu.dam.proyectofct.apiclient.model;

import java.time.LocalDate;
import java.util.UUID;

public class PracticeRecord {

	private UUID id;
    private User associatedStudent;
    private LocalDate associatedDate;
    private int hours;
    private String description;

    public PracticeRecord(User student, LocalDate date, int hours, String description) {
        this.associatedStudent = student;
        this.associatedDate = date;
        this.hours = hours;
        this.description = description;
    }

}
