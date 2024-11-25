package com.codeus.winter.annotation;

import com.codeus.winter.config.BeanFactory;
import com.codeus.winter.config.BeanPostProcessor;
import com.codeus.winter.exception.BeanNotFoundException;
import jakarta.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


/**
 * BeanPostProcessor implementation that autowires annotated fields, setter methods, and constructor.
 *
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    /**
     * Set BeanFactory as dependency.
     *
     * @param beanFactory bean factory
     */
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Resolve dependency injection for constructors/methods/fields with @Autowired annotation.
     *
     * @param bean bean object
     * @param beanName bean name
     * @return bean object
     */
    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeanNotFoundException {
        try {
            injectConstructor(bean);
            injectMethod(bean);
            injectField(bean);
        } catch (Exception e) {
            throw new BeanNotFoundException("Bean post processing failed: " + beanName, e);
        }
        return bean;
    }

    private void injectMethod(Object bean) throws InvocationTargetException, IllegalAccessException {
        Class<?> beanType = bean.getClass();
        for (Method method : beanType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Autowired.class)) {
                for (Parameter parameter : method.getParameters()) {
                    Object dependency = beanFactory.getBean(parameter.getType());
                    method.setAccessible(true);
                    method.invoke(bean, dependency);
                }
            }
        }
    }

    private void injectField(Object bean) throws IllegalAccessException {
        Class<?> beanType = bean.getClass();
        for (Field field : beanType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = beanFactory.getBean(field.getType());
                field.setAccessible(true);
                field.set(bean, dependency);
            }
        }
    }

    private void injectConstructor(Object bean) throws IllegalAccessException {
        Class<?> beanType = bean.getClass();
        for (Constructor constructor : beanType.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                for (Parameter parameter : constructor.getParameters()) {
                    Class<?> type = parameter.getType();
                    Object dependency = beanFactory.getBean(type);
                    Field field = getFieldByType(beanType.getDeclaredFields(), type);
                    field.setAccessible(true);
                    field.set(bean, dependency);
                }
            }
        }
    }

    private Field getFieldByType(Field[] declaredFields, Class<?> type) {
        Field field = null;
        for (Field declaredField : declaredFields) {
            if (declaredField.getType().equals(type)) {
                field = declaredField;
            } else {
                throw new IllegalArgumentException(String.format("Field '%s' not found", type));
            }
        }
        return field;
    }
}
