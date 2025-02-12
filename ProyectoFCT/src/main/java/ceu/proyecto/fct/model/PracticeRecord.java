package ceu.proyecto.fct.model;

import lombok.Data;

@Data
public class PracticeRecord {
	
	private Student associatedStudent;
    private Date associatedDate;
    private int hours;
    private String description;
}
