package com.saucelabs.common.unit;

import com.saucelabs.common.SauceSession;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SauceSessionTest {
    @Test
    public void defaultConstructor_called_returnsObject()
    {
        SauceSession session = new SauceSession();
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
}
