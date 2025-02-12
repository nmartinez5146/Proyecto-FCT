package ceu.proyecto.fct.model;

import java.time.LocalDate;
import java.util.UUID;

import ceu.proyecto.fct.model.num.Evaluation;
import lombok.Data;

@Data
public class Date {
	private UUID id;
    private LocalDate date;
    private int courseYear;
    private Evaluation evaluation;
}
