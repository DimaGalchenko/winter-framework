package com.codeus.winter.config;

import com.codeus.winter.exception.BeanNotFoundException;
import com.codeus.winter.exception.NotUniqueBeanDefinitionException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of the {@link BeanFactory} interface.
 */
public class DefaultBeanFactory implements BeanFactory<Object> {

    private final Map<String, Object> singletonBeans = new HashMap<>();
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();

    /**
     * Request beans from the storage with specified parameters.
     *
     * @param name bean's name.
     * @return bean object if its exist or else throw exception.
     */
    @Nullable
    @Override
    public Object getBean(@Nonnull final String name) throws BeanNotFoundException {
        if (singletonBeans.containsKey(name)) {
            final Object bean = singletonBeans.get(name);
            postProcessors
                    .forEach(postProcessor -> postProcessor.postProcessBeforeInitialization(bean, name));
            postProcessors
                    .forEach(postProcessor -> postProcessor.postProcessAfterInitialization(bean, name));
            return bean;
        } else {
            throw new BeanNotFoundException(String.format("Bean: %s not found", name));
        }
    }

    /**
     * Request beans from the storage with specified parameters.
     *
     * @param name         bean name
     * @param requiredType required class type
     * @return bean object if its exist or else throw exception.
     */
    @Nullable
    @Override
    public Object getBean(@Nonnull final String name,
                          @Nonnull final Class<Object> requiredType) throws BeanNotFoundException {
        Object bean = singletonBeans.get(name);

        if (bean == null) {
            throw new BeanNotFoundException(String.format("Bean with a name %s not found", name));
        }

        if (!requiredType.isAssignableFrom(bean.getClass())) {
            throw new BeanNotFoundException(String.format("Bean with a name %s is not compatible with the type %s",
                    name, requiredType.getName()));
        }

        return bean;
    }

    /**
     * Request beans from the storage with specified parameters.
     * @param requiredType required class type
     * @return bean object if its exist or else throw exception.
     */
    @Nullable
    @Override
    public Object getBean(@Nonnull final Class<Object> requiredType) throws BeanNotFoundException {
        return singletonBeans.values().stream()
                .filter(requiredType::isInstance)
                .findAny()
                .map(requiredType::cast)
                .orElseThrow(() -> new BeanNotFoundException(
                        String.format("Bean not found for type: %s", requiredType.getName())));
    }

    /**
     * Creating bean object with class type.
     * @param beanClass specified bean class.
     * @return bean object if its not possible throw exception.
     */
    @Override
    public Object createBean(@Nonnull final Class<Object> beanClass)
            throws NotUniqueBeanDefinitionException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {
        checkBeanNameUniqueness(beanClass);

        Object newBean = beanClass.getDeclaredConstructor().newInstance();
        singletonBeans.put(newBean.getClass().getName(), newBean);
        return newBean;
    }

    /**
     * Creating bean object with name.
     * @param name bean's name
     * @return bean object if its not possible throw exception.
     */
    @Override
    public Object createBean(@Nonnull final String name) throws NotUniqueBeanDefinitionException {
        checkBeanNameUniqueness(name);

        Object newBean = new Object();
        singletonBeans.put(name, newBean);
        return newBean;
    }

    /**
     * Register bean in the bean's storage.
     * @param name bean's name.
     * @param beanDefinition bean's BeanDefinition.
     * @param beanInstance bean's instance.
     */
    @Override
    public void registerBean(@Nonnull final String name,
                             @Nonnull final BeanDefinition beanDefinition,
                             @Nonnull final Object beanInstance) {
        if (beanDefinition.isSingleton()) {
            singletonBeans.put(name, beanInstance);
        }
        beanDefinitions.put(name, beanDefinition);
    }

    /**
     * Adding BeanPostProcessor to the storage.
     *
     * @param postProcessor BeanPostProcessor.
     */
    @Override
    public void addBeanPostProcessor(@Nonnull final BeanPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    private void checkBeanNameUniqueness(@Nonnull final String beanName) {
        if (singletonBeans.containsKey(beanName)) {
            throw new NotUniqueBeanDefinitionException(
                    String.format("Bean with name '%s' already exists", beanName));
        }
    }

    private void checkBeanNameUniqueness(@Nonnull final Class<Object> beanClass) {
        if (singletonBeans.values().stream().anyMatch(beanClass::isInstance)) {
            throw new NotUniqueBeanDefinitionException(
                    String.format("Bean with type '%s' already exists", beanClass.getName()));
        }
    }
}
