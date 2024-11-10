package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks a method to be executed after the dependency injection is complete.
 * Methods annotated with {@code @PostConstruct} will be invoked by the framework
 * after all fields have been injected.
 * This allows for additional configuration or setup before the bean is used.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostConstruct {
}
