package com.blackboxai.utils.readers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/**
 * Utility reader for .properties files.
 */
public class PropertyReader {

    public Properties readProperties(String filePath) {
        Objects.requireNonNull(filePath, "filePath");

        Properties props = new Properties();
        try (InputStream is = Files.newInputStream(Path.of(filePath))) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file: " + filePath, e);
        }
        return props;
    }

    public String getString(String filePath, String key, String defaultValue) {
        Properties props = readProperties(filePath);
        String value = props.getProperty(key);
        return value != null ? value : defaultValue;
    }

}

