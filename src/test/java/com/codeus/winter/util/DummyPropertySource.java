package com.codeus.winter.util;

/**
 * Class for testing abstract class {@link PropertySource}.
 */
public class DummyPropertySource extends PropertySource<Object> {

    /**
     * Constructor with params.
     *
     * @param name   properties name.
     * @param source source object.
     */
    public DummyPropertySource(String name, Object source) {
        super(name, source);
    }

    /**
     * Method for get property.
     *
     * @param name properties name.
     * @return property object.
     */
    @Override
    protected Object getProperty(String name) {
        return null;
    }

    /**
     * Method for checking contains property.
     *
     * @param name properties name.
     * @return true if property with name is exist.
     */
    @Override
    protected boolean containsProperty(String name) {
        return false;
    }
}
