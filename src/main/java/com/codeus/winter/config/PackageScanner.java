package com.codeus.winter.config;

import java.util.Set;

/**
 * PackageScanner interface contains common methods for parsing components.
 */
public interface PackageScanner {
    /**
     * Components scanning.
     *
     * @return Set of component's classes
     */
    Set<Class<?>> scanForComponents();

    /**
     * Scanning chosen package.
     *
     * @param packageName package name.
     * @return Set of component's classes
     */
    Set<Class<?>> findClasses(String packageName);
}
