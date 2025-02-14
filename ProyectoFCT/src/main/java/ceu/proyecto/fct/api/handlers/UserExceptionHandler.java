package ceu.proyecto.fct.api.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ceu.proyecto.fct.service.UserException;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handle(UserException e) {
		return ResponseEntity.internalServerError().body(e.getMessage());
	}

}
