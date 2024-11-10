package com.codeus.winter.config;

import com.codeus.winter.exception.BeanNotFoundException;
import jakarta.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;

public interface BeanFactory {

    /**
     * Return the unique bean object of this application context for specified name.
     *
     * @param name bean's name.
     * @return the unique bean object of this context, or null if none.
     */
    @Nullable
    Object getBean(String name) throws BeanNotFoundException;

    /**
     * Return the unique bean object of this application context for specified name and cast it specified class type.
     *
     * @param name         bean name
     * @param requiredType required class type
     * @return the unique bean object of this context, or null if none
     */
    @Nullable
    <T> T getBean(String name, Class<T> requiredType) throws BeanNotFoundException;

    /**
     * Return the unique bean object of this application context for specified class type.
     *
     * @param requiredType required class type
     * @return the unique bean object of this context, or null if none
     */
    @Nullable
    <T> T getBean(Class<T> requiredType) throws BeanNotFoundException;

    /**
     * Create bean for specified bean class.
     *
     * @param beanClass specified bean class.
     * @return bean for specified bean class.
     */
    <T> T createBean(Class<T> beanClass) throws BeanNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Create bean for specified name.
     *
     * @param name bean's name
     * @return bean object for specified bean's name
     */
    Object createBean(String name) throws BeanNotFoundException;

    /**
     * Register bean for its name, BeanDefinition and instance.
     *
     * @param name           bean's name.
     * @param beanDefinition bean's BeanDefinition.
     * @param beanInstance   bean's instance.
     */
    void registerBean(String name, BeanDefinition beanDefinition, Object beanInstance);

    /**
     * Adding BeanPostProcessor.
     *
     * @param postProcessor BeanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor postProcessor);
}
