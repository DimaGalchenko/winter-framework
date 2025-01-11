package com.codeus.winter.util;

import com.codeus.winter.exception.PropertySourceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesResourceReader implements ResourceReader<String> {

    @Override
    public boolean isFileExist(String filepath, String filename) {
        File file = new File(filepath, filename);
        return file.exists() && file.isFile();
    }

    @Override
    public List<PropertySource<String>> readProperties(String filePath, String fileName) {
        List<PropertySource<String>> propertySources = new ArrayList<>();

        try (InputStream input = new FileInputStream(new File(filePath, fileName))) {
            Properties properties = new Properties();
            properties.load(input);
            for (String name : properties.stringPropertyNames()) {
                propertySources.add(new SimplePropertySource(name, properties.getProperty(name)));
            }
        } catch (IOException e) {
            throw new PropertySourceException(e.getMessage());
        }

        return propertySources;
    }

    private static class SimplePropertySource extends PropertySource<String> {
        private final String value;

        protected SimplePropertySource(String name, String value) {
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
