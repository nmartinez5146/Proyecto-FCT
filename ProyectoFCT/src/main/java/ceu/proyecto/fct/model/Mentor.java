package ceu.proyecto.fct.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "mentors")
@DiscriminatorValue("MENTOR")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Mentor extends User{

	
	@Size(max = 100)
	private String fullName;
	
	private boolean active;
	
	@OneToMany(mappedBy = "mentor")
	private List<Student> students;

	@JsonProperty("students")
    public List<Student> getStudents() {
        return students;
    }
}
