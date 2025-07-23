package com.rdlbe.foundations.exceptions;

public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 7440063942944779088L;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
