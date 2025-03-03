package ceu.dam.proyectofct.apiclient.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeRecord {

	private UUID id;
	private Date associatedDate;
	private int hours;
	private String description;

}
