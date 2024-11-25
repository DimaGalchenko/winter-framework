package com.codeus.winter.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertySourceTest {

    @Test
    void testConstructorWithValidArguments() {
        String name = "dummyName";
        Object source = new Object();
        PropertySource<Object> propertySource = new DummyPropertySource(name, source);

        assertEquals(name, propertySource.getName());
        assertEquals(source, propertySource.getSource());
    }

    @Test
    void testConstructorWithInvalidName() {
        assertThrows(NullPointerException.class, () -> new DummyPropertySource(null, new Object()));
        assertThrows(NullPointerException.class, () -> new DummyPropertySource("", new Object()));
    }

    @Test
    void testConstructorWithInvalidSource() {
        assertThrows(NullPointerException.class, () -> new DummyPropertySource("dummyName", null));
    }

    @Test
    void testEqualsAndHashCode() {
        String name = "dummyName";
        Object source = new Object();
        PropertySource<Object> propertySource1 = new DummyPropertySource(name, source);
        PropertySource<Object> propertySource2 = new DummyPropertySource(name, source);

        assertEquals(propertySource1, propertySource2);
        assertEquals(propertySource1.hashCode(), propertySource2.hashCode());
    }
}
