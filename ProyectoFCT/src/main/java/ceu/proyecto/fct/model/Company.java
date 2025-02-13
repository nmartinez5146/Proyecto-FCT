package ceu.proyecto.fct.model;

import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Company {
	private UUID id;
	@Size(max = 255)
    private String name;
	@Size(max = 255)
    private String mentorName;
	@Size(max = 255)
    private String mentorEmail;
	@Size(max = 9)
    private String mentorPhone;
    private boolean active;
}
