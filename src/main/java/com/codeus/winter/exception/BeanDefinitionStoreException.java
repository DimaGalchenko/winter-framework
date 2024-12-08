package com.codeus.winter.exception;

import javax.annotation.Nullable;

public class BeanDefinitionStoreException extends RuntimeException {
    /**
     * Constructor with message.
     *
     * @param message message.
     */
    public BeanDefinitionStoreException(final String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message message.
     * @param cause   cause.
     */
    public BeanDefinitionStoreException(@Nullable final String message, @Nullable final Throwable cause) {
        super(message, cause);
    }
}
