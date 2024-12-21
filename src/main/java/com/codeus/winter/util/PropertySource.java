package com.codeus.winter.util;

public abstract class PropertySource<T> {
    private final String name;

    private final T source;

    protected PropertySource(String name, T source) {
        hasText(name);
        notNull(source);
        this.name = name;
        this.source = source;
    }

    @SuppressWarnings("unchecked")
    protected PropertySource(String name) {
        this(name, (T) new Object());
    }

    private void hasText(String string) {
        if (string == null || string.isBlank()) {
            throw new NullPointerException("Property source name must contain at least one character");
        }
    }

    private void notNull(T source) {
        if (source == null) {
            throw new NullPointerException("Property source name must not be null");
        }
    }

    /**
     * Getter for Name.
     *
     * @return String as properties' name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for Source.
     *
     * @return T Object of source.
     */
    public T getSource() {
        return source;
    }

    protected abstract Object getProperty(String name);

    protected abstract boolean containsProperty(String name);

    /**
     * Defining of method equals.
     *
     * @param o comparing object.
     * @return true if both of objects are equals, else - false.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertySource<?> that)) {
            return false;
        }

        return name.equals(that.name);
    }

    /**
     * Calculating hashcode.
     *
     * @return int value of hashcode.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
