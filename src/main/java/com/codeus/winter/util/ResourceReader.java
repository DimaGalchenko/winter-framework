package com.codeus.winter.util;

import java.util.List;

/**
 * Common operation for resource reader.
 *
 * @param <T> type of resource.
 */
public interface ResourceReader<T> {

    /**
     * Checking existing resource file.
     *
     * @param filepath string path of file.
     * @param filename filename.
     * @return true if resource file is exist.
     */
    boolean isFileExist(String filepath, String filename);

    /**
     * Method for reading properties from file to object.
     *
     * @param filePath string path of file.
     * @param fileName filename.
     * @return list of properties.
     */
    List<PropertySource<T>> readProperties(String filePath, String fileName);
}
