package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class SauceApi {

    private WebDriver webDriver;

    public SauceApi(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public SauceApi() {
    }

    //TODO duplication of 'sauce:jobresult' string and concatenation
    public String getTestResultString(boolean testResult) {
        return "sauce:job-result=" + testResult;
    }
    private String getTestResultString(String testResult) {
        return "sauce:job-result=" + testResult;
    }

    public void setTestStatus(String testResult) {
        //TODO finish implementation
        //testResult = testResult.toLowerCase();
        //isValidTestStatus(testResult);
        new JavaScriptInvoker(webDriver).executeScript(getTestResultString(testResult));
    }

//    private void isValidTestStatus(String testResult) throws InvalidTestStatusException {
//        if(!testResult.equals("passed")  || !testResult.equals("failed")  ||
//            !testResult.equals("true") || !testResult.equals("false"))
//        {
//            throw new InvalidTestStatusException();
//        }
//    }

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