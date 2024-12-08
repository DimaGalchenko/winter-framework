package com.codeus.winter.config;

import com.codeus.winter.annotation.Component;
import com.codeus.winter.annotation.Bean;
import com.codeus.winter.exception.NotUniqueBeanDefinitionException;

import java.util.Set;

/**
 * Responsible for scanning a package for classes annotated with {@link Component} or {@link Bean},
 * and registering their {@link BeanDefinition}s in the provided {@link BeanDefinitionRegistry}.
 */
public class WinterPackageBeanRegistration {

    private final PackageScanner packageScanner;
    private final BeanDefinitionRegistry registry;

    /**
     * Constructor to initialize the package scanner and the registry.
     *
     * @param packageScanner the {@link PackageScanner} for scanning annotated classes
     * @param registry       the {@link BeanDefinitionRegistry} to register bean definitions
     */
    public WinterPackageBeanRegistration(PackageScanner packageScanner, BeanDefinitionRegistry registry) {
        this.packageScanner = packageScanner;
        this.registry = registry;
    }

    /**
     * Scans the specified package for classes annotated with {@link Component} or {@link Bean}
     * and registers their {@link BeanDefinition}s in the registry.
     *
     * @param packageName the package to scan for annotated classes
     */
    public void registerBeans(String packageName) {
        Set<Class<?>> componentClasses = packageScanner.findClassesWithAnnotations(
                packageName, Set.of(Component.class, Bean.class)
        );

        for (Class<?> clazz : componentClasses) {
            String beanName = getBeanName(clazz);

            WinterBeanDefinition beanDefinition = new WinterBeanDefinition();
            beanDefinition.setBeanClassName(clazz.getName());
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanDefinition.setInjectCandidate(true);

            if (!registry.containsBeanDefinition(beanName)) {
                registry.registerBeanDefinition(beanName, beanDefinition);
            } else {
                throw new NotUniqueBeanDefinitionException(
                        String.format("A bean with the name '%s' is already defined in the registry.", beanName)
                );
            }
        }
    }

    /**
     * Generates a bean name based on the class name.
     * <p>
     * This logic can be replaced with more robust naming strategies if needed.
     *
     * @param clazz the class for which to generate the bean name
     * @return the generated bean name
     */
    private String getBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
