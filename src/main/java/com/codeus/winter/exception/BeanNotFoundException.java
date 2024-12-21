package com.codeus.winter.exception;

import jakarta.annotation.Nullable;

public class BeanNotFoundException extends RuntimeException {
    /**
     * Constructor with message.
     *
     * @param message message.
     */
    public BeanNotFoundException(final String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message message.
     * @param cause   cause.
     */
    public BeanNotFoundException(@Nullable final String message, @Nullable final Throwable cause) {
        super(message, cause);
    }
}
