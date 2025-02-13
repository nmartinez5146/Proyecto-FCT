package ceu.proyecto.fct.model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import ceu.proyecto.fct.model.num.Evaluation;
import lombok.Data;

@Data
public class Date {
	private UUID id;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private int courseYear;
    private Evaluation evaluation;
}
