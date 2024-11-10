package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a component, that will be managed by registry.
 * Classes are automatically detected and registered
 * within the framework’s dependency injection container.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    /**
     * Optional element to specify a custom name for the component.
     * If not provided, the class name will be used as the default.
     *
     * @return The name of the component.
     */
    String value() default "";
}
