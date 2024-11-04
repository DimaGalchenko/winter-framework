package com.codeus.winter.config;

import java.util.Set;

public interface PackageScanner {
    Set<Class<?>> scanForComponents();

    Set<Class<?>> findClasses(String packageName);
}
