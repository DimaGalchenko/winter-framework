package com.codeus.winter.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanC {
    private BeanA beanA;
    private BeanB beanB;
    private List<Common> list;
    private Set<Common> set;
    private Map<String, Common> map;

    public BeanC(BeanA beanA, BeanB beanB, List<Common> list, Set<Common> set, Map<String, Common> map) {
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

    public List<Common> getList() {
        return list;
    }

    public Set<Common> getSet() {
        return set;
    }

    public Map<String, Common> getMap() {
        return map;
    }
}
