package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceJavaScriptStringsTest {
    private SauceHelper sauceHelper;

    @Before
    public void runBeforeEveryTest()
    {
        sauceHelper = new SauceHelper();
    }

    @Test
    public void shouldReturnPassedForTrueResult()
    {
        assertJobResult("sauce:job-result=", true);
    }
    private void assertJobResult(String s, boolean b) {
        assertEquals(s + b, sauceHelper.getTestResultString(b));
    }

    @Test
    public void shouldReturnFailedForFalseResult()
    {
        assertJobResult("sauce:job-result=", false);
    }
    @Test
    public void shouldBeCorrectTestNamePrefix()
    {
        assertEquals("sauce:job-name=", SauceJavaScriptStrings.testNamePrefix);
    }
    @Test
    public void shouldBeCorrectSauceContextPrefix()
    {
        assertEquals("sauce:context=", SauceJavaScriptStrings.sauceContextPrefix);
    }
    @Test
    public void shouldBeCorrectTagsPrefix()
    {
        assertEquals("sauce:job-tags=", SauceJavaScriptStrings.tagsPrefix);
    }
    @Test
    public void shouldBeCorrectBuildPrefix()
    {
        assertEquals("sauce:job-build=", SauceJavaScriptStrings.buildPrefix);
    }
    @Test
    public void shouldBeCorrectBreakStatement()
    {
        assertEquals("sauce: break", SauceJavaScriptStrings.breakStatement);
    }
}
