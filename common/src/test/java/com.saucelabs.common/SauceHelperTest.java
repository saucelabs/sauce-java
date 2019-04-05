package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class SauceHelperTest extends BaseUnitTest {
    private SauceHelper sauceHelper;
    private JavaScriptExecutor mockJSExecutor;

    @Before
    public void runBeforeEveryTest()
    {
        sauceHelper = new SauceHelper();
        mockJSExecutor = mock(JavaScriptExecutor.class);
        JavaScriptInvokerFactory.setJavaScriptExecutor(mockJSExecutor);
    }
    @Test
    public void shouldRunExecuteStringMethodWithoutDefaultManagerSet() throws InvalidTestStatusException {
        sauceHelper.setTestStatus("pass");
        verify(mockJSExecutor, times(1)).executeScript("sauce:job-result=pass");
    }
    @Test
    public void shouldSetTestName()
    {
        String testName = "testName";
        sauceHelper.setTestName("testName");
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.testNamePrefix + testName);
    }
    @Test
    public void shouldSetTags()
    {
        String tags = "tag1,tag2,tag3";
        sauceHelper.setTestTags(tags);
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.tagsPrefix + tags);
    }
    @Test
    public void shouldComment()
    {
        String comment = "a comment";
        sauceHelper.comment(comment);
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.sauceContextPrefix + comment);
    }

    @Test
    public void shouldSetBuildName()
    {
        String buildName = "myBuild";
        sauceHelper.setBuildName(buildName);
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.buildPrefix + buildName);
    }
    @Test
    public void shouldSetBreakpoint()
    {
        sauceHelper.setBreakpoint();
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.breakStatement);
    }
    @Test
    public void shouldSetTestStatusWithPass() throws InvalidTestStatusException {
        String testStatus = "pass";
        sauceHelper.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }
    @Test
    public void shouldSetTestStatusWithTrue() throws InvalidTestStatusException {
        String testStatus = "true";
        sauceHelper.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }
    @Test
    public void shouldSetTestStatusWithFailed() throws InvalidTestStatusException {
        String testStatus = "failed";
        sauceHelper.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }
    @Test
    public void shouldSetTestStatusWithFalse() throws InvalidTestStatusException {
        String testStatus = "false";
        sauceHelper.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }

    private void verifyTestStatus(String testStatus) {
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.testStatusPrefix + testStatus);
    }
}
