package ceu.proyecto.fct.api.request;

import ceu.proyecto.fct.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePassRequest {

	@Size(min = 8)
	@Schema(description =  "Password nueva. Mínimo 8 caracteres. Se debe enviar sin codificar.")
	private String newPass;
	
	private User user;
}
