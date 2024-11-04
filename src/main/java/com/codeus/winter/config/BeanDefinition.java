package com.codeus.winter.config;

import jakarta.annotation.Nullable;

/**
 * A BeanDefinition describes a bean instance, which has property values,
 * constructor argument values, and further information supplied by
 * concrete implementations.
 **/
public interface BeanDefinition {
    /**
     * Scope identifier for the standard singleton scope: {@value}.
     */
    String SCOPE_SINGLETON = Scope.SINGLETON.toString();

    /**
     * Scope identifier for the standard prototype scope: {@value}.
     */
    String SCOPE_PROTOTYPE = Scope.PROTOTYPE.toString();

    /**
     * @param beanClassName Specify the bean class name of this bean definition.
     */
    void setBeanClassName(@Nullable String beanClassName);

    /**
     * @return the current bean class name of this bean definition
     */
    @Nullable
    String getBeanClassName();

    /**
     * Override the target scope of this bean, specifying a new scope name.
     * @see #SCOPE_SINGLETON
     * @see #SCOPE_PROTOTYPE
     */
    void setScope(@Nullable String scope);

    /**
     * Return the name of the current target scope for this bean,
     * or {@code null} if not known yet.
     */
    @Nullable
    String getScope();

    default boolean isSingleton() {
        return true;
    }

    /**
     * Set the names of the beans that this bean depends on being initialized.
     * The bean factory will guarantee that these beans get initialized first.
     */
    void setDependsOn(@Nullable String... dependsOn);

    /**
     * Return the bean names that this bean depends on.
     */
    @Nullable
    String[] getDependsOn();

    /**
     * Set whether this bean is a candidate for getting autowired into some other bean.
     * <p>Note that this flag is designed to only affect type-based autowiring.
     * It does not affect explicit references by name, which will get resolved even
     * if the specified bean is not marked as an autowire candidate. As a consequence,
     * autowiring by name will nevertheless inject a bean if the name matches.
     */
    void setAutowireCandidate(boolean autowireCandidate);

    /**
     * Return whether this bean is a candidate for getting autowired into some other bean.
     */
    boolean isAutowireCandidate();

    /**
     * Set whether this bean is a primary autowire candidate.
     * <p>If this value is {@code true} for exactly one bean among multiple
     * matching candidates, it will serve as a tie-breaker.
     */
    void setPrimary(boolean primary);

    /**
     * Return whether this bean is a primary autowire candidate.
     */
    boolean isPrimary();

    /**
     * Specify the factory bean to use, if any.
     * This the name of the bean to call the specified factory method on.
     * @see #setFactoryMethodName
     */
    void setFactoryBeanName(@Nullable String factoryBeanName);

    /**
     * Return the factory bean name, if any.
     */
    @Nullable
    String getFactoryBeanName();

    /**
     * Specify a factory method, if any. This method will be invoked with
     * constructor arguments, or with no arguments if none are specified.
     * The method will be invoked on the specified factory bean, if any,
     * or otherwise as a static method on the local bean class.
     * @see #setFactoryBeanName
     * @see #setBeanClassName
     */
    void setFactoryMethodName(@Nullable String factoryMethodName);

    /**
     * Return a factory method, if any.
     */
    @Nullable
    String getFactoryMethodName();

    /**
     * Set the name of the initializer method.
     */
    void setInitMethodName(@Nullable String initMethodName);

    /**
     * Return the name of the initializer method.
     */
    @Nullable
    String getInitMethodName();

    /**
     * Set the name of the destroy method.
     */
    void setDestroyMethodName(@Nullable String destroyMethodName);

    /**
     * Return the name of the destroy method.
     */
    @Nullable
    String getDestroyMethodName();
}
