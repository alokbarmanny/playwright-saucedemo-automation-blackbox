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
        // Try filesystem path first (works when running from project root).
        try {
            Properties props = readProperties(filePath);
            String value = props.getProperty(key);
            return value != null ? value : defaultValue;
        } catch (RuntimeException filesystemFailure) {
            // Fallback: attempt to load from classpath.
            String classpathLocation = normalizeToClasspathLocation(filePath);
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(classpathLocation)) {
                if (is == null) {
                    return defaultValue;
                }
                Properties props = new Properties();
                props.load(is);
                String value = props.getProperty(key);
                return value != null ? value : defaultValue;
            } catch (IOException ignored) {
                return defaultValue;
            }
        }
    }

    private String normalizeToClasspathLocation(String filePath) {
        // Examples:
        // - src/test/resources/env/dev/env.properties -> env/dev/env.properties
        // - env/dev/env.properties -> env/dev/env.properties
        String fp = filePath.replace("\\", "/");
        int idx = fp.indexOf("src/test/resources/");
        if (idx >= 0) {
            fp = fp.substring(idx + "src/test/resources/".length());
        }
        if (fp.startsWith("resources/")) {
            fp = fp.substring("resources/".length());
        }
        while (fp.startsWith("/")) {
            fp = fp.substring(1);
        }
        return fp;
    }

}
