package ceu.proyecto.fct.model;



import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Mentor{

	@Id
    @GeneratedValue
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private UUID id;
	@Size(max = 100)
	private String fullName;
    private boolean active;
	
}
