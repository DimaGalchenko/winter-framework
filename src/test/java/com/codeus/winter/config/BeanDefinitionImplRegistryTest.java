package com.codeus.winter.config;

import com.codeus.winter.config.impl.BeanDefinitionImpl;
import com.codeus.winter.config.impl.BeanDefinitionRegistryImpl;
import com.codeus.winter.exception.BeanDefinitionStoreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * Unit tests for {@link BeanDefinitionRegistryImpl}.
 * These tests validate the registration, retrieval, and removal of bean definitions
 * while ensuring proper handling of edge cases like duplicates and non-existent beans.
 */
class BeanDefinitionImplRegistryTest {
    private BeanDefinitionRegistryImpl registry;
    private BeanDefinition winterBeanDefinition;

    /**
     * Sets up the test environment by initializing the registry and creating a test bean definition.
     */
    @BeforeEach
    void setUp() {
        registry = new BeanDefinitionRegistryImpl();
        winterBeanDefinition = new BeanDefinitionImpl();
        winterBeanDefinition.setBeanClassName("com.framework");
    }

    /**
     * Tests that a bean definition can be successfully registered.
     */
    @Test
    void shouldRegisterBeanDefinitionSuccessfully() {
        registry.registerBeanDefinition("winterBean", winterBeanDefinition);

        assertTrue(registry.containsBeanDefinition("winterBean"),
                "Registry should contain the registered bean definition");
        assertEquals(winterBeanDefinition, registry.getBeanDefinition("winterBean"),
                "Retrieved bean definition should match the registered definition");
    }

    /**
     * Tests that an exception is thrown when attempting to register a bean definition with a duplicate name.
     */
    @Test
    void shouldThrowExceptionForDuplicateBeanDefinition() {
        registry.registerBeanDefinition("winterBean", winterBeanDefinition);

        BeanDefinitionStoreException exception = assertThrows(
                BeanDefinitionStoreException.class,
                () -> registry.registerBeanDefinition("winterBean", winterBeanDefinition),
                "Expected BeanDefinitionStoreException for duplicate bean registration"
        );

        assertEquals("Cannot register bean definition with name 'winterBean' another bean with the same " +
                "name already exists and overriding is not allowed.", exception.getMessage());
    }

    /**
     * Tests that a bean definition can be removed from the registry.
     */
    @Test
    void shouldRemoveBeanDefinitionSuccessfully() {
        registry.registerBeanDefinition("winterBean", winterBeanDefinition);
        registry.removeBeanDefinition("winterBean");

        assertFalse(registry.containsBeanDefinition("winterBean"),
                "Registry should no longer contain the removed bean definition");
    }

    /**
     * Tests that an exception is thrown when attempting to remove a non-existent bean definition.
     */
    @Test
    void shouldThrowExceptionForRemovingNonExistentBeanDefinition() {
        BeanDefinitionStoreException exception = assertThrows(
                BeanDefinitionStoreException.class,
                () -> registry.removeBeanDefinition("nonExistentBean"),
                "Expected BeanDefinitionStoreException for removing a non-existent bean definition"
        );

        assertEquals("No bean definition found for name 'nonExistentBean'", exception.getMessage());
    }

    /**
     * Tests that the total count of bean definitions in the registry is calculated correctly.
     */
    @Test
    void shouldReturnCorrectBeanDefinitionCount() {
        assertEquals(0, registry.getBeanDefinitionCount(),
                "Registry should initially have zero bean definitions");

        registry.registerBeanDefinition("firstWinterBean", winterBeanDefinition);
        registry.registerBeanDefinition("secondWinterBean", winterBeanDefinition);

        assertEquals(2, registry.getBeanDefinitionCount(),
                "Registry should return the correct number of registered bean definitions");
    }

    /**
     * Tests that the names of all registered beans are returned correctly.
     */
    @Test
    void shouldReturnAllRegisteredBeanDefinitionNames() {
        registry.registerBeanDefinition("firstWinterBean", winterBeanDefinition);
        registry.registerBeanDefinition("secondWinterBean", winterBeanDefinition);

        String[] beanNames = registry.getBeanDefinitionNames();

        assertArrayEquals(new String[]{"firstWinterBean", "secondWinterBean"}, beanNames,
                "Registry should return the correct names of all registered bean definitions");
    }
}
