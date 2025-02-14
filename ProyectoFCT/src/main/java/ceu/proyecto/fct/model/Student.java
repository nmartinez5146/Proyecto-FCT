package ceu.proyecto.fct.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import ceu.proyecto.fct.model.num.Course;
import ceu.proyecto.fct.model.num.Evaluation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Student {
	
	@Id
    @GeneratedValue
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private UUID id;

	@Size(max = 100)
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    private Course course;
    
    @Enumerated(EnumType.STRING)
    private Evaluation evaluation;
    
    private int courseYear;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mentor")
    private Mentor mentor;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_company")
    private Company company;
    
    
    
    
}