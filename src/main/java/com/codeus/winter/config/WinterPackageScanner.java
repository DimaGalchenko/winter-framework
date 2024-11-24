package com.codeus.winter.config;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class WinterPackageScanner implements PackageScanner {

    @Override
    public final Set<Class<?>> findClassesWithAnnotations(String packageName,
                                                          Set<Class<? extends Annotation>> annotations) {
        Set<Class<?>> annotatedClasses = new HashSet<>();

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage(packageName)
                        .addScanners(Scanners.TypesAnnotated)
                        .addScanners(Scanners.SubTypes)
        );

        for (Class<? extends Annotation> annotation : annotations) {
            annotatedClasses.addAll(reflections.getTypesAnnotatedWith(annotation));
        }

        return annotatedClasses;
    }

}
