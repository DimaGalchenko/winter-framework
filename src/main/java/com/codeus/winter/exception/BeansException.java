package com.codeus.winter.exception;

import javax.annotation.Nullable;

public class BeansException extends RuntimeException {
    public BeansException(String message) {
        super(message);
    }

    public BeansException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
