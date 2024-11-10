package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a field or constructor for dependency injection by the framework.
 * Fields or constructors annotated with {@code @Autowired} will have their dependencies
 * automatically injected by the framework’s dependency injection container.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface Autowired {
}
