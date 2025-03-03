package ceu.proyecto.fct.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ceu.proyecto.fct.model.num.Perfil;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	
	private String username;
	
	@NotBlank(message = "The password cannot be empty")
	private String pass;

	@Enumerated(EnumType.STRING)
	private Perfil profile;

	private boolean active;
	
	@JsonIgnore // Evita que Hibernate serialice los usuarios con todas sus subclases
	public boolean isStudent() {
		return this.profile == Perfil.STUDENT;
	}
	
	@JsonIgnore
	public boolean isMentor() {
		return this.profile == Perfil.MENTOR;
	}
}
