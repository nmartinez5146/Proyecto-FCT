package ceu.dam.proyectofct.apiclient.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private UUID id;
	private String username;
	private String pass;
	private Perfil profile;
	private boolean active;
	
}
