package com.saucelabs.remotedriver;

import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SauceSessionTest {

    private SauceSession sauceSession;

    @Before
    public void setUp()
    {
        sauceSession = new SauceSession();
    }
    @Test
    public void seleniumServer_notSet_returnsNull()
    {
        //TODO is this okay to be like a property,
        //or should it be a getSeleniumServer()
        assertNull(sauceSession.seleniumServer);
    }

    @Test
    public void defaultConstructor_instantiated_setsConcreteDriverManager()
    {
        assertThat(sauceSession.getDriverManager(), instanceOf(ConcreteRemoteDriverManager.class));
    }

    @Test
    public void getInstance_serverNotSet_setsSauceSeleniumServer() throws MalformedURLException {
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        sauceSession = new SauceSession(stubRemoteManager);
        sauceSession.getInstanceOld();
        String expectedServer = "https://ondemand.saucelabs.com/wd/hub";
        assertEquals(expectedServer, sauceSession.seleniumServer);
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultIsChrome()
    {
        String actualBrowser = sauceSession.getCapabilities().getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("Chrome"));
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultIsWindows10() {
        String actualOs = sauceSession.getCapabilities().getPlatform().name();
        assertThat(actualOs, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }
    @Test
    public void noSauceOptionsSet_whenCreated_latestBrowserVersion()
    {
        MutableCapabilities caps = new SauceSession().getCapabilities();
        String actualOperatingSystem = caps.getCapability("browserVersion").toString();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("latest"));
    }
    @Test
    public void sauceOptions_defaultConfiguration_setsSauceOptions()
    {
        sauceSession.getCapabilities();
        boolean hasAccessKey = sauceSession.getSauceOptionsCapability().asMap().containsKey("accessKey");
        assertTrue(hasAccessKey);
    }

    @Test
    @Ignore("Having trouble testing. How do we actually validate that what came back is" +
        "what we want. Or is checking the set properties enough")
    public void getInstance_default_returnsChromeBad() throws MalformedURLException {
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        Capabilities stubCaps = mock(Capabilities.class);
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(stubCaps);
        when(stubRemoteManager.getRemoteWebDriver(anyString(),anyObject())).thenReturn(remoteWebDriver);
        sauceSession = new SauceSession(stubRemoteManager);

        WebDriver driver = sauceSession.getInstance();

        String actualBrowser = (((RemoteWebDriver) driver).getCapabilities()).getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("chrome"));
    }
}
