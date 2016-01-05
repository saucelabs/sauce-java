package com.saucelabs.junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
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
public class WebDriverTest implements SauceOnDemandSessionIdProvider {

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    public
    @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new
            SauceOnDemandTestWatcher(this, authentication);

    public
    @Rule
    TestName testName = new TestName();

    private WebDriver driver;
    private String sessionId;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("version", "5");
        capabilities.setCapability("platform", Platform.XP);
        capabilities.setCapability("name", "xxTest :"
                + testName.getMethodName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" +
                        authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();

    }

    @Test
    public void basic() throws Exception {
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals("I am a page title - Sauce Labs", driver.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        assertNotNull(driver);
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

}