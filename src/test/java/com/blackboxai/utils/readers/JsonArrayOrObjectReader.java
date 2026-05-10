package com.blackboxai.utils.readers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JSON utility to handle both:
 * - JSON arrays: [ {...}, {...} ]
 * - JSON objects: { ... }
 */
public class JsonArrayOrObjectReader {

    public JSONObject readFirstObject(String filePath) {
        String content = readContent(filePath).trim();

        if (content.startsWith("[")) {
            JSONArray arr = new JSONArray(content);
            if (arr.length() == 0) {
                throw new RuntimeException("No user objects found in JSON array: " + filePath);
            }
            return arr.getJSONObject(0);
        }

        JSONObject obj = new JSONObject(content);
        if (obj.has("data") && obj.get("data") instanceof JSONObject) {
            // optional wrapper pattern
            return obj.getJSONObject("data");
        }
        return obj;
    }

    public JSONObject readObject(String filePath) {
        String content = readContent(filePath).trim();
        if (content.startsWith("[")) {
            // for callers that expect a JSONObject, return the first element
            return readFirstObject(filePath);
        }
        return new JSONObject(content);
    }

    private String readContent(String filePath) {
        try {
            return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read JSON file: " + filePath, e);
        }
    }
}

