package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a class is a configuration class for the framework.
 * A class annotated with {@code @Configuration} is marked as a configuration source
 * for the application context, providing one or more {@code @Bean}-annotated
 * methods to create and manage beans.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {
}
