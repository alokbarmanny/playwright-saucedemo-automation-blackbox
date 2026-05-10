package com.blackboxai.utils.readers;

/**
 * Resolves env-specific test data paths.
 */
public class EnvDataPathResolver {

    public String resolveUserDataPath() {
        String envName = System.getProperty("env", "dev");
        return "src/test/resources/env/" + envName + "/data/userData.json";
    }
}

