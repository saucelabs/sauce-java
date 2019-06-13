package com.saucelabs.common.unit;

import com.saucelabs.common.SauceApi;
import com.saucelabs.common.SauceJavaScriptStrings;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceJavaScriptStringsTest {

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

    @Test
    public void itShouldBeCorrectJobResultPrefix()
    {
        assertEquals("sauce:job-result=", SauceJavaScriptStrings.testStatusPrefix);
    }
}
