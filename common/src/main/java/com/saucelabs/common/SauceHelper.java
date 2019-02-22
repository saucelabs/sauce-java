package com.saucelabs.common;

public class SauceHelper {

    public String getTestResultString(boolean result) {
        return "sauce:job-result=" + result;
    }

    public String getTestNameString(String testName) {
        return "sauce:job-name=" + testName;
    }
}
