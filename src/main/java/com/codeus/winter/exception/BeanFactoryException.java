package com.codeus.winter.exception;

public class BeanFactoryException extends RuntimeException {
    public BeanFactoryException(String message) {
        super(message);
    }

    public BeanFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
