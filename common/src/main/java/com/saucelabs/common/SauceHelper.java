package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class SauceHelper {

    private WebDriver webDriver;

    public SauceHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public SauceHelper() {
    }

    //TODO duplication of 'sauce:jobresult' string and concatenation
    public String getTestResultString(boolean testResult) {
        return "sauce:job-result=" + testResult;
    }
    private String getTestResultString(String testResult) {
        return "sauce:job-result=" + testResult;
    }

    public String getTestNameString(String testName) {
        return "sauce:job-name=" + testName;
    }

    public String getCommentString(String comment) {
        return "sauce:context=" + comment;
    }

    public void setTestStatus(String testResult) {
        new JavaScriptInvoker(webDriver).executeScript(getTestResultString(testResult));
    }

    public void setTestName(String testName) {
        new JavaScriptInvoker(webDriver).executeScript(getTestNameString(testName));
    }
}