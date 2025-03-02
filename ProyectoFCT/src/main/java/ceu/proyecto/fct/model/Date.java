package ceu.proyecto.fct.model;

import java.time.DayOfWeek;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
	
	@PrePersist
	@PreUpdate
	private void validateDate() {
		if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			throw new IllegalArgumentException("No se pueden registrar prácticas en fines de semana.");
		}
	}

}
