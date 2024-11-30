package com.codeus.winter.annotation;

@Component
public class DependencyTestClass {

    private String dependency;

    public DependencyTestClass(String dependency) {
        this.dependency = dependency;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }
}
