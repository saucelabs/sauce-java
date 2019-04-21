package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;


public class SauceLabsSessionTest {
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
        String userName = "testUserName";
        SauceLabsSession sauceTest = new StubSauceSession(userName);
        assertEquals(userName, sauceTest.getUserName());
    }
    @Test
    public void shouldReturnSauceAccessKey()
    {
        String accessKey = "testAccessKey";
        SauceLabsSession sauceTest = new StubSauceSession(accessKey);
        assertEquals(accessKey, sauceTest.getAccessKey());
    }
    @Test
    public void shouldStartTestSession() throws MalformedURLException {
        RemoteWebDriver mockRemoteSession = mock(RemoteWebDriver.class);
        SauceLabsSession sauceTest = new StubSauceSession(mockRemoteSession);
        RemoteWebDriver driver = sauceTest.start();
        assertNotNull(driver);
    }
}
