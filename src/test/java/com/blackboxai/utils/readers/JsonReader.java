package com.blackboxai.utils.readers;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Utility reader for JSON files.
 */
public class JsonReader {

    public JSONObject readObject(String filePath) {
        Objects.requireNonNull(filePath, "filePath");

        try {
            String content = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
            return new JSONObject(content);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read JSON file: " + filePath, e);
        }
    }

    public String getString(String filePath, String key) {
        return readObject(filePath).getString(key);
    }
}

