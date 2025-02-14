package ceu.proyecto.fct.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import lombok.Data;

@Data
@Entity
public class PracticeRecord {
	
	@Id
	@GeneratedValue
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_student")
	private Student associatedStudent;
	
	@Valid
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_date")
	private Date associatedDate;
	
    private int hours;
    private String description;
}
