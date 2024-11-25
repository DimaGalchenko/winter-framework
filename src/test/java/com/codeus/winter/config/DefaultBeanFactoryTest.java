package com.codeus.winter.config;

import com.codeus.winter.exception.BeanNotFoundException;
import com.codeus.winter.exception.NotUniqueBeanDefinitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultBeanFactoryTest {
    private DefaultBeanFactory beanFactory;
    private final String TEST_BEAN_NAME = "Name";

    @BeforeEach
    void setUpBeforeClass() {
        beanFactory = new DefaultBeanFactory();
    }

    @Test
    @DisplayName("Test getBean(String) with 'name'; " +
                 "when bean with 'name' is missing; " +
                 "then throw BeanNotFoundException")
    void testGetBeanWithNameWhereBeanIsMissing() throws BeanNotFoundException {
        assertThrows(BeanNotFoundException.class,
                () -> beanFactory.getBean(TEST_BEAN_NAME));
    }

    @Test
    @DisplayName("Test getBean(String) with 'name'; " +
                 "when bean with 'name' is missing; " +
                 "then gets the bean")
    void testGetBeanWithName() throws BeanNotFoundException {
        var expectedBean = beanFactory.createBean(TEST_BEAN_NAME);
        var actual = beanFactory.getBean(TEST_BEAN_NAME);

        assertEquals(expectedBean, actual);
    }

    @Test
    @DisplayName("Test getBean(String, Class) with 'name', 'requiredType'; " +
                 "when bean with class type of 'requiredType' is missing; " +
                 "then throw BeanNotFoundException")
    void testGetBeanWithNameRequiredTypeWhereBeanIsMissing() throws BeanNotFoundException {
        Class<Object> requiredType = Object.class;

        assertThrows(BeanNotFoundException.class,
                () -> beanFactory.getBean(TEST_BEAN_NAME, requiredType));
    }

    @Test
    @DisplayName("Test getBean(String, Class) with 'name', 'requiredType'")
    void testGetBeanWithNameRequiredType() throws BeanNotFoundException {
        var requiredType = Object.class;
        var expected = beanFactory.createBean(TEST_BEAN_NAME);
        var actual = beanFactory.getBean(TEST_BEAN_NAME, requiredType);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test getBean(Class) with 'requiredType'; " +
                 "when 'java.lang.Object'; " +
                 "then throw BeanNotFoundException")
    void testGetBeanWithRequiredTypeWhenJavaLangObjectThenThrowBeanNotFoundException() throws BeanNotFoundException {
        Class<Object> requiredType = Object.class;

        assertThrows(BeanNotFoundException.class, () -> beanFactory.getBean(requiredType));
    }

    @Test
    @DisplayName("Test getBean(Class) with 'requiredType'")
    void testGetBeanWithRequiredType() throws BeanNotFoundException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        var requiredType = Object.class;
        var expected = beanFactory.createBean(requiredType);
        var actual = beanFactory.getBean(requiredType);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test createBean(Class) with 'beanClass'; when 'java.lang.Object'")
    void testCreateBeanWithBeanClassWhenJavaLangObject() throws NotUniqueBeanDefinitionException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        var beanClass = Object.class;
        var actual = beanFactory.createBean(beanClass);
        var expected = beanFactory.getBean(beanClass);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test createBean(String) with 'name'")
    void testCreateBeanWithName() throws NotUniqueBeanDefinitionException {
        var actual = beanFactory.createBean(TEST_BEAN_NAME);
        var expected = beanFactory.getBean(TEST_BEAN_NAME);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test createBean(String) with 'name'; " +
                 "when 'name' is not uniq bean's name; " +
                 "then throw NotUniqueBeanDefinitionException")
    void testCreateBeanWithNotUniqNameAndThrowsException() throws NotUniqueBeanDefinitionException {
        beanFactory.createBean(TEST_BEAN_NAME);

        assertThrows(NotUniqueBeanDefinitionException.class,
                () -> beanFactory.createBean(TEST_BEAN_NAME));
    }

    @Test
    @DisplayName("Test createBean(Class) with 'requiredType'; " +
                 "when class type 'requiredType' is not uniq; " +
                 "then throw NotUniqueBeanDefinitionException")
    void testCreateBeanWithNotUniqRequiredClassTypeAndThrowsException()
            throws NotUniqueBeanDefinitionException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {
        var requiredType = Object.class;
        beanFactory.createBean(requiredType);

        assertThrows(NotUniqueBeanDefinitionException.class,
                () -> beanFactory.createBean(requiredType));
    }

    @Test
    @DisplayName("Test registerBean(String, BeanDefinition, Object)")
    @Disabled("BeanDefinitionImp is not realized yet")
    void testRegisterBean() {
        var beanName = TEST_BEAN_NAME;
        var beanInstance = beanFactory.createBean(beanName);
        beanFactory.registerBean(beanName, (BeanDefinition) new Object(), beanInstance);
        var expected = beanFactory.getBean(beanName, (Class<Object>) beanInstance.getClass());

        assertEquals(expected, beanInstance);
    }

    @Test
    @DisplayName("Test addBeanPostProcessor(BeanPostProcessor)")
    @Disabled("BeanPostProcessorImp is not realized yet")
    void testAddBeanPostProcessor() throws NoSuchFieldException, IllegalAccessException {
        beanFactory.addBeanPostProcessor((BeanPostProcessor) new Object());

        Field field = DefaultBeanFactory.class.getDeclaredField("postProcessors");
        field.setAccessible(true);
        @SuppressWarnings("unchecked") var postProcessors = (List<BeanPostProcessor>) field.get(beanFactory);

        assertNotNull(postProcessors);
    }
}
