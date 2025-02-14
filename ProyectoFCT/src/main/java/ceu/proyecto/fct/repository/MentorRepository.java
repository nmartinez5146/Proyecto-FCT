package ceu.proyecto.fct.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ceu.proyecto.fct.model.Mentor;
@Repository
public interface MentorRepository extends JpaRepository<Mentor, UUID>{

}
