package com.saucelabs.common.unit;

<<<<<<< HEAD:common/src/test/java/com/saucelabs/common/unit/SauceJavaScriptStringsTest.java
import com.saucelabs.common.SauceJavaScriptStrings;
import org.junit.Assert;
=======
>>>>>>> b3926482d56790d2c587b3a965d3c63a2222669a:common/src/test/java/com.saucelabs.common/SauceJavaScriptStringsTest.java
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceJavaScriptStringsTest {

<<<<<<< HEAD:common/src/test/java/com/saucelabs/common/unit/SauceJavaScriptStringsTest.java
=======
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
>>>>>>> b3926482d56790d2c587b3a965d3c63a2222669a:common/src/test/java/com.saucelabs.common/SauceJavaScriptStringsTest.java
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
