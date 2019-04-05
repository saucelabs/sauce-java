package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
    public void shouldReturnPassedForTrueResult()
    {
        assertStringsEqual("sauce:job-result=", true);
    }
    private void assertStringsEqual(String s, boolean b) {
        assertEquals(s + b, sauceHelper.getTestResultString(b));
    }

    @Test
    public void shouldReturnFailedForFalseResult()
    {
        assertStringsEqual("sauce:job-result=", false);
    }
    @Test
    public void shouldReturnCorrectStringForTestName()
    {
        String testName = "MyTestName";
        assertEquals("sauce:job-name=" + testName, sauceHelper.getTestNameString(testName));
    }
    @Test
    public void shouldReturnSauceContextString()
    {
        String comment = "This is a comment";
        assertEquals("sauce:context=" + comment, sauceHelper.getCommentString(comment));
    }
    @Test
    public void shouldRunExecuteStringMethodWithoutDefaultManagerSet()
    {
        sauceHelper.setTestStatus("pass");
        verify(mockJSExecutor, times(1)).executeScript("sauce:job-result=pass");
    }
    @Test
    public void shouldSetTestName()
    {
        sauceHelper.setTestName("testName");
        verify(mockJSExecutor, times(1)).executeScript("sauce:job-name=testName");
    }
    @Test
    public void shouldSetTags()
    {
        String tags = "tag1,tag2,tag3";
        sauceHelper.setTestTags(tags);
        verify(mockJSExecutor, times(1)).executeScript("sauce:job-tags=" + tags);
    }
}
