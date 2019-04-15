package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class SauceGridTest{
    SauceRemoteGrid sauceGrid;
    @Before
    public void setupBeforeTest()
    {
        sauceGrid = new SauceRemoteGrid();
    }
    @Test
    public void shouldReturnObject()
    {
        Assert.assertNotNull(sauceGrid);
    }
    @Test
    public void shouldReturnTestObject()
    {
        Assert.assertNotNull(sauceGrid.test);
    }
    @Test
    public void shouldSetDefaultBrowserToChrome()
    {
        Assert.assertEquals(Browser.Chrome, sauceGrid.test.getBrowser());
    }
}
