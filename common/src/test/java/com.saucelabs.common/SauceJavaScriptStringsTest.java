package com.saucelabs.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceJavaScriptStringsTest {
    private SauceHelper sauceHelper;

    @Test
    public void shouldReturnCorrectCommandForTrueResult()
    {
        sauceHelper = new SauceHelper();
        Boolean testStatus = true;
        String actualTestResultCommand = sauceHelper.getTestResultString(testStatus);

        assertEquals("sauce:job-result=true", actualTestResultCommand);
    }

    @Test
    public void shouldReturnCorrectCommandForFalseResult()
    {
        sauceHelper = new SauceHelper();
        Boolean testStatus = false;

        String actualTestResultCommand = sauceHelper.getTestResultString(testStatus);
        assertEquals("sauce:job-result=false", actualTestResultCommand);
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

    @Test
    public void itShouldBeCorrectJobResultPrefix()
    {
        assertEquals("sauce:job-result=", SauceJavaScriptStrings.testStatusPrefix);
    }
}
