package com.codeus.winter.exception;

public class PropertySourceException extends RuntimeException {
    /**
     * Constructor with message.
     *
     * @param message text message.
     */
    public PropertySourceException(final String message) {
        super(message);
    }
}
