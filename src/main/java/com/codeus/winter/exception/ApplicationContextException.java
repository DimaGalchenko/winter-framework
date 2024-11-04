package com.codeus.winter.exception;

public class ApplicationContextException extends RuntimeException {
    public ApplicationContextException(String message) {
        super(message);
    }

    public ApplicationContextException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
