package com.codeus.winter.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codeus.winter.config.DefaultBeanFactory;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class AutowiredAnnotationBeanPostProcessorTest {

    private static final String AUTOWIRED_FIELD_BEAN_NAME = "AutowiredFieldTestClass";
    private static final String AUTOWIRED_METHOD_BEAN_NAME = "AutowiredMethodTestClass";
    private DefaultBeanFactory beanFactory;
    private AutowiredAnnotationBeanPostProcessor postProcessor;

    @BeforeEach
    void setUpBeforeClass() {
        beanFactory = new DefaultBeanFactory(new HashMap<>());
        postProcessor = new AutowiredAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(beanFactory);
        createBeanTestClass();
    }

    @Test
    void injectField() {
        // given
        var bean = beanFactory.getBean(AutowiredFieldTestClass.class);

        // when
        Object actual = postProcessor.postProcessBeforeInitialization(bean,
            AUTOWIRED_FIELD_BEAN_NAME);

        // then
        Field dependency = actual.getClass().getDeclaredFields()[0];
        dependency.setAccessible(true);
        assertEquals(DependencyTestClass.class, dependency.getType());
    }

    @Test
    void injectMethod() {
        // given
        var bean = beanFactory.getBean(AutowiredMethodTestClass.class);

        // when
        Object actual = postProcessor.postProcessBeforeInitialization(bean,
            AUTOWIRED_METHOD_BEAN_NAME);

        // then
        Field dependency = actual.getClass().getDeclaredFields()[0];
        dependency.setAccessible(true);
        assertEquals(DependencyTestClass.class, dependency.getType());
    }

    private void createBeanTestClass() {
        Reflections refelections = new Reflections("com.codeus.winter.annotation");
        Set<Class<?>> classes = refelections.getTypesAnnotatedWith(Component.class);
        classes.forEach(clazz -> {
            try {
                beanFactory.createBean(clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
