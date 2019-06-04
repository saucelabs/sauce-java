package com.saucelabs.common.unit;

import com.saucelabs.common.SauceSession;
import com.saucelabs.remotedriver.SauceOptions;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

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
    public void noSauceOptionsSet_instantiated_defaultLinux()
    {
        SauceSession session = new SauceSession();
        String actualOperatingSystem = session.getOs();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("Linux"));
    }
    @Test
    public void noSauceOptionsSet_instantiated_latestBrowserVersion()
    {
        SauceSession session = new SauceSession();
        String actualOperatingSystem = session.getBrowserVersion();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("latest"));
    }
    @Test
    public void start_noSauceOptions_returnsChrome() throws MalformedURLException {
        SauceSession session = new SauceSession();
        session.start();
        WebDriver driver = session.getDriver();
        String actualBrowser = (((RemoteWebDriver) driver).getCapabilities()).getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("chrome"));
    }
    @Test
    @Ignore("need to implement")
    public void sauceOptionsSet_withOnlyWindows10_returnsWindows10()
    {
        SauceOptions options = new SauceOptions("","");
        SauceSession session = new SauceSession();
        String actualOperatingSystem = session.getBrowserVersion();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("latest"));
    }
}
