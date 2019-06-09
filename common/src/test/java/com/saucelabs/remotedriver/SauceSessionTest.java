package com.saucelabs.remotedriver;

import org.hamcrest.text.IsEqualIgnoringCase;
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

    @Test
    public void defaultConstructor_instantiated_returnsFactory()
    {
        sauceSession = new SauceSession();
        assertNotNull(sauceSession);
    }

    @Test
    public void seleniumServer_notSet_returnsNull()
    {
        //TODO is this okay to be like a property,
        //or should it be a getSeleniumServer()
        sauceSession = new SauceSession();
        assertNull(sauceSession.seleniumServer);
    }

    @Test
    public void defaultConstructor_instantiated_setsConcreteDriverManager()
    {
        sauceSession = new SauceSession();
        assertThat(sauceSession.getDriverManager(), instanceOf(ConcreteRemoteDriverManager.class));
    }

    @Test
    public void getInstance_serverNotSet_setsSauceSeleniumServer() throws MalformedURLException {
        sauceSession = getSessionWithFakeDriverInstance();
        sauceSession.getInstance();
        String expectedServer = "https://ondemand.saucelabs.com/wd/hub";
        assertEquals(expectedServer, sauceSession.seleniumServer);
    }
    @Test
    @Ignore("old")
    public void getInstance_default_setsCapabilitiesToChrome() throws MalformedURLException {
        sauceSession = getSessionWithFakeDriverInstance();

        sauceSession.getInstance();

        String actualBrowser = sauceSession.capabilities.getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("chrome"));
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultIsChrome()
    {
        sauceSession = new SauceSession();
        String actualBrowser = sauceSession.getCapabilities().getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("Chrome"));
    }
    @Test
    @Ignore("old")
    public void getInstance_default_setsCapabilityToLinux() throws MalformedURLException {
        sauceSession = getSessionWithFakeDriverInstance();

        sauceSession.getInstance();

        String actualBrowser = sauceSession.capabilities.getPlatform().name();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultIsWindows10() throws MalformedURLException {
        sauceSession = getSessionWithFakeDriverInstance();
        sauceSession.getInstanceOld();
        String actualOs = sauceSession.capabilities.getPlatform().name();
        assertThat(actualOs, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }
    private SauceSession getSessionWithFakeDriverInstance() {
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        return new SauceSession(stubRemoteManager);
    }
    @Test
    public void sauceOptions_defaultConfiguration_latestBrowserVersion()
    {
        MutableCapabilities caps = new SauceSession().getCapabilities();
        String actualOperatingSystem = caps.getCapability("browserVersion").toString();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("latest"));
    }
    @Test
    public void sauceOptions_defaultConfiguration_setsSauceOptions()
    {
        sauceSession = new SauceSession();
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
