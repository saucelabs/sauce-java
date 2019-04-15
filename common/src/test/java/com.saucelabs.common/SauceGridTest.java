package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Test;



public class SauceGridTest{
    @Test
    public void shouldReturnObject()
    {
        SauceRemoteGrid sauceGrid = new SauceRemoteGrid();
        Assert.assertNotNull(sauceGrid);
    }
    @Test
    public void shouldReturnTestObject()
    {
        SauceRemoteGrid sauceGrid = new SauceRemoteGrid();
        Assert.assertNotNull(sauceGrid.test);
    }
    @Test
    public void shouldSetDefaultBrowserToChrome()
    {
        SauceRemoteGrid sauceGrid = new SauceRemoteGrid();
        Assert.assertEquals(Browser.Chrome, sauceGrid.test.getBrowser());
    }
}
