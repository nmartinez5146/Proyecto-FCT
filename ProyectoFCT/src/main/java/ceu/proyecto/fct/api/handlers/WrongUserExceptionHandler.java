package ceu.proyecto.fct.api.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ceu.proyecto.fct.service.WrongUserException;

@ControllerAdvice
public class WrongUserExceptionHandler {

	@ExceptionHandler(WrongUserException.class)
	public ResponseEntity<String> handle(WrongUserException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(e.getMessage());
	}

}
