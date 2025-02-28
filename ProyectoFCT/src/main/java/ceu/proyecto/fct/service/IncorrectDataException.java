package ceu.proyecto.fct.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4200403395223902741L;

	public IncorrectDataException() {
		// TODO Auto-generated constructor stub
	}

	public IncorrectDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IncorrectDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public IncorrectDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IncorrectDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
