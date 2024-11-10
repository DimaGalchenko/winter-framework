package com.codeus.winter.context;

import com.codeus.winter.config.*;
import com.codeus.winter.exception.BeanNotFoundException;
import jakarta.annotation.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Standalone application context, accepting component classes as input — in particular
 *
 * @Configuration-annotated classes, but also plain @Component types and JSR-330 compliant classes
 * using jakarta. inject annotations.
 * Allows for registering classes one by one using register(Class...) as well as for classpath scanning
 * using scan(String...).
 */
public class AnnotationApplicationContext implements ApplicationContext, BeanFactory {
    private final String id = ObjectUtils.identityToString(this);
    private String displayName = ObjectUtils.identityToString(this);
    private final ClassPathBeanDefinitionScanner scanner;
    private final DefaultBeanFactory beanFactory;

    public AnnotationApplicationContext(String... basePackages) {
        beanFactory = new DefaultBeanFactory();
        this.scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scanPackages(basePackages);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    /**
     * Set a friendly name for this context.
     * Typically done during initialization of concrete context implementations.
     * <p>Default is the object id of the context instance.
     */
    public void setDisplayName(String displayName) {
        Assert.hasLength(displayName, "Display name must not be empty");
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Nullable
    @Override
    public Object getBean(String name) throws BeanNotFoundException {
        return null;
    }

    @Nullable
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanNotFoundException {
        return null;
    }

    @Nullable
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanNotFoundException {
        return beanFactory.getBean(requiredType);
    }

    @Override
    public <T> T createBean(Class<T> beanClass) throws BeanNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return beanFactory.createBean(beanClass);
    }

    @Override
    public Object createBean(String name) throws BeanNotFoundException {
        return beanFactory.createBean(name);
    }

    @Override
    public void registerBean(String name, BeanDefinition beanDefinition, Object beanInstance) {
        beanFactory.registerBean(name, beanDefinition, beanInstance);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        beanFactory.addBeanPostProcessor(postProcessor);
    }
}
