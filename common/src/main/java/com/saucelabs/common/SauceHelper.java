package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class SauceHelper {

    private WebDriver webDriver;

    public SauceHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public SauceHelper() {
    }

    public String getTestResultString(boolean result) {
        return "sauce:job-result=" + result;
    }

    public String getTestNameString(String testName) {
        return "sauce:job-name=" + testName;
    }

    public String getCommentString(String comment) {
        return "sauce:context=" + comment;
    }

    public void comment(String comment) {
        new JavaScriptInvoker(webDriver).executeScript(getCommentString(comment));
    }

    public void setTestStatus(String testResult) {
        new JavaScriptInvoker(webDriver).executeScript(getTestResultString(testResult));
    }

    private String getTestResultString(String testResult) {
        return getTestResultString(Boolean.getBoolean(testResult));
    }
}
