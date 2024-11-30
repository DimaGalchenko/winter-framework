package com.codeus.winter.annotation;

@Component
public class AutowiredConstructorTestClass {

    private DependencyTestClass field;

    public AutowiredConstructorTestClass() {
    }

    @Autowired
    public AutowiredConstructorTestClass(DependencyTestClass field) {
        this.field = field;
    }

    public DependencyTestClass getField() {
        return field;
    }

    public void setField(DependencyTestClass field) {
        this.field = field;
    }
}
