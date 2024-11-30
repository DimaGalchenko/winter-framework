package com.codeus.winter.annotation;

@Component
public class AutowiredMethodTestClass {

    private DependencyTestClass field;

    public AutowiredMethodTestClass() {
        // default constructor
    }

    @Autowired
    public void setField(DependencyTestClass field) {
        this.field = field;
    }
}
