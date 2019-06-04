package com.saucelabs.common.unit;

import com.saucelabs.common.SauceSession;
import com.saucelabs.remotedriver.RemoteDriverInterface;
import com.saucelabs.remotedriver.SauceOptions;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SauceSessionTest {
    private SauceSession session;

    @Test
    public void defaultConstructor_called_returnsObject()
    {
        session = new SauceSession();
        assertNotNull(session);
    }
    @Test
    public void getDriver_called_returnsWebDriver() {
        WebDriver stubDriver = mock(WebDriver.class);
        SauceSession session = new SauceSession(stubDriver);
        assertTrue(session.getDriver() instanceof WebDriver);
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultChrome()
    {
        SauceSession session = new SauceSession();
        String actualBrowser = session.getBrowser();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("Chrome"));
    }
    @Test
    public void noSauceOptionsSet_instantiated_defaultOsWindows10() throws MalformedURLException {
        RemoteDriverInterface stubRemoteDriver = mock(RemoteDriverInterface.class);
        SauceSession session = new SauceSession(stubRemoteDriver);
        session.start();
        String actualOperatingSystem = session.getOs();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }
    @Test
    public void noSauceOptionsSet_instantiated_latestBrowserVersion()
    {
        SauceSession session = new SauceSession();
        String actualOperatingSystem = session.getBrowserVersion();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("latest"));
    }
    @Test
    public void sauceOptionsSet_withOnlyWindows10_returnsWindows10() throws MalformedURLException {
        SauceOptions options = new SauceOptions();
        options.setOs("Windows 10");

        RemoteDriverInterface stubRemoteDriver = mock(RemoteDriverInterface.class);
        SauceSession session = new SauceSession(options, stubRemoteDriver);
        session.start();
        String actualOperatingSystem = session.getOs();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }
}
