package ceu.dam.proyectofct.apiclient.model;

public class ChangePassRequest {

	private String user;
    private String newPass;

    public ChangePassRequest(String user, String newPass) {
        this.user = user;
        this.newPass = newPass;
    }

}
