package com.codeus.winter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to distinguish between multiple beans of the same type during dependency injection.
 * The {@code @Qualifier} annotation can be applied alongside {@code @Autowired} to provide
 * fine-grained control over dependency resolution in situations where there are multiple candidates.
 * By specifying a unique qualifier name, the framework can locate and inject the correct bean.
 *
 * The {@code @Qualifier} value should match the name of a bean that the framework recognizes.
 * This annotation provides flexibility by allowing different instances of a type to be injected as needed.
 * Used in conjunction with {@code @Autowired} to specify the exact bean to inject when multiple candidates exist.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Qualifier {
    /**
     * The name of the bean to inject.
     * Specifies the unique identifier of the desired bean,
     * allowing the framework to resolve dependencies unambiguously.
     *
     * @return the name of the bean to inject.
     */
    String value();
}
