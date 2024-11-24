package com.codeus.winter.config;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface PackageScanner {
    /**
     * Scan the specified package and find all classes annotated with the given annotations.
     *
     * @param packageName the package to scan
     * @param annotations the annotations to look for
     * @return a set of classes annotated with any of the specified annotations
     */
    Set<Class<?>> findClassesWithAnnotations(String packageName, Set<Class<? extends Annotation>> annotations);
}
