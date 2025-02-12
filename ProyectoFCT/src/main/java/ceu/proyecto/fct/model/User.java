package ceu.proyecto.fct.model;

import java.util.UUID;

import org.apache.naming.java.javaURLContextFactory;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;

import ceu.proyecto.fct.model.num.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    private Perfil profile;
    private UUID associateProfile;
    private boolean active;

}
