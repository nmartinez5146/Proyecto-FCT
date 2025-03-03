package ceu.dam.proyectofct.apiclient.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mentor extends User {
	
	private String fullName;
	private List<UUID> students;
	
}
