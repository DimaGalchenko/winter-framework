package com.codeus.winter.context;

import com.codeus.winter.config.PackageScanner;

public interface AnnotationApplicationContext extends ApplicationContext<Object>, PackageScanner {
    void scanPackage(String packageName);

    void registerAnnotation(Class<?> annotationClass);
}
