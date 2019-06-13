package com.saucelabs.common.unit;

import com.saucelabs.common.SauceApi;
import com.saucelabs.common.SauceJavaScriptStrings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceJavaScriptStringsTest {
    private SauceApi sauceApi;

    @Before
    public void runBeforeEveryTest()
    {
        sauceApi = new SauceApi();
    }

    @Test
    public void shouldReturnPassedForTrueResult()
    {
        assertStringsEqual("sauce:job-result=", true);
    }
    private void assertStringsEqual(String s, boolean b) {
        assertEquals(s + b, sauceApi.getTestResultString(b));
    }

    @Test
    public void shouldReturnFailedForFalseResult()
    {
        assertStringsEqual("sauce:job-result=", false);
    }
    @Test
    public void shouldBeCorrectTestNamePrefix()
    {
        Assert.assertEquals("sauce:job-name=", SauceJavaScriptStrings.testNamePrefix);
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
