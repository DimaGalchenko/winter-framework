package com.codeus.winter.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanC {
    private BeanA beanA;
    private BeanB beanB;
    private List<BeanD> list;
    private Set<BeanD> set;
    private Map<String, BeanD> map;

    public BeanC(BeanA beanA, BeanB beanB, List<BeanD> list, Set<BeanD> set, Map<String, BeanD> map) {
        this.beanA = beanA;
        this.beanB = beanB;
        this.list = list;
        this.set = set;
        this.map = map;
    }

    public BeanA getBeanA() {
        return beanA;
    }

    public BeanB getBeanB() {
        return beanB;
    }

    public List<BeanD> getList() {
        return list;
    }

    public Set<BeanD> getSet() {
        return set;
    }

    public Map<String, BeanD> getMap() {
        return map;
    }
}
