package ceu.dam.proyectofct.apiclient.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Date {

	private UUID id;
	private LocalDate date;
	private int courseYear;
	private Evaluation evaluation;
	
}
