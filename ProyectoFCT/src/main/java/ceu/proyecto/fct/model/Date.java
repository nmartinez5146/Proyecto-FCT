package ceu.proyecto.fct.model;

import java.util.UUID;

import ceu.proyecto.fct.model.num.Evaluation;
import lombok.Data;

@Data
public class Date {
	private UUID id;
    private Date date;
    private int courseYear;
    private Evaluation evaluation;
}
