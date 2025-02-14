package ceu.proyecto.fct.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WrongUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6442452699793592480L;

	public WrongUserException() {
		// TODO Auto-generated constructor stub
	}

	public WrongUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WrongUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
