package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SauceLabsSessionTest {
    SauceRemoteGrid sauceGrid;
    @Before
    public void setupBeforeTest() throws SauceEnvironmentVariableNotSetException {
        sauceGrid = new SauceRemoteGrid();
    }
    @Test
    public void shouldReturnObject()
    {
        assertNotNull(sauceGrid);
    }
    @Test
    public void shouldReturnTestObject()
    {
        assertNotNull(sauceGrid.test);
    }
    @Test
    public void shouldSetDefaultBrowserToChrome()
    {
        assertEquals(Browser.Chrome, sauceGrid.test.getBrowser());
    }
    @Test
    public void shouldSetDefaultOSToLinux()
    {
        assertEquals(OperatingSystem.Linux, sauceGrid.test.getOperatingSystem());
    }
    @Test
    public void shouldSetDefaultBrowserVersionToLatest()
    {
        assertEquals("latest", sauceGrid.test.getBrowserVersion());
    }

}
