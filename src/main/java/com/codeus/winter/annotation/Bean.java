package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method produces a bean to be managed by the registry.
 * Default name for bean is method name.
 * It is possible to set up custom name by annotation attribute value().
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {
    /**
     * The value may indicate a suggestion for a bean name.
     *
     * @return the suggested bean name, if any (or method name otherwise).
     */
    String value() default "";
}
