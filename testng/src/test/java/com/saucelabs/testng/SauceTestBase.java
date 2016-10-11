package com.saucelabs.testng;

/**
 * @author Ross Rowe
 */

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Listeners;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;


/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class SauceTestBase implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    /**
     * Constructs a {@link com.saucelabs.common.SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link com.saucelabs.common.SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    protected ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    protected ThreadLocal<String> sessionId = new ThreadLocal<String>();

    protected static final Logger logger = Logger.getLogger(SauceTestBase.class.getName());


    /**
     * Creates a new {@link RemoteWebDriver} instance which is configured
     * @param browser Browser to use
     * @param version Browser version to use
     * @param os VM OS/Platform OS
     * @return webdriver instance created
     * @throws MalformedURLException
     */
    protected WebDriver createDriver(String browser, String version, String os) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", os);
        webDriver.set(new RemoteWebDriver(
            new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:443/wd/hub"),
            capabilities));
        sessionId.set(((RemoteWebDriver) getWebDriver()).getSessionId().toString());
        return webDriver.get();
    }



    public WebDriver getWebDriver() {
        System.out.println("WebDriver" + webDriver.get());
        return webDriver.get();
    }

    public String getSessionId() {
        return sessionId.get();
    }

    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }
}
