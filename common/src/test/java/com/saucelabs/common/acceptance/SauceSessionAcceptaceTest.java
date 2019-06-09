package com.saucelabs.common.acceptance;

import com.saucelabs.remotedriver.SafariVersion;
import com.saucelabs.remotedriver.SauceSession;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SauceSessionAcceptaceTest {

    private WebDriver webDriver;
    @After
    public void cleanUp()
    {
        webDriver.quit();
    }
    @Test
    public void startSession_noSauceOptionsSet_returnsDriver() throws MalformedURLException {
        SauceSession session = new SauceSession().start();
        webDriver = session.getDriver();
        assertNotNull(webDriver);
    }

    @Test
    public void getInstance_nonDefaultCapabilities_returnsCorrectDriver() throws MalformedURLException {
        webDriver = new SauceSession().withFirefox().getWebDriver();
        String actualBrowser = (((RemoteWebDriver) webDriver).getCapabilities()).getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("firefox"));
    }
    @Test
    public void safari_osNotSet_returnsValidSafariSession() throws MalformedURLException {
        webDriver = new SauceSession().withSafari(SafariVersion.elevenDotOne).getWebDriver();
        String actualBrowser = (((RemoteWebDriver) webDriver).getCapabilities()).getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("safari"));
    }
}
