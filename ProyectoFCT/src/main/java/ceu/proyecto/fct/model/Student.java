package ceu.proyecto.fct.model;

import java.util.UUID;

import ceu.proyecto.fct.model.num.Course;
import ceu.proyecto.fct.model.num.Evaluation;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Student {
	private UUID id;
	@Size(max = 255)
    private String fullName;
    private Course course;
    private Evaluation evaluation;
    private int courseYear;
    private Mentor mentor;
    private Company company;
}