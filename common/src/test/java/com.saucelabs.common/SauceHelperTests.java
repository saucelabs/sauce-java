package com.saucelabs.common;

import org.junit.Test;
import static org.junit.Assert.*;


public class SauceHelperTests {
    @Test
    public void shouldReturnPassedForTrueResult()
    {
        SauceHelper sauce = new SauceHelper();
        assertEquals(sauce.getTestResultString(true), "sauce:job-result=true");
    }
    @Test
    public void shouldReturnFailedForFalseResult()
    {
        SauceHelper sauce = new SauceHelper();
        assertEquals(sauce.getTestResultString(false), "sauce:job-result=false");
    }
}
