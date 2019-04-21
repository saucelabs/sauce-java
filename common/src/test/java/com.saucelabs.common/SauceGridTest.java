package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;


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
    @Test
    public void shouldReturnSauceUserName()
    {
        assertEquals("fakeName", sauceGrid.test.getUserName());
    }
    @Test
    public void shouldReturnSauceAccessKey()
    {
        assertEquals("fakeKey", sauceGrid.test.getAccessKey());
    }
    @Test
    public void shouldStartSessionWithDefaults() throws MalformedURLException {
        IRemoteBrowser mockRemoteBrowser = mock(IRemoteBrowser.class);
        TestableSauceTest sauceTest = new TestableSauceTest(mockRemoteBrowser);
        RemoteWebDriver driver = sauceTest.start();
        assertEquals(OperatingSystem.Linux, sauceTest.getOperatingSystem());
        assertNotNull(driver);
    }
}
