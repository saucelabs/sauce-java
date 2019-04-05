package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class SauceHelperTest extends BaseUnitTest {
    private SauceHelper sauceHelper;
    @Before
    public void runBeforeEveryTest()
    {
        sauceHelper = new SauceHelper();
    }

    @Test
    public void shouldReturnPassedForTrueResult()
    {
        assertStringsEqual("sauce:job-result=", true);
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
        JavaScriptInvokerImpl javascriptExecutor = mock(JavaScriptInvokerImpl.class);
        JavaScriptInvokerFactory.setJavaScriptInvoker(javascriptExecutor);

        sauceHelper.setTestStatus("pass");
        verify(javascriptExecutor, times(1)).executeScript("sauce:job-result=pass");
    }
    private void assertStringsEqual(String s, boolean b) {
        assertEquals(s + b, sauceHelper.getTestResultString(b));
    }
}
