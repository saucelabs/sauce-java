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

        assertEquals("sauce:job-result=true", sauceHelper.getTestResultString(true));
    }
    @Test
    public void shouldReturnFailedForFalseResult()
    {
        assertEquals("sauce:job-result=false", sauceHelper.getTestResultString(false));
    }
    @Test
    public void shouldReturnCorrectStringForTestName()
    {
        String testName = "MyTestName";
        assertEquals("sauce:job-name=" + testName, sauceHelper.getTestNameString(testName));
    }
}
