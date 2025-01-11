package com.codeus.winter.test;

public class BeanC {
    private BeanA beanA;
    private BeanB beanB;

    public BeanC(BeanA beanA, BeanB beanB) {
        this.beanA = beanA;
        this.beanB = beanB;
    }

    public BeanA getBeanA() {
        return beanA;
    }

    public BeanB getBeanB() {
        return beanB;
    }
}
