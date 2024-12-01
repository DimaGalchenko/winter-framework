package com.codeus.winter.config;

import com.codeus.winter.exception.BeanFactoryException;
import com.codeus.winter.exception.BeanNotFoundException;
import com.codeus.winter.exception.NotUniqueBeanDefinitionException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of the {@link BeanFactory} interface.
 */
public class DefaultBeanFactory implements BeanFactory {

    private final Map<String, Object> singletonBeans = new HashMap<>();
    private final Map<String, BeanDefinition> beanDefinitions;
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();
    private final Map<String, Object> beanToNameMap = new HashMap<>();

    public DefaultBeanFactory(Map<String, BeanDefinition> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
        initializeBeans();
    }

    /**
     * Request beans from the storage with specified parameters.
     *
     * @param name bean's name.
     * @return bean object if its exist or else throw exception.
     */
    @Nullable
    @Override
    public final Object getBean(@Nonnull final String name) throws BeanNotFoundException {
        if (singletonBeans.containsKey(name)) {
            final Object bean = singletonBeans.get(name);

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
    public final <T> T getBean(@Nonnull final String name,
                         @Nonnull final Class<T> requiredType) throws BeanNotFoundException {
        Object bean = singletonBeans.get(name);

        if (bean == null) {
            throw new BeanNotFoundException(String.format("Bean with a name %s not found", name));
        }

        if (!requiredType.isAssignableFrom(bean.getClass())) {
            throw new BeanNotFoundException(String.format("Bean with a name %s is not compatible with the type %s",
                    name, requiredType.getName()));
        }

        return requiredType.cast(bean);
    }

    /**
     * Request beans from the storage with specified parameters.
     *
     * @param requiredType required class type
     * @return bean object if its exist or else throw exception.
     */
    @Nullable
    @Override
    public final <T> T getBean(@Nonnull final Class<T> requiredType) throws BeanNotFoundException {
        return singletonBeans.values().stream()
                .filter(requiredType::isInstance)
                .findAny()
                .map(requiredType::cast)
                .orElseThrow(() -> new BeanNotFoundException(
                        String.format("Bean not found for type: %s", requiredType.getName())));
    }

    /**
     * Creating bean object with class type.
     *
     * @param beanClass specified bean class.
     * @return bean object if its not possible throw exception.
     */
    @Override
    public final <T> T createBean(@Nonnull final Class<T> beanClass)
            throws NotUniqueBeanDefinitionException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {
        checkBeanClassUniqueness(beanClass);

        Object newBean = beanClass.getDeclaredConstructor().newInstance();
        singletonBeans.put(newBean.getClass().getName(), newBean);
        return beanClass.cast(newBean);
    }

    /**
     * Creating bean object with name.
     *
     * @param name bean's name
     * @return bean object if its not possible throw exception.
     */
    @Override
    public final Object createBean(@Nonnull final String name) throws NotUniqueBeanDefinitionException {
        checkBeanNameUniqueness(name);

        Object newBean = new Object();
        singletonBeans.put(name, newBean);
        return newBean;
    }

    /**
     * Register bean in the bean's storage.
     *
     * @param name           bean's name.
     * @param beanDefinition bean's BeanDefinition.
     * @param beanInstance   bean's instance.
     */
    @Override
    public final void registerBean(@Nonnull final String name,
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
    public final void addBeanPostProcessor(@Nonnull final BeanPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    private void checkBeanNameUniqueness(@Nonnull final String beanName) {
        if (singletonBeans.containsKey(beanName)) {
            throw new NotUniqueBeanDefinitionException(
                    String.format("Bean with name '%s' already exists", beanName));
        }
    }

    private <T> void checkBeanClassUniqueness(@Nonnull final Class<T> beanClass) {
        if (singletonBeans.values().stream().anyMatch(beanClass::isInstance)) {
            throw new NotUniqueBeanDefinitionException(
                    String.format("Bean with type '%s' already exists", beanClass.getName()));
        }
    }

    private void initializeBeans() {
        Map<String, Boolean> initializationStatuses = new HashMap<>();
        List<String> pendingBeans = new ArrayList<>();

        for (Map.Entry<String, BeanDefinition> entry: beanDefinitions.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();

            boolean isInitialized = tryInitializeBean(beanName, beanDefinition, initializationStatuses);

            if (!isInitialized) {
                pendingBeans.add(beanName);
            }
        }

        boolean isAnyBeanResolved;
        while (!pendingBeans.isEmpty()) {
            isAnyBeanResolved = false;
            List<String> stillPendingBeans = new ArrayList<>();

            for (String beanName: pendingBeans) {
                BeanDefinition beanDefinition = beanDefinitions.get(beanName);
                boolean isInitialized = tryInitializeBean(beanName, beanDefinition, initializationStatuses);
                if (isInitialized) {
                    isAnyBeanResolved = true;
                } else {
                    stillPendingBeans.add(beanName);
                }
            }

            if (!isAnyBeanResolved) {
                throw new BeanFactoryException("Unresolved dependencies for beans: " + stillPendingBeans);
            }
            pendingBeans = stillPendingBeans;
        }
    }

    private boolean tryInitializeBean(String beanName, BeanDefinition beanDefinition, Map<String, Boolean> initializationStatuses) {
        if (Boolean.TRUE.equals(initializationStatuses.get(beanName))) {
            return false;
        }

        initializationStatuses.put(beanName, false);

        String[] dependsOn = beanDefinition.getDependsOn();
        if (dependsOn != null) {
            for (String dependency: dependsOn) {
                BeanDefinition dependencyBeanDefinition = beanDefinitions.get(dependency);
                if (dependencyBeanDefinition == null) {
                    throw new BeanFactoryException("Dependency not found for bean: " + dependency);
                }
                tryInitializeBean(beanName, beanDefinition, initializationStatuses);
            }
        }

        Object beanInstance = createBeanInstance(beanName, beanDefinition);
        singletonBeans.put(beanName, beanInstance);

        initializationStatuses.put(beanName, true);
        return true;
    }

    private Object createBeanInstance(String beanName, BeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        if (className == null) {
            throw new BeanFactoryException("Bean class name is not set for bean: " + beanName);
        }

        try {
            Class<?> beanClass = Class.forName(className);
            Object beanInstance = resolveConstructor(beanClass);

            invokeInitMethod(beanDefinition.getInitMethodName(), beanInstance);

            return beanInstance;
        } catch (ClassNotFoundException e) {
            throw new BeanFactoryException("Class with name not found: " + className, e);
        }
    }

    private void invokeInitMethod(String initMethodName, Object beanInstance) {
        if (initMethodName != null) {
            try {
                beanInstance.getClass().getMethod(initMethodName).invoke(beanInstance);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new BeanFactoryException(String.format(
                        "Unable to invoke init method for bean '%s' due to: %s",
                        beanInstance.getClass().getSimpleName(),
                        e.getMessage()
                ), e);
            }
        }
    }

    private Object resolveConstructor(Class<?> beanClass) {
        Constructor<?>[] constructors = beanClass.getConstructors();

        for (Constructor<?> constructor: constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] resolvedDependencies = new Object[parameterTypes.length];
            boolean canResolve = true;

            for (int i = 0; i < parameterTypes.length;  i++) {
                Class<?> dependencyClass = parameterTypes[i];
                Object dependency = singletonBeans.values().stream()
                        .filter(dependencyClass::isInstance)
                        .findFirst()
                        .orElse(null);
                if (dependency == null) {
                    canResolve = false;
                    break;
                }
                resolvedDependencies[i] = dependency;
            }

            if (canResolve) {
                try {
                    return constructor.newInstance(resolvedDependencies);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new BeanFactoryException("Unable to create bean instance due to: " + e.getMessage(), e);
                }
            }
        }
        throw new BeanFactoryException("Unable to resolve dependencies for class: " + beanClass.getName());
    }
}
