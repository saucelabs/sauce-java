package com.saucelabs.junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test is annotated with the {@link ConcurrentParameterized} test runner...
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher}...
 *
 * @author Ross Rowe
 */
@RunWith(ConcurrentParameterized.class)
public class SampleSauceTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    private String browser;
    private String os;
    private String version;
    /**
     *
     */
    private String sessionId;

    public SampleSauceTest(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {

        LinkedList<String[]> browsers = new LinkedList<String[]>();
        browsers.add(new String[]{"Windows 2003", null, "chrome"});
        browsers.add(new String[]{"Windows 2003", "17", "firefox"});
        browsers.add(new String[]{"linux", "17", "firefox"});
        return browsers;
    }

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:443/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    @Test
    public void webDriverOne() {
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals("I am a page title - Sauce Labs", driver.getTitle());
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
