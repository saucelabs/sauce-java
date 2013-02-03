package com.saucelabs.junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import org.junit.After;
import org.junit.Before;
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
 * Demonstrates how to write a JUnit test that runs tests against Sauce OnDemand in parallel.
 *
 * @author Ross Rowe
 */
@RunWith(ConcurrentParameterized.class)
public class WebDriverParallelTest {

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    private String browser;
    private String os;
    private String version;

    public WebDriverParallelTest(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() throws Exception {
        LinkedList browsers = new LinkedList();
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
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
    }

    @Test
    public void webDriverOne() throws Exception {

        printSessionId("webDriverOne");
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    @Test
    public void webDriverTwo() throws Exception {
        printSessionId("webDriverTwo");
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    private void printSessionId(String testName) {

        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", (((RemoteWebDriver) driver).getSessionId()).toString(), testName);
        System.out.println( Thread.currentThread().getName() + " : " + message);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
