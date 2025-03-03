package ceu.proyecto.fct.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
	@Email
	private String mentorEmail;

	@Size(max = 9)
	@Pattern(regexp = "^[0-9]*$", message = "The phone can only contain numbers")
	private String mentorPhone;

	private boolean active;

	@OneToMany(mappedBy = "company")
	private List<Student> students;

}
