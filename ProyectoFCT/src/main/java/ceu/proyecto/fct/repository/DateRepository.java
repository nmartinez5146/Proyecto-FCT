package ceu.proyecto.fct.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ceu.proyecto.fct.model.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, UUID>{

}
