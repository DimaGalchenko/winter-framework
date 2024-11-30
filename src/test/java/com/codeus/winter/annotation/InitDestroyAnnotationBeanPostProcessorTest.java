package com.codeus.winter.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codeus.winter.config.DefaultBeanFactory;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class InitDestroyAnnotationBeanPostProcessorTest {

    private static final String INIT_DESTROY_BEAN_NAME = "InitDestroyTestClass";
    private DefaultBeanFactory beanFactory;
    private InitDestroyAnnotationBeanPostProcessor postProcessor;

    @BeforeEach
    void setUpBeforeClass() {
        beanFactory = new DefaultBeanFactory();
        postProcessor = new InitDestroyAnnotationBeanPostProcessor();
        createBeanTestClass();
    }

    @Test
    void initPostConstructAnnotation() throws IllegalAccessException {
        // given
        var bean = beanFactory.getBean(InitDestroyTestClass.class);

        // when
        Object actual = postProcessor.postProcessBeforeInitialization(bean, INIT_DESTROY_BEAN_NAME);

        // then
        Field field = actual.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        assertEquals("init", field.get(actual));
    }

    @Test
    void destroyPreDestroyAnnotation() throws IllegalAccessException {
        // given
        var bean = beanFactory.getBean(InitDestroyTestClass.class);

        // when
        postProcessor.postProcessBeforeDestruction(bean, INIT_DESTROY_BEAN_NAME);

        // then
        Field field = bean.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        assertEquals("destroy", field.get(bean));
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
