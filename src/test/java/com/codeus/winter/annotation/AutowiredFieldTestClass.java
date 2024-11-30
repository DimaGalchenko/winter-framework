package com.codeus.winter.annotation;

@Component
public class AutowiredFieldTestClass {

    @Autowired
    private DependencyTestClass field;


    public AutowiredFieldTestClass() {
        // default constructor
    }
}
