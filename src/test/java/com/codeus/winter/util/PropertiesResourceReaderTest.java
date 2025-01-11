package com.codeus.winter.util;

import com.codeus.winter.exception.PropertySourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropertiesResourceReaderTest {

    private PropertiesResourceReader reader;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        reader = new PropertiesResourceReader();

        // We create a temporary .properties file for testing
        tempFile = File.createTempFile("test", ".properties");
        Properties properties = new Properties();
        properties.setProperty("key1", "value1");
        properties.setProperty("key2", "value2");

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            properties.store(outputStream, null);
        }
    }

    @Test
    void testIsFileExist() {
        assertTrue(reader.isFileExist(tempFile.getParent(), tempFile.getName()));
        assertFalse(reader.isFileExist(tempFile.getParent(), "nonexistent.properties"));
    }

    @Test
    void testReadProperties() {
        List<PropertySource<String>> properties = reader.readProperties(tempFile.getParent(), tempFile.getName());
        assertEquals(2, properties.size());

        PropertySource<String> property1 = properties.get(0);
        assertEquals("key1", property1.getName());
        assertEquals("value1", property1.getSource());

        PropertySource<String> property2 = properties.get(1);
        assertEquals("key2", property2.getName());
        assertEquals("value2", property2.getSource());
    }

    @Test
    void testReadPropertiesException() {
        assertThrows(PropertySourceException.class,
                () -> reader.readProperties(tempFile.getParent(), "nonexistent.properties"));
    }

    @Test
    void testReadPropertiesEmptyFile() throws IOException {
        // We create an empty file `empty.properties`
        File emptyFile = new File(tempFile.getParent(), "empty.properties");
        emptyFile.createNewFile();

        assertTrue(reader.isFileExist(emptyFile.getParent(), emptyFile.getName()));
        List<PropertySource<String>> properties = reader.readProperties(emptyFile.getParent(), emptyFile.getName());
        assertTrue(properties.isEmpty());
    }

}
