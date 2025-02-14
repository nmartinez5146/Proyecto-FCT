package ceu.proyecto.fct.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.proyecto.fct.model.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

	
	
}
