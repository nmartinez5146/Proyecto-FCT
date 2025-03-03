package ceu.dam.proyectofct.apiclient.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	private UUID id;
	private String name;
	private String mentorName;
	private String mentorEmail;
	private String mentorPhone;
	private boolean active;
	private List<UUID> students;
	
}
