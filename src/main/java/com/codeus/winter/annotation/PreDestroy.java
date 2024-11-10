package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with {@code @PreDestroy} will be called by the framework when an object
 * is being destroyed, enabling resource cleanup or other finalization tasks,
 * which is especially useful for releasing external resources.
 * Only one method per class should be annotated with @PreDestroy.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreDestroy {
}
