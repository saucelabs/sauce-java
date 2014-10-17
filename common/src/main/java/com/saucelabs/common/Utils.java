package com.saucelabs.common;

import java.util.Map;

/**
 * @author Ross Rowe
 */
public final class Utils {

    private Utils() {
    }

    /**
     * Populates the <code>updates</code> map with the value of the system property/environment variable
     * with the following name:
     * <ol>
     *     <li>SAUCE_BAMBOO_BUILDNUMBER</li>
     *     <li>JENKINS_BUILD_NUMBER</li>
     *     <li>BUILD_TAG</li>
     *     <li>BUILD_NUMBER</li>
     *     <li>TRAVIS_BUILD_NUMBER</li>
     *     <li>CIRCLE_BUILD_NUM</li>
     * </ol>
     * @param updates
     */
    public static void addBuildNumberToUpdate(Map<String, Object> updates) {
        //try Bamboo
        String buildNumber = readPropertyOrEnv("SAUCE_BAMBOO_BUILDNUMBER", null);
        if (buildNumber == null || buildNumber.equals("")) {
            //try Jenkins
            buildNumber = readPropertyOrEnv("JENKINS_BUILD_NUMBER", null);
        }

        if (buildNumber == null || buildNumber.equals("")) {
            //try BUILD_TAG
            buildNumber = readPropertyOrEnv("BUILD_TAG", null);
        }

        if (buildNumber == null || buildNumber.equals("")) {
            //try BUILD_NUMBER
            buildNumber = readPropertyOrEnv("BUILD_NUMBER", null);
        }
        if (buildNumber == null || buildNumber.equals("")) {
            //try TRAVIS_BUILD_NUMBER
            buildNumber = readPropertyOrEnv("TRAVIS_BUILD_NUMBER", null);
        }
        if (buildNumber == null || buildNumber.equals("")) {
            //try CIRCLE_BUILD_NUM
            buildNumber = readPropertyOrEnv("CIRCLE_BUILD_NUM", null);
        }

        if (buildNumber != null && !(buildNumber.equals(""))) {
            updates.put("build", buildNumber);
        }

    }

    public static String readPropertyOrEnv(String key, String defaultValue) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);
        if (v == null)
            v = defaultValue;
        return v;
    }
}
