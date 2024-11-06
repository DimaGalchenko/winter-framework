package com.codeus.winter.exception;

import javax.annotation.Nullable;

public class NotUniqueBeanDefinitionException extends RuntimeException {
    public NotUniqueBeanDefinitionException(String message) {
        super(message);
    }

    public NotUniqueBeanDefinitionException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
