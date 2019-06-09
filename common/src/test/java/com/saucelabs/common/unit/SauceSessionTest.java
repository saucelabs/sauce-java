package com.saucelabs.common.unit;

import com.saucelabs.common.SauceSession;
import com.saucelabs.remotedriver.DriverFactory;
import com.saucelabs.remotedriver.RemoteDriverInterface;
import com.saucelabs.remotedriver.SauceOptions;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SauceSessionTest {
    private SauceOptions sauceOptions;
    private SauceSession sauceSession;

    @Test
    public void getDriver_called_returnsWebDriver() {
        WebDriver stubDriver = mock(WebDriver.class);
        SauceSession session = new SauceSession(stubDriver);
        assertTrue(session.getDriver() instanceof WebDriver);
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultChrome()
    {
        sauceSession = new SauceSession();
        String actualBrowser = sauceSession.getBrowser();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("Chrome"));
    }
    @Test
    public void noSauceOptionsSet_instantiated_defaultOsWindows10() throws MalformedURLException {
        RemoteDriverInterface stubRemoteDriver = mock(RemoteDriverInterface.class);
        sauceSession = new SauceSession(stubRemoteDriver);

        sauceSession.start();

        String actualOperatingSystem = sauceSession.getOs();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }

    @Test
    public void sauceOptionsSet_withOnlyWindows10_returnsWindows10() throws MalformedURLException {
        sauceOptions = new SauceOptions();
        sauceOptions.setOs("Windows 10");

        RemoteDriverInterface stubRemoteDriver = mock(RemoteDriverInterface.class);
        SauceSession session = new SauceSession(sauceOptions, stubRemoteDriver);
        session.start();
        String actualOperatingSystem = session.getOs();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }

    @Test
    @Ignore("need to implement")
    public void sauceOptionsSet_withNewConfiguration_returnsCorrectConfiguration() throws MalformedURLException {
        DriverFactory factory = new DriverFactory();
        factory.withFirefox();
        //factory.withChrome().withSauceLabs();
        //factory.withBrowser(new ChromeConfiguration().withVersion("latest").withPlatform("Windows 10"));
        RemoteDriverInterface stubRemoteDriver = mock(RemoteDriverInterface.class);
        SauceSession session = new SauceSession(sauceOptions, stubRemoteDriver);

        session.start();

        assertThat(session.getOs(), IsEqualIgnoringCase.equalToIgnoringCase("macOS 10.14"));
        assertThat(session.getBrowser(), IsEqualIgnoringCase.equalToIgnoringCase("safari"));
        assertThat(session.getBrowserVersion(), IsEqualIgnoringCase.equalToIgnoringCase("12.0"));
    }
}
