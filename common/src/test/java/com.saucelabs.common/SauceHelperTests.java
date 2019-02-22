package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class SauceHelperTests {
    SauceHelper sauceHelper;
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
    private void assertStringsEqual(String s, boolean b) {
        assertEquals(s + b, sauceHelper.getTestResultString(b));
    }
}
