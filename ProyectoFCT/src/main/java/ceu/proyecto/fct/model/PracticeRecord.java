package ceu.proyecto.fct.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PracticeRecord {

	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "id_student", nullable = false)
	@JsonIgnore
	private Student associatedStudent;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_date", nullable = false, unique = true) // Only one record for date
	private Date associatedDate;

	@Column(nullable = false)
<<<<<<< HEAD
	@Min(value = 1, message = "Las horas deben ser al menos 1")
	@Max(value = 16, message = "No puedes registrar más de 8 horas")
	private Double hours;
=======
	@Min(value = 1, message = "The hours must be at least 1")
	@Max(value = 16, message = "You cannot register more than 8 hours")
	private int hours;
>>>>>>> 379f4cdc6193b59ca5b611e1aa5ca848e72b567d
	private String description;

}
