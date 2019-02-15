package com.saucelabs.common;

import org.junit.Test;

import static org.junit.Assert.*;


public class SauceHelperTests {
    @Test
    public void shouldInstantiateClient()
    {
        SauceHelper sauce = new SauceHelper();
        assertTrue(sauce != null);
    }
}
