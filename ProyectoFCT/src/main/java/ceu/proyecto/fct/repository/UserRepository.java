package ceu.proyecto.fct.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ceu.proyecto.fct.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	public User findByUsername(String username);
	
}
