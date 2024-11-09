package com.codeus.winter.exception;

public class ApplicationContextException extends RuntimeException {
    /**
     * Constructor with message.
     *
     * @param message text message.
     */
    public ApplicationContextException(final String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param msg   text message.
     * @param cause cause.
     */
    public ApplicationContextException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
