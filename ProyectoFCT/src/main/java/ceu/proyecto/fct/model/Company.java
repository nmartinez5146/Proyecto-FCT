package ceu.proyecto.fct.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Entity
@Data
public class Company {
	
	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	@Size(max = 100)
    private String name;
	@Size(max = 100)
    private String mentorName;
	@Size(max = 100)
    private String mentorEmail;
	@Size(max = 9)
    private String mentorPhone;
    private boolean active;
}
