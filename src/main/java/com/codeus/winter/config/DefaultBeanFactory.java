package com.codeus.winter.config;

import com.codeus.winter.exception.BeanNotFoundException;
import com.codeus.winter.exception.NotUniqueBeanDefinitionException;
import jakarta.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of the {@link BeanFactory} interface
 */
public class DefaultBeanFactory implements BeanFactory<Object> {
    private final Map<String, Object> singletonBeans = new HashMap<>();
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();


    @Nullable
    @Override
    public Object getBean(String name) throws BeanNotFoundException {
        var isPresent = singletonBeans.entrySet().stream()
                .map(entry -> entry.getKey().equals(name))
                .anyMatch(name::equals);

        if (isPresent) {
            final Object bean = singletonBeans.get(name);
            postProcessors
                    .forEach(postProcessor -> postProcessor.postProcessBeforeInitialization(bean, name));
            postProcessors
                    .forEach(postProcessor -> postProcessor.postProcessAfterInitialization(bean, name));
            return bean;
        } else {
            throw new BeanNotFoundException("Bean: " + name + " not found");
        }
    }

    @Nullable
    @Override
    public Object getBean(String name, Class<Object> requiredType) throws BeanNotFoundException {
        Object bean = singletonBeans.get(name);

        if (bean == null) {
            throw new BeanNotFoundException("Bean with a name '" + name + "' not found.");
        }

        if (!requiredType.isAssignableFrom(bean.getClass())) {
            throw new BeanNotFoundException("Бін з ім'ям '" + name + "' is not compatible with the type '" +
                                            requiredType.getName() + "'.");
        }

        return bean;
    }

    @Nullable
    @Override
    public Object getBean(Class<Object> requiredType) throws BeanNotFoundException {
        return singletonBeans.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(requiredType::isInstance)
                .findFirst()
                .map(requiredType::cast)
                .orElseThrow(() -> new BeanNotFoundException("Bean not found for type: " + requiredType.getName()));
    }

    @Override
    public Object createBean(Class<Object> beanClass)
            throws NotUniqueBeanDefinitionException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {
        uniqueBeanChecking(beanClass);

        Object newBean = beanClass.getDeclaredConstructor().newInstance();
        singletonBeans.put(newBean.getClass().getName(), newBean);
        return newBean;
    }

    @Override
    public Object createBean(String name) throws NotUniqueBeanDefinitionException {
        uniqueBeanChecking(name);

        Object newBean = new Object();
        singletonBeans.put(name, newBean);
        return newBean;
    }

    @Override
    public void registerBean(String name, BeanDefinition beanDefinition, Object beanInstance) {
        if (beanDefinition.isSingleton()) {
            singletonBeans.put(name, beanInstance);
        }
        beanDefinitions.put(name, beanDefinition);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    private void uniqueBeanChecking(String beanName) {
        var isNotUniq = singletonBeans.entrySet().stream()
                .map(entry -> entry.getKey().equals(beanName))
                .anyMatch(beanName::equals);
        if (isNotUniq) {
            throw new NotUniqueBeanDefinitionException("Bean '" + beanName + "' already exists");
        }
    }

    private void uniqueBeanChecking(Class<Object> beanClass) {
        for (String key : singletonBeans.keySet()) {
            if (beanClass.isInstance(singletonBeans.get(key))) {
                throw new NotUniqueBeanDefinitionException("Bean '" + beanClass.getName() + "' already exists");
            }
        }
    }
}
