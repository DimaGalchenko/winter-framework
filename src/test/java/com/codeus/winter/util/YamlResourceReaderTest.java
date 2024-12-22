package com.codeus.winter.util;

import com.codeus.winter.exception.PropertySourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class YamlResourceReaderTest {

    private YamlResourceReader reader;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        reader = new YamlResourceReader();

        // We create a temporary .yaml file for testing
        tempFile = File.createTempFile("test", ".yaml");
        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile))) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(data, writer);
        }
    }

    @Test
    void testIsFileExist() {
        assertTrue(reader.isFileExist(tempFile.getParent(), tempFile.getName()));
        assertFalse(reader.isFileExist(tempFile.getParent(), "nonexistent.yaml"));
    }

    @Test
    void testReadProperties() {
        List<PropertySource<Object>> properties = reader.readProperties(tempFile.getParent(), tempFile.getName());
        assertEquals(2, properties.size());

        PropertySource<Object> property1 = properties.get(0);
        assertEquals("key1", property1.getName());
        assertEquals("value1", property1.getSource());

        PropertySource<Object> property2 = properties.get(1);
        assertEquals("key2", property2.getName());
        assertEquals("value2", property2.getSource());
    }

    @Test
    void testReadPropertiesException() {
        assertThrows(PropertySourceException.class,
                () -> reader.readProperties(tempFile.getParent(), "nonexistent.yaml"));
    }

    @Test
void testReadPropertiesEmptyFile() throws IOException {
    // We create an empty "empty.yaml" file
    File emptyFile = new File(tempFile.getParent(), "empty.yaml");
    if (!emptyFile.exists()) {
        emptyFile.createNewFile();
    }

    assertTrue(reader.isFileExist(emptyFile.getParent(), emptyFile.getName()));
    List<PropertySource<Object>> properties = reader.readProperties(emptyFile.getParent(), emptyFile.getName());
    assertTrue(properties.isEmpty());
}


}
