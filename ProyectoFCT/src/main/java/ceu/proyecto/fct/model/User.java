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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_student")
    private Student idStudent;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mentor")
    private Mentor idMentor;
    
    private boolean active;

    
    
    
    
}
