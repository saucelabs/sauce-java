package com.saucelabs.junit;

import com.saucelabs.common.SauceAuthentication;
import com.saucelabs.common.SauceSessionIdProvider;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;


/**
 * @author Ross Rowe
 */
@RunWith(ConcurrentParameterized.class)
public class WebDriverParallelWithHelper implements SauceSessionIdProvider {

    public SauceAuthentication authentication = new SauceAuthentication();

    public
    @Rule
    SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher(this, authentication);

    public
    @Rule
    TestName testName = new TestName();

    private WebDriver driver;
    private String sessionId;

    private String browser;
    private String os;
    private String version;

    public WebDriverParallelWithHelper(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    @Parameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList<String[]> browsers = new LinkedList<String[]>();
        browsers.add(new String[]{Platform.MAC.toString(), "5.0", "iPhone"});
        return browsers;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, Platform.valueOf(os));
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:443/wd/hub"),
                capabilities);
        this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Test
    @Ignore
    public void basic() {
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals("I am a page title - Sauce Labs", driver.getTitle());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
