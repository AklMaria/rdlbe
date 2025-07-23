package com.rdlbe.foundations.exceptions;

public class InvalidParameterException extends RuntimeException {
	private static final long serialVersionUID = -7633011057345587692L;

	public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}
