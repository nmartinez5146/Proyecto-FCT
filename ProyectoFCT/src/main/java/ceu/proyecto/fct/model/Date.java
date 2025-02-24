package ceu.proyecto.fct.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonFormat;

import ceu.proyecto.fct.model.num.Evaluation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Date {
	
	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private int courseYear;
    private Evaluation evaluation;
}
