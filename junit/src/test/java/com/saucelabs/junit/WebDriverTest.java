package com.saucelabs.junit;

import com.saucelabs.common.SauceAuthentication;
import com.saucelabs.common.SauceSessionIdProvider;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ross Rowe
 */
public class WebDriverTest implements SauceSessionIdProvider {

    public SauceAuthentication authentication = new SauceAuthentication();

    public
    @Rule
    SauceTestWatcher resultReportingTestWatcher = new
        SauceTestWatcher(this, authentication);

    public
    @Rule
    TestName testName = new TestName();

    private WebDriver driver;
    private String sessionId;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("version", "5");
        capabilities.setCapability("platform", Platform.WIN10);
        capabilities.setCapability("name", "xxTest :"
                + testName.getMethodName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" +
                        authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();

    }

    @Test
    public void basic() {
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals("I am a page title - Sauce Labs", driver.getTitle());
    }

    @After
    public void tearDown() {
        assertNotNull(driver);
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

}