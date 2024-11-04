package com.codeus.winter.exception;

import javax.annotation.Nullable;

public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException(String message) {
        super(message);
    }

    public BeanNotFoundException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
