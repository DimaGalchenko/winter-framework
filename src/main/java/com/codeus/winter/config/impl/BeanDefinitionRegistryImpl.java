package com.codeus.winter.config.impl;

import com.codeus.winter.config.BeanDefinition;
import com.codeus.winter.config.BeanDefinitionRegistry;
import com.codeus.winter.exception.BeanDefinitionStoreException;

import java.util.HashMap;
import java.util.Map;

public class BeanDefinitionRegistryImpl implements BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (beanDefinitionMap.containsKey(beanName)) {
            throw new BeanDefinitionStoreException("Cannot register bean definition with name '" + beanName +
                    "': another bean with the same name already exists and overriding is not allowed.");
        }
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new BeanDefinitionStoreException("No bean definition found for name '" + beanName + "'");
        }
        beanDefinitionMap.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }
}
