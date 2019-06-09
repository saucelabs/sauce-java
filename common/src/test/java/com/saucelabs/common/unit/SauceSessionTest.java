package com.saucelabs.common.unit;

import com.saucelabs.remotedriver.SauceSession;
import com.saucelabs.remotedriver.RemoteDriverInterface;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SauceSessionTest {
    private SauceSession sauceSession;

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
        SauceSession factory = new SauceSession();
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
