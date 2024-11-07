package com.codeus.winter.exception;

import javax.annotation.Nullable;

/**
 * Exception for not uniq BeanDefinition.
 */
public class NotUniqueBeanDefinitionException extends RuntimeException {
    /**
     * Constructor with message.
     *
     * @param message message.
     */
    public NotUniqueBeanDefinitionException(final String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message message.
     * @param cause   cause.
     */
    public NotUniqueBeanDefinitionException(@Nullable final String message, final @Nullable Throwable cause) {
        super(message, cause);
    }
}
