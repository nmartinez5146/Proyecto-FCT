package ceu.proyecto.fct.api.request;

import ceu.proyecto.fct.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePassRequest {

	@Size(min = 8)
	@Schema(description = "Password nueva. Mínimo 8 caracteres.")
	private String newPass;

	private User user;

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
