package com.codeus.winter.annotation;

@Component
public class AutowiredMethodTestClass {

    private DependencyTestClass field;

    public AutowiredMethodTestClass() {
    }

    @Autowired
    public void setField(DependencyTestClass field) {
        this.field = field;
    }
}
