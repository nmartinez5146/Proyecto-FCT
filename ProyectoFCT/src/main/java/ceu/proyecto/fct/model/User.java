package ceu.proyecto.fct.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import ceu.proyecto.fct.model.num.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	private String username;
	private String pass;

	@Enumerated(EnumType.STRING)
	private Perfil profile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_student")
	private Student idStudent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mentor")
	private Mentor idMentor;

	private boolean active;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Perfil getProfile() {
		return profile;
	}

	public void setProfile(Perfil profile) {
		this.profile = profile;
	}

	public Student getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Student idStudent) {
		this.idStudent = idStudent;
	}

	public Mentor getIdMentor() {
		return idMentor;
	}

	public void setIdMentor(Mentor idMentor) {
		this.idMentor = idMentor;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
