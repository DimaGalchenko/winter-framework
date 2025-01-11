package com.codeus.winter.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanD {
    private List<Common> list;
    private Set<Common> set;
    private Map<String, Common> map;

    public BeanD(List<Common> list, Set<Common> set, Map<String, Common> map) {
        this.list = list;
        this.set = set;
        this.map = map;
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
