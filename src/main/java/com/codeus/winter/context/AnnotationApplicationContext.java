package com.codeus.winter.context;

import com.codeus.winter.config.PackageScanner;

public interface AnnotationApplicationContext extends ApplicationContext<Object>, PackageScanner {
    /**
     * Scanning specified package.
     *
     * @param packageName package name.
     */
    void scanPackage(String packageName);

    /**
     * Registration annotation.
     *
     * @param annotationClass annotation's class.
     */
    void registerAnnotation(Class<?> annotationClass);
}
