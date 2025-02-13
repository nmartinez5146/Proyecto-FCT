package ceu.proyecto.fct.model;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class PracticeRecord {
	
	private Student associatedStudent;
	@Valid
    private Date associatedDate;
    private int hours;
    private String description;
}
