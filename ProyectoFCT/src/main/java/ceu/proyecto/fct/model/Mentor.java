package ceu.proyecto.fct.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Mentor {
	private UUID id;

	private String fullName;
    private boolean active;
	
}
