package com.codeus.winter.config.impl;

import com.codeus.winter.config.BeanDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BeanDefinitionRegistryImplTest {
    private BeanDefinitionRegistryImpl beanFactory;

    @BeforeEach
    void setUp() {
        beanFactory = new BeanDefinitionRegistryImpl();
    }

    @Test
    @DisplayName("Should create a bean using a mocked BeanDefinition")
    void testCreateBeanWithMockBeanDefinition() {
        BeanDefinition mockBeanDefinition = mock(BeanDefinition.class);
        when(mockBeanDefinition.getBeanClassName()).thenReturn("test.MyTestBean");
        when(mockBeanDefinition.isSingleton()).thenReturn(true);

        beanFactory.registerBeanDefinition("testBean", mockBeanDefinition);

        Object bean = beanFactory.getBeanDefinition("testBean");
        assertNotNull(bean, "Bean should not be null");
    }

    @Test
    @DisplayName("Should register and retrieve a BeanDefinition successfully")
    void testRegisterAndRetrieveBeanDefinition() {
        BeanDefinition mockBeanDefinition = mock(BeanDefinition.class);
        when(mockBeanDefinition.getBeanClassName()).thenReturn("test.MyTestBean");

        beanFactory.registerBeanDefinition("testBean", mockBeanDefinition);

        BeanDefinition retrievedDefinition = beanFactory.getBeanDefinition("testBean");

        assertNotNull(retrievedDefinition, "BeanDefinition should not be null");
        assertEquals("test.MyTestBean", retrievedDefinition.getBeanClassName());
    }

    @Test
    @DisplayName("Should check if a BeanDefinition exists in the registry")
    void testContainsBeanDefinition() {
        BeanDefinition mockBeanDefinition = mock(BeanDefinition.class);

        beanFactory.registerBeanDefinition("testBean", mockBeanDefinition);

        assertTrue(beanFactory.containsBeanDefinition("testBean"),
                "Registry should contain 'testBean'");
        assertFalse(beanFactory.containsBeanDefinition("nonExistentBean"),
                "Registry should not contain 'nonExistentBean'");
    }

    @Test
    @DisplayName("Should remove a BeanDefinition from the registry")
    void testRemoveBeanDefinition() {
        BeanDefinition mockBeanDefinition = mock(BeanDefinition.class);

        beanFactory.registerBeanDefinition("testBean", mockBeanDefinition);
        beanFactory.removeBeanDefinition("testBean");

        assertFalse(beanFactory.containsBeanDefinition("testBean"),
                "Registry should not contain 'testBean' after removal");
    }
}