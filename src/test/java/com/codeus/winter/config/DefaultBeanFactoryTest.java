package com.codeus.winter.config;

import com.codeus.winter.exception.BeanFactoryException;
import com.codeus.winter.exception.BeanNotFoundException;
import com.codeus.winter.test.BeanA;
import com.codeus.winter.test.BeanB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultBeanFactoryTest {
    private final BeanDefinition beanDefinitionA = mock(BeanDefinition.class);
    private final BeanDefinition beanDefinitionB = mock(BeanDefinition.class);

    @BeforeEach
    void setUpBeforeEach() {
        when(beanDefinitionA.getBeanClassName()).thenReturn("com.codeus.winter.test.BeanA");
        when(beanDefinitionA.isSingleton()).thenReturn(true);

        when(beanDefinitionB.getBeanClassName()).thenReturn("com.codeus.winter.test.BeanB");
        when(beanDefinitionB.isSingleton()).thenReturn(true);
        when(beanDefinitionB.getDependsOn()).thenReturn(new String[]{"BeanA"});
    }

    @Test
    @DisplayName("Should initialize one bean without dependency")
    void testInitializeSingleBeanAWithoutDependency() {
        DefaultBeanFactory factory = new DefaultBeanFactory(
                Map.of("BeanA", beanDefinitionA)
        );

        BeanA beanA = factory.getBean(BeanA.class);
        assertNotNull(beanA);
    }

    @Test
    @DisplayName("Should initialize two bean with dependencies in order:" +
            "second bean definition depends on first bean definition")
    void testInitializeBeansWithDependencyInOrder() {
        DefaultBeanFactory factory = new DefaultBeanFactory(
                Map.of("BeanA", beanDefinitionA,
                        "BeanB", beanDefinitionB)
        );

        BeanA beanA = factory.getBean(BeanA.class);
        assertNotNull(beanA);
        BeanB beanB = factory.getBean(BeanB.class);
        assertNotNull(beanB);
        assertNotNull(beanB.getBeanA());
        assertEquals(beanA, beanB.getBeanA());
    }

    @Test
    @DisplayName("Should initialize two bean with dependencies in reverse order:" +
            "first bean definition depends on second bean definition")
    void testInitializeBeansWithDependencyInReverseOrder() {
        Map<String, BeanDefinition> beanDefinitionMap = new LinkedHashMap<>();
        beanDefinitionMap.put("BeanB", beanDefinitionB);
        beanDefinitionMap.put("BeanA", beanDefinitionA);

        DefaultBeanFactory factory = new DefaultBeanFactory(beanDefinitionMap);

        BeanA beanA = factory.getBean(BeanA.class);
        assertNotNull(beanA);
        BeanB beanB = factory.getBean(BeanB.class);
        assertNotNull(beanB);
        assertNotNull(beanB.getBeanA());
        assertEquals(beanA, beanB.getBeanA());
    }

    @Test
    @DisplayName("Should throw exception when bean definitions does not contain dependency bean")
    void testThrowExceptionWhenBeanDefinitionsDoesNotContainDependencyBean() {
        Map<String, BeanDefinition> beanDefinitionMap = new LinkedHashMap<>();
        beanDefinitionMap.put("BeanB", beanDefinitionB);
        beanDefinitionMap.put("BeanA", beanDefinitionA);
        when(beanDefinitionB.getDependsOn()).thenReturn(new String[]{"BeanA", "BeanC"});

        BeanFactoryException beanFactoryException = assertThrows(BeanFactoryException.class, () ->
                new DefaultBeanFactory(beanDefinitionMap)
        );

        assertEquals("Dependency not found for bean: BeanC", beanFactoryException.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when bean definition does not contain class name")
    void testThrowExceptionWhenBeanDefinitionsDoesNotContainClassName() {
        Map<String, BeanDefinition> beanDefinitionMap = new LinkedHashMap<>();
        beanDefinitionMap.put("BeanA", beanDefinitionA);
        when(beanDefinitionA.getBeanClassName()).thenReturn(null);

        BeanFactoryException beanFactoryException = assertThrows(BeanFactoryException.class, () ->
                new DefaultBeanFactory(beanDefinitionMap)
        );

        assertEquals("Bean class name is not set for bean: BeanA", beanFactoryException.getMessage());
    }

    @Test
    @DisplayName("Should get bean object by bean name")
    void testGetBeanByBeanName() {
        BeanFactory factory = new DefaultBeanFactory(
                Map.of("BeanA", beanDefinitionA)
        );

        Object actual = factory.getBean("BeanA");
        assertNotNull(actual);
        assertEquals(BeanA.class, actual.getClass());
    }

    @Test
    @DisplayName("Should throw exception when try to get by bean name but factory does not contain bean")
    void testGetBeanThrowExceptionWhenBeanIsNull() {
        BeanFactory factory = new DefaultBeanFactory(new HashMap<>());

        BeanNotFoundException exception = assertThrows(BeanNotFoundException.class, () -> factory.getBean("BeanA"));
        assertEquals("Bean: BeanA not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should get bean object by bean name and typeToken")
    void testGetBeanByBeanNameAndType() {
        BeanFactory factory = new DefaultBeanFactory(
                Map.of("BeanA", beanDefinitionA)
        );

        BeanA actual = factory.getBean("BeanA", BeanA.class);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Should throw exception when try to get by bean name and bean type but factory does not contain bean")
    void testGetBeanByNameAndTypeThrowExceptionWhenBeanIsNull() {
        String beanName = "BeanA";
        BeanFactory factory = new DefaultBeanFactory(
                Map.of(beanName, beanDefinitionA)
        );

        BeanNotFoundException exception = assertThrows(BeanNotFoundException.class,
                () -> factory.getBean(beanName, BeanB.class));
        assertEquals(String.format(
                        "Bean with a name %s is not compatible with the type %s",
                        beanName,
                        BeanB.class.getName()),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Should get bean object by bean type")
    void testGetBeanByBeanType() {
        BeanFactory factory = new DefaultBeanFactory(
                Map.of("BeanA", beanDefinitionA)
        );

        Object actual = factory.getBean(BeanA.class);
        assertNotNull(actual);
        assertEquals(BeanA.class, actual.getClass());
    }

    @Test
    @DisplayName("Should throw exception when try to get by bean type but factory does not contain bean")
    void testGetBeanByTypeThrowExceptionWhenBeanIsNull() {
        BeanFactory factory = new DefaultBeanFactory(new HashMap<>());

        BeanNotFoundException exception = assertThrows(BeanNotFoundException.class, () -> factory.getBean(BeanA.class));
        assertEquals("Bean not found for type: " + BeanA.class.getName(), exception.getMessage());
    }

}
