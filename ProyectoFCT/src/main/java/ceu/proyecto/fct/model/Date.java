package ceu.proyecto.fct.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonFormat;

import ceu.proyecto.fct.model.num.Evaluation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Date {

	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	
	@NotNull(message = "La fecha no puede ser nula")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate date;
	
	@NotNull(message = "El año del curso no puede ser nulo")
	private int courseYear;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "La evaluación no puede ser nula")
	private Evaluation evaluation;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(int courseYear) {
		this.courseYear = courseYear;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

}
