package com.codeus.winter.annotation;

@Component
public class InitDestroyTestClass {

    private String field;

    @PostConstruct
    public void init() {
        field = "init";
    }

    @PreDestroy
    public void destroy() {
        field = "destroy";
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
