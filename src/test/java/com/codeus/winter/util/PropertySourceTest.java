package com.codeus.winter.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PropertySourceTest {

    private PropertySource<String> propertySource;

    @BeforeEach
    void setUp() {
        propertySource = new PropertySource<>("testName", "testSource") {
            @Override
            protected Object getProperty(String name) {
                return null;
            }

            @Override
            protected boolean containsProperty(String name) {
                return false;
            }
        };
    }

    @Test
    void testName() {
        assertEquals("testName", propertySource.getName());
    }

    @Test
    void testSource() {
        assertEquals("testSource", propertySource.getSource());
    }

    @Test
    void testEquals() {
        PropertySource<String> anotherPropertySource = new PropertySource<>("testName", "anotherSource") {
            @Override
            protected Object getProperty(String name) {
                return null;
            }

            @Override
            protected boolean containsProperty(String name) {
                return false;
            }
        };
        assertEquals(propertySource, anotherPropertySource);
    }

    @Test
    void testHashCode() {
        PropertySource<String> anotherPropertySource = new PropertySource<>("testName", "anotherSource") {
            @Override
            protected Object getProperty(String name) {
                return null;
            }

            @Override
            protected boolean containsProperty(String name) {
                return false;
            }
        };
        assertEquals(propertySource.hashCode(), anotherPropertySource.hashCode());
    }

    @Test
    void testHasTextThrowsException() {
        assertThrows(NullPointerException.class, () -> new PropertySource<String>(null, "source") {
            @Override
            protected Object getProperty(String name) {
                return null;
            }

            @Override
            protected boolean containsProperty(String name) {
                return false;
            }
        });
        assertThrows(NullPointerException.class, () -> new PropertySource<String>("", "source") {
            @Override
            protected Object getProperty(String name) {
                return null;
            }

            @Override
            protected boolean containsProperty(String name) {
                return false;
            }
        });
    }

    @Test
    void testNotNullThrowsException() {
        assertThrows(NullPointerException.class, () -> new PropertySource<String>("name", null) {
            @Override
            protected Object getProperty(String name) {
                return null;
            }

            @Override
            protected boolean containsProperty(String name) {
                return false;
            }
        });
    }

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

class ResourceReaderTest {

    private ResourceReader<String> resourceReader;

    @BeforeEach
    void setUp() {
        resourceReader = Mockito.mock(ResourceReader.class);
    }

    @Test
    void testIsFileExist() {
        when(resourceReader.isFileExist("path", "file")).thenReturn(true);
        assertTrue(resourceReader.isFileExist("path", "file"));
        verify(resourceReader, times(1)).isFileExist("path", "file");
    }

    @Test
    void testReadProperties() {
        List<PropertySource<String>> properties = List.of(
                new PropertySource<>("name1", "source1") {
                    @Override
                    protected Object getProperty(String name) {
                        return null;
                    }

                    @Override
                    protected boolean containsProperty(String name) {
                        return false;
                    }
                },
                new PropertySource<>("name2", "source2") {
                    @Override
                    protected Object getProperty(String name) {
                        return null;
                    }

                    @Override
                    protected boolean containsProperty(String name) {
                        return false;
                    }
                }
        );
        when(resourceReader.readProperties("path", "file")).thenReturn(properties);
        List<PropertySource<String>> result = resourceReader.readProperties("path", "file");
        assertEquals(2, result.size());
        assertEquals("name1", result.get(0).getName());
        assertEquals("name2", result.get(1).getName());
        verify(resourceReader, times(1)).readProperties("path", "file");
    }
}
