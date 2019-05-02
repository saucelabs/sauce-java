package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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
    public void shouldReturnSauceUserName() throws SauceEnvironmentVariableNotSetException {
        String userName = "testUserName";
        SauceLabsSession sauceTest = new SauceLabsSession();
        assertEquals(userName, sauceTest.getUserName());
    }
    @Test(expected = SauceEnvironmentVariableNotSetException.class)
    public void shouldThrowExceptionForNullUserName() throws SauceEnvironmentVariableNotSetException {
        SauceEnvironment stubSauceEnv = new SauceEnvironment();
        stubSauceEnv.setUserNameEnvironmentVariable("FAKE", "FAKEVAL");
        SauceLabsSession sauceTest = new SauceLabsSession(stubSauceEnv);

        sauceTest.getUserName();
    }
    @Test
    public void shouldReturnSauceAccessKey()
    {
        String accessKey = "testAccessKey";
        SauceLabsSession sauceTest = new StubSauceSession(accessKey);
        assertEquals(accessKey, sauceTest.getAccessKey());
    }
    @Test
    public void shouldStartTestSession() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        SauceLabsSession sauceTest = new StubSauceSession("testKey");
        RemoteWebDriver driver = sauceTest.start();
        assertNotNull(driver);
    }
}
