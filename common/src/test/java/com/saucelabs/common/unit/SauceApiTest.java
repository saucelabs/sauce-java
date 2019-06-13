package com.saucelabs.common.unit;

import com.saucelabs.common.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class SauceApiTest extends BaseUnitTest {
    private SauceApi sauceApi;
    private JavaScriptExecutor mockJSExecutor;
  
    @Before
    public void runBeforeEveryTest()
    {
        sauceApi = new SauceApi();
        mockJSExecutor = mock(JavaScriptExecutor.class);
        JavaScriptInvokerFactory.setJavaScriptExecutor(mockJSExecutor);
    }
    @Test
    public void shouldRunExecuteStringMethodWithoutDefaultManagerSet() throws InvalidTestStatusException {
        sauceApi.setTestStatus("pass");
        verify(mockJSExecutor, times(1)).executeScript("sauce:job-result=pass");
    }
    @Test
    public void shouldSetTestName()
    {
        String testName = "testName";
        sauceApi.setTestName("testName");
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.testNamePrefix + testName);
    }
    @Test
    public void shouldSetTags()
    {
        String tags = "tag1,tag2,tag3";
        sauceApi.setTestTags(tags);
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.tagsPrefix + tags);
    }
    @Test
    public void shouldComment()
    {
        String comment = "a comment";
        sauceApi.comment(comment);
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.sauceContextPrefix + comment);
    }

    @Test
    public void shouldSetBuildName()
    {
        String buildName = "myBuild";
        sauceApi.setBuildName(buildName);
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.buildPrefix + buildName);
    }
    @Test
    public void shouldSetBreakpoint()
    {
        sauceApi.setBreakpoint();
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.breakStatement);
    }
    @Test
    public void shouldSetTestStatusWithPass() {
        String testStatus = "pass";
        sauceApi.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }
    @Test
    public void shouldSetTestStatusWithTrue() {
        String testStatus = "true";
        sauceApi.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }
    @Test
    public void shouldSetTestStatusWithFailed() {
        String testStatus = "failed";
        sauceApi.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }
    @Test
    public void shouldSetTestStatusWithFalse() {
        String testStatus = "false";
        sauceApi.setTestStatus(testStatus);
        verifyTestStatus(testStatus);
    }

    //TODO add this test after we finish implementation of SauceApi.isValidTestStatus()
//    @Test
//    public void shouldThrowExceptionForInvalidTestStatus()
//    {
//        String invalidTestStatus = "success";
//        verify(mockJSExecutor, times(1)).
//            executeScript(SauceJavaScriptStrings.testStatusPrefix + invalidTestStatus);
//    }
    private void verifyTestStatus(String testStatus) {
        verify(mockJSExecutor, times(1)).
            executeScript(SauceJavaScriptStrings.testStatusPrefix + testStatus);
    }
}
