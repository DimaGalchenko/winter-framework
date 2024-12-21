package com.codeus.winter.util;

import java.util.List;

public interface ResourceReader<T> {

    /**
     * Checking an existing resource file.
     *
     * @param filepath string path of a file.
     * @param filename filename.
     * @return true if a resource file exists.
     */
    boolean isFileExist(String filepath, String filename);

    /**
     * Method for reading properties from file to object.
     *
     * @param filePath string path of a file.
     * @param fileName filename.
     * @return list of properties.
     */
    List<PropertySource<T>> readProperties(String filePath, String fileName);
}
