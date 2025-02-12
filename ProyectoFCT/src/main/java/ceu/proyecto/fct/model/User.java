package ceu.proyecto.fct.model;

import java.util.UUID;

import ceu.proyecto.fct.model.num.Perfil;
import lombok.Data;
@Data
public class User {
	private UUID id;
    private String username;
    private String pass;
    private Perfil profile;
    private UUID associateProfile;
    private boolean active;

}
