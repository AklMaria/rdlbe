package com.rdlbe.foundations.exceptions;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5907915962706991625L;

	public NotFoundException(String message) {
        super(message);
    }
}
