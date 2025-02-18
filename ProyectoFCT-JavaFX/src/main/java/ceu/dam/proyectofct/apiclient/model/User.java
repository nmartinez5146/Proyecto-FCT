package ceu.dam.proyectofct.apiclient.model;

import java.util.UUID;

public class User {

	private UUID id;
    private String username;
    private String pass;
    private Perfil profile;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Perfil getProfile() { return profile; }
    public void setProfile(Perfil profile) { this.profile = profile; }

}
