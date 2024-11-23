package com.codeus.winter.annotation;

import com.codeus.winter.config.BeanPostProcessor;
import com.codeus.winter.config.DestructionBeanPostProcessor;
import com.codeus.winter.exception.BeanNotFoundException;
import jakarta.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * BeanPostProcessor implementation that invokes annotated init and destroy methods. Processes
 * methods that annotated with @PostConstruct and @PreDestroy classes
 */
public class InitDestroyAnnotationBeanPostProcessor implements BeanPostProcessor,
    DestructionBeanPostProcessor {

    /**
     * Invoke method that annotated with @PostConstruct after bean properties set.
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
            processInitMethod(bean);
        } catch (Exception e) {
            throw new BeanNotFoundException("Invocation of init method failed: " + beanName, e);
        }
        return bean;
    }

    private void processInitMethod(Object bean)
        throws InvocationTargetException, IllegalAccessException {
        Class<?> beanType = bean.getClass();
        for (Method method : beanType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(bean);
            }
        }
    }

    /**
     * Invoke method that annotated with @PreDestroy before destroy bean.
     *
     *  @param bean bean object
     *  @param beanName bean name
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName)
        throws BeanNotFoundException {
        try {
            processDestroyMethod(bean);
        } catch (Exception e) {
            throw new BeanNotFoundException(
                "Failed to invoke destroy method on bean with name: " + beanName, e);
        }
    }

    private void processDestroyMethod(Object bean)
        throws InvocationTargetException, IllegalAccessException {
        Class<?> beanType = bean.getClass();
        for (Method method : beanType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PreDestroy.class)) {
                method.setAccessible(true);
                method.invoke(bean);
            }
        }
    }
}
