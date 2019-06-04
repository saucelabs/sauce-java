package com.saucelabs.common.unit;

import com.saucelabs.common.Browser;
import com.saucelabs.common.SauceEnvironmentVariableNotSetException;
import com.saucelabs.common.SauceRemoteGrid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SauceRemoteGridTest {
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
    public void shouldSetDefaultBrowserToChrome()
    {
        Assert.assertEquals(Browser.CHROME, sauceGrid.getBrowser());
    }
    @Test
    public void shouldSetDefaultOSToWindows()
    {
        assertEquals("Windows 10", sauceGrid.getOperatingSystem());
    }
    @Test
    public void shouldSetDefaultBrowserVersionToLatest()
    {
        assertEquals("latest", sauceGrid.getBrowserVersion());
    }
}