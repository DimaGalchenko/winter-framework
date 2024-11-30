package com.codeus.winter.annotation;

@Component
public class DependencyTestClass {

    private String dependency;

    public DependencyTestClass() {
        // default constructor
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }
}
