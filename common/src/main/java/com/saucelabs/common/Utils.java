package com.saucelabs.common;

import java.util.Map;

/**
 * @author Ross Rowe
 */
public final class Utils {

    private Utils() {
    }

    public static void addBuildNumberToUpdate(Map<String, Object> updates) {
        String buildNumber = readPropertyOrEnv("BAMBOO_BUILDNUMBER", null);
        if (buildNumber == null || buildNumber.equals("")) {
            //try Jenkins
            buildNumber = readPropertyOrEnv("JENKINS_BUILD_NUMBER", null);
        }

        if (buildNumber != null && !(buildNumber.equals(""))) {
            updates.put("build", buildNumber);
        }

    }

    private static String readPropertyOrEnv(String key, String defaultValue) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);
        if (v == null)
            v = defaultValue;
        return v;
    }
}
