package com.codeus.winter.config;

import com.codeus.winter.exception.BeanNotFoundException;
import com.codeus.winter.exception.NotUniqueBeanDefinitionException;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link BeanDefinitionRegistry} interface.
 * <p>
 * This class provides a default registry for storing and managing bean definitions.
 * It maintains a map of bean names to their corresponding {@link BeanDefinition} objects,
 * allowing for registration, retrieval, and removal of bean definitions.
 * </p>
 */
public class WinterBeanDefinitionRegistry implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * Registers a new bean definition in the registry.
     *
     * @param beanName       the name of the bean instance to register
     * @param beanDefinition the {@link BeanDefinition} associated with the bean
     * @throws NotUniqueBeanDefinitionException if a bean with the same name is already registered
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (beanDefinitionMap.containsKey(beanName)) {
            throw new NotUniqueBeanDefinitionException(
                    String.format("Bean with name %s is already registered",beanName));
        }
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * Removes the bean definition for the specified bean name from the registry.
     *
     * @param beanName the name of the bean whose definition is to be removed
     * @throws BeanNotFoundException if no bean definition is found for the given name
     */
    @Override
    public void removeBeanDefinition(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new BeanNotFoundException(String.format("No bean definition found for name %s",beanName));
        }
        beanDefinitionMap.remove(beanName);
    }

    /**
     * Retrieves the {@link BeanDefinition} for the specified bean name.
     *
     * @param beanName the name of the bean whose definition is to be retrieved
     * @return the {@link BeanDefinition} for the given name
     * @throws BeanNotFoundException if no bean definition is found for the given name
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new BeanNotFoundException(String.format("No bean definition found for name %s",beanName));
        }
        return beanDefinitionMap.get(beanName);
    }

    /**
     * Checks whether the registry contains a bean definition with the specified name.
     *
     * @param beanName the name of the bean to check
     * @return {@code true} if a bean definition with the given name exists; {@code false} otherwise
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * Returns the names of all beans defined in the registry.
     *
     * @return an array of bean names registered in the registry, or an empty array if no beans are defined
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    /**
     * Returns the total number of beans defined in the registry.
     *
     * @return the number of beans defined in the registry
     */
    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }
}
