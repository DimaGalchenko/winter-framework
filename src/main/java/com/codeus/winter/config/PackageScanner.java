package com.codeus.winter.config;

import java.util.Set;

/**
 * PackageScanner interface contains common methods for parsing components
 */
public interface PackageScanner {
    Set<Class<?>> scanForComponents();

    Set<Class<?>> findClasses(String packageName);
}
