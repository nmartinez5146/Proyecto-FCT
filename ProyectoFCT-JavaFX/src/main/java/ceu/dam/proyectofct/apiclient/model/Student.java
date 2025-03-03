package ceu.dam.proyectofct.apiclient.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
	
	private String fullName;
	private Course course;
	private Evaluation evaluation;
	private int courseYear;
	
	private  Mentor mentor;
	
	private Company company;
	
	private List<PracticeRecord> practiceRecords;
	
}
