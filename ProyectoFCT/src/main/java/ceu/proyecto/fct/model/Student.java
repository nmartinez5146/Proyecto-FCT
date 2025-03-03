package ceu.proyecto.fct.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ceu.proyecto.fct.model.num.Course;
import ceu.proyecto.fct.model.num.Evaluation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "students")
@DiscriminatorValue("STUDENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Student extends User {

	@Size(max = 100)
	private String fullName;

	@Enumerated(EnumType.STRING)
	private Course course;

	@Enumerated(EnumType.STRING)
	private Evaluation evaluation;

	private int courseYear;

	@ManyToOne
	@JoinColumn(name = "id_mentor")
	private Mentor mentor;

	@ManyToOne
	@JoinColumn(name = "id_company")
	private Company company;
	
	@OneToMany(mappedBy = "associatedStudent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PracticeRecord> practiceRecords;

	// Methods to force the loading of data in JSON
    @JsonProperty("company")
    public Company getCompany() {
        return company;
    }

    @JsonProperty("mentor")
    public Mentor getMentor() {
        return mentor;
    }
}