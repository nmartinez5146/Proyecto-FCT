package ceu.proyecto.fct.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Company {
	private UUID id;
    private String name;
    private String mentorName;
    private String mentorEmail;
    private String mentorPhone;
    private boolean active;
}
