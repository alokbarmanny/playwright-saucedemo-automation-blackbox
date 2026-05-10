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
        return readIndexedObject(filePath, 0);
    }

    public JSONObject readIndexedObject(String filePath, int index) {
        String content = readContent(filePath).trim();

        if (content.startsWith("[")) {
            JSONArray arr = new JSONArray(content);
            if (arr.length() == 0) {
                throw new RuntimeException("No user objects found in JSON array: " + filePath);
            }
            return arr.getJSONObject(Math.min(index, arr.length() - 1));

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
        // 1) Try filesystem path (works when running from project root).
        try {
            return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        } catch (Exception filesystemFailure) {
            // 2) Fallback: try classpath resource.
            String classpathLocation = normalizeToClasspathLocation(filePath);
            try (var is = getClass().getClassLoader().getResourceAsStream(classpathLocation)) {
                if (is == null) {
                    throw new RuntimeException("Unable to read JSON file: " + filePath, filesystemFailure);
                }
                byte[] bytes = is.readAllBytes();
                return new String(bytes, StandardCharsets.UTF_8);
            } catch (Exception classpathFailure) {
                throw new RuntimeException("Unable to read JSON file: " + filePath, classpathFailure);
            }
        }
    }

    private String normalizeToClasspathLocation(String filePath) {
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
