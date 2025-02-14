package ceu.proyecto.fct.model;

import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Mentor {
	private UUID id;
	@Size(max = 100)
	private String fullName;
    private boolean active;
	
}
