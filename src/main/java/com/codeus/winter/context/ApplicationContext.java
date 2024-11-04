package com.codeus.winter.context;

import com.codeus.winter.config.BeanFactory;

import javax.annotation.Nullable;

public interface ApplicationContext<T> extends BeanFactory<T> {

    /**
     * Register bean at ApplicationContext
     * @param name bean's name
     * @param bean bean's object
     */
    void registerBean(String name, Object bean);

    /**
     * Up-to-date application context
     */
    void refresh();

}
