package com.codeus.winter.config;

import com.codeus.winter.exception.BeansException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory<Object> {
    private final Map<String, Object> singletonBeans = new HashMap<>();
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();


    @Nullable
    @Override
    public Object getBean(String name) throws BeansException {
        final Object bean = singletonBeans.get(name);
        postProcessors
                .forEach(postProcessor -> postProcessor.postProcessBeforeInitialization(bean, name));
        postProcessors
                .forEach(postProcessor -> postProcessor.postProcessAfterInitialization(bean, name));
        return bean;
    }

    @Nullable
    @Override
    public Object getBean(String name, Class<Object> requiredType) throws BeansException {
        return getBean(name);
    }

    @Nullable
    @Override
    public Object getBean(Class<Object> requiredType) throws BeansException {
        return singletonBeans.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(requiredType::isInstance)
                .findFirst()
                .map(requiredType::cast)
                .orElseThrow(() -> new BeansException("Bean not found for type: " + requiredType.getName()));
    }

    @Override
    public Object createBean(Class<Object> beanClass) throws BeansException {
        Object bean;
        try {
            bean = getBean(beanClass);
        } catch (BeansException beansException) {
            bean = createBean(beanClass);
            singletonBeans.put(bean.getClass().getName(), bean);
        }
        return bean;
    }

    @Override
    public Object createBean(String name) throws BeansException {
        boolean isPresentBean = singletonBeans.entrySet().stream()
                .map(Map.Entry::getKey)
                .anyMatch(key -> key.equals(name));
        if (isPresentBean) {
            return singletonBeans.get(name);
        } else {
            Object newBean = singletonBeans.get(name);
            singletonBeans.put(name, newBean);
            return newBean;
        }
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
}
