package com.codeus.winter.util;

import com.codeus.winter.exception.PropertySourceException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlResourceReader implements ResourceReader<Object> {

    @Override
    public boolean isFileExist(String filepath, String filename) {
        File file = new File(filepath, filename);
        return file.exists() && file.isFile();
    }

    @Override
public List<PropertySource<Object>> readProperties(String filePath, String fileName) {
    List<PropertySource<Object>> propertySources = new ArrayList<>();
    try (InputStream input = new FileInputStream(new File(filePath, fileName))) {
        Yaml yaml = new Yaml();
        Map<String, Object> properties = yaml.load(input);
        if (properties != null) {
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                propertySources.add(new SimplePropertySource(entry.getKey(), entry.getValue()));
            }
        }
    } catch (IOException e) {
        throw new PropertySourceException(e.getMessage());
    }
    return propertySources;
}


    private static class SimplePropertySource extends PropertySource<Object> {
        private final Object value;

        protected SimplePropertySource(String name, Object value) {
            super(name, value);
            this.value = value;
        }

        @Override
        protected Object getProperty(String name) {
            return name.equals(getName()) ? value : null;
        }

        @Override
        protected boolean containsProperty(String name) {
            return name.equals(getName());
        }
    }
}
