package com.codeus.winter.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Unit tests for {@link WinterBeanDefinition}.
 * These tests validate the behavior of the getter and setter methods for bean metadata.
 */
class WinterBeanDefinitionTest {
    private WinterBeanDefinition beanDefinition;

    /**
     * Sets up the test environment by creating a new {@link WinterBeanDefinition} instance.
     */
    @BeforeEach
    void setUp() {
        beanDefinition = new WinterBeanDefinition();
    }

    /**
     * Tests that the bean class name can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetBeanClassName() {
        String className = "com.framework.MagicClass";
        beanDefinition.setBeanClassName(className);

        assertEquals(className, beanDefinition.getBeanClassName(),
                "Bean class name should match the value set");
    }

    /**
     * Tests that the default scope is singleton and can be changed.
     */
    @Test
    void shouldSetAndGetScope() {
        assertEquals(BeanDefinition.SCOPE_SINGLETON, beanDefinition.getScope(),
                "Default scope should be singleton");

        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        assertEquals(BeanDefinition.SCOPE_PROTOTYPE, beanDefinition.getScope(),
                "Scope should match the value set");
    }

    /**
     * Tests that the `isSingleton` method returns the correct value based on the scope.
     */
    @Test
    void shouldIdentifySingletonScope() {
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        assertTrue(beanDefinition.isSingleton(),
                "Bean should be identified as singleton when scope is singleton");

        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        assertFalse(beanDefinition.isSingleton(),
                "Bean should not be identified as singleton when scope is prototype");
    }

    /**
     * Tests that dependencies can be added and retrieved correctly.
     */
    @Test
    void shouldSetAndGetDependsOn() {
        String[] dependencies = {"beanWinter", "beanSpring"};
        beanDefinition.setDependsOn(dependencies);

        assertArrayEquals(dependencies, beanDefinition.getDependsOn(),
                "Dependencies should match the values set");
    }

    /**
     * Tests that the `injectCandidate` property can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetInjectCandidate() {
        assertTrue(beanDefinition.isInjectCandidate(),
                "Default inject candidate should be true");

        beanDefinition.setInjectCandidate(false);
        assertFalse(beanDefinition.isInjectCandidate(),
                "Inject candidate should match the value set");
    }

    /**
     * Tests that the `primary` property can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetPrimary() {
        assertFalse(beanDefinition.isPrimary(),
                "Default primary value should be false");

        beanDefinition.setPrimary(true);
        assertTrue(beanDefinition.isPrimary(),
                "Primary should match the value set");
    }

    /**
     * Tests that the factory bean name can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetFactoryBeanName() {
        String factoryBeanName = "myWinterFactory";
        beanDefinition.setFactoryBeanName(factoryBeanName);

        assertEquals(factoryBeanName, beanDefinition.getFactoryBeanName(),
                "Factory bean name should match the value set");
    }

    /**
     * Tests that the factory method name can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetFactoryMethodName() {
        String factoryMethodName = "createWinterBean";
        beanDefinition.setFactoryMethodName(factoryMethodName);

        assertEquals(factoryMethodName, beanDefinition.getFactoryMethodName(),
                "Factory method name should match the value set");
    }

    /**
     * Tests that the init method name can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetInitMethodName() {
        String initMethodName = "initializeWinter";
        beanDefinition.setInitMethodName(initMethodName);

        assertEquals(initMethodName, beanDefinition.getInitMethodName(),
                "Init method name should match the value set");
    }

    /**
     * Tests that the destroy method name can be set and retrieved correctly.
     */
    @Test
    void shouldSetAndGetDestroyMethodName() {
        String destroyMethodName = "clean";
        beanDefinition.setDestroyMethodName(destroyMethodName);

        assertEquals(destroyMethodName, beanDefinition.getDestroyMethodName(),
                "Destroy method name should match the value set");
    }
}
