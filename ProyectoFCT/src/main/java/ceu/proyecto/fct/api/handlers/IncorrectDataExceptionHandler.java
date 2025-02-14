package ceu.proyecto.fct.api.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ceu.proyecto.fct.service.IncorrectDateException;

@ControllerAdvice
public class IncorrectDataExceptionHandler {

	@ExceptionHandler(IncorrectDateException.class)
	public ResponseEntity<String> handle(IncorrectDateException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(e.getMessage());
	}

}