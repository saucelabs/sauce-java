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

    public void setTestStatus(String testResult) throws InvalidTestStatusException {
        //TODO finish implementation
        //testResult = testResult.toLowerCase();
        //isValidTestStatus(testResult);
        new JavaScriptInvoker(webDriver).executeScript(getTestResultString(testResult));
    }

    private void isValidTestStatus(String testResult) throws InvalidTestStatusException {
        if(testResult != "passed" || testResult != "failed" ||
            testResult != "true" || testResult != "false")
        {
            throw new InvalidTestStatusException();
        }
    }

    public void setTestName(String testName) {
        new JavaScriptInvoker(webDriver).executeScript(SauceJavaScriptStrings.testNamePrefix + testName);
    }

    public void setTestTags(String tags) {
        new JavaScriptInvoker(webDriver).executeScript(SauceJavaScriptStrings.tagsPrefix + tags);
    }

    public void comment(String comment) {
        new JavaScriptInvoker(webDriver).executeScript(SauceJavaScriptStrings.sauceContextPrefix + comment);
    }

    public void setBuildName(String buildName) {
        new JavaScriptInvoker(webDriver).executeScript(SauceJavaScriptStrings.buildPrefix + buildName);
    }

    public void setBreakpoint() {
        new JavaScriptInvoker(webDriver).executeScript(SauceJavaScriptStrings.breakStatement);
    }
}