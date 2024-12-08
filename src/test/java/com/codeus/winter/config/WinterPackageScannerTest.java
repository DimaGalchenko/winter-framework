package com.codeus.winter.config;

import com.codeus.winter.annotation.Component;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link WinterPackageScanner}.
 * These tests verify that the package scanner correctly identifies classes
 * annotated with specific annotations within a given package.
 */
class WinterPackageScannerTest {

    private final WinterPackageScanner packageScanner = new WinterPackageScanner();

    /**
     * Tests that the scanner correctly finds classes annotated with a given annotation.
     */
    @Test
    void shouldFindClassesWithSpecifiedAnnotation() {
        String testPackage = "com.codeus.winter.config";
        Set<Class<? extends Annotation>> annotations = Set.of(Component.class);

        Set<Class<?>> result = packageScanner.findClassesWithAnnotations(testPackage, annotations);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.contains(WinterClass.class),
                "Result should contain the WinterClass annotated with @Component"); }

    /**
     * Tests that the scanner returns an empty set if no annotated classes are found.
     */
    @Test
    void shouldReturnEmptySetWhenNoAnnotatedClassesFound() {
        String testPackage = "com.framework";
        Set<Class<? extends Annotation>> annotations = Set.of(Component.class);

        Set<Class<?>> result = packageScanner.findClassesWithAnnotations(testPackage, annotations);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be an empty set");
    }

    /**
     * Tests that the scanner handles an empty annotation set correctly.
     */
    @Test
    void shouldHandleEmptyAnnotationSet() {
        // Arrange
        String testPackage = "com.framework";
        Set<Class<? extends Annotation>> annotations = Set.of();

        Set<Class<?>> result = packageScanner.findClassesWithAnnotations(testPackage, annotations);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be an empty set when no annotations are provided");
    }

    /**
     * A mock class annotated with {@link Component}, used for testing.
     */
    @Component
    static class WinterClass {
    }
}
