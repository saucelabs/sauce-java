package com.saucelabs.common.acceptance;

import com.saucelabs.remotedriver.SafariVersion;
import com.saucelabs.remotedriver.SauceSession;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.After;
import org.junit.Ignore;
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
        if(webDriver != null)
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
        webDriver = new SauceSession().withFirefox().start().getDriver();
        String actualBrowser = getBrowserNameFromCapabilities();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("firefox"));
    }
    @Test
    public void withSafari_osNotSet_returnsValidSafariSession() throws MalformedURLException {
        webDriver = new SauceSession().withMacOsMojave().start().getDriver();
        String actualBrowser = getBrowserNameFromCapabilities();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("safari"));
    }
    @Test
    public void newSession_default_canUseSauceApi() throws MalformedURLException {
        SauceSession sauceLabs = new SauceSession().start();

        sauceLabs.test.comment("sample test comment");
        sauceLabs.test.setTestStatus("true");
        sauceLabs.test.setTestName("MyTestName");

        sauceLabs.stop();
    }
    @Test
    public void withSafari_differentVersion_returnsValidSession() throws MalformedURLException {
        webDriver = new SauceSession().
            withBrowserVersion(SafariVersion.elevenDotOne).
            withMacOsHighSierra().
            start().
            getDriver();

        String actualBrowser = getBrowserNameFromCapabilities();
        String actualBrowserVersion = (((RemoteWebDriver) webDriver).getCapabilities()).getVersion();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("safari"));
        assertThat(actualBrowserVersion, IsEqualIgnoringCase.equalToIgnoringCase("13605.3.8"));
    }

    private String getBrowserNameFromCapabilities() {
        return (((RemoteWebDriver) webDriver).getCapabilities()).getBrowserName();
    }

    @Test
    @Ignore("Not sure how to make it work")
    public void withEdge_default_returnsValidEdgeSession() throws MalformedURLException {
        webDriver = new SauceSession().withEdge().start().getDriver();
        String actualBrowser = getBrowserNameFromCapabilities();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("edge"));
    }
    @Test
    @Ignore("Not sure how to make it work")
    public void withIE_default_returnsValidIESession() throws MalformedURLException {
        webDriver = new SauceSession().withIE().start().getDriver();
        String actualBrowser = getBrowserNameFromCapabilities();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("IE"));
    }
    @Test
    @Ignore("Invalid Use Case: I don't want the use to be able to do this")
    public void withIE_nonDefaultOs_returnsValidIESession() throws MalformedURLException {
        webDriver = new SauceSession().withIE().withPlatform("Linux").start().getDriver();
        String actualBrowser = getBrowserNameFromCapabilities();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("IE"));
    }
    @Test
    @Ignore("No clue why this doesn't work")
    public void withIE_nonDefaultVersion_returnsValidIESession() throws MalformedURLException {
        webDriver = new SauceSession().withIE().withBrowserVersion("11.285").start().getDriver();
        String actualBrowser = getBrowserNameFromCapabilities();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("IE"));
    }
}
