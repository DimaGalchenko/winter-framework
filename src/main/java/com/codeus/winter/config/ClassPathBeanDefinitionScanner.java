package com.codeus.winter.config;

/**
 * A bean definition scanner that detects bean candidates on the classpath,
 * registering corresponding bean definitions with a given registry ({@code BeanFactory}
 * or {@code ApplicationContext}).
 **/
public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;

    /**
     * Create a new {@code ClassPathBeanDefinitionScanner} for the given bean factory.
     *
     * @param registry the {@code BeanFactory} to load bean definitions into, in the form
     *                 of a {@code BeanDefinitionRegistry}
     */
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * Perform a scan within the specified base packages.
     *
     * @param basePackages the packages to check for annotated classes
     * @return number of beans registered
     */
    public int scanPackages(String... basePackages) {
        return registry.getBeanDefinitionCount();
    }
}
