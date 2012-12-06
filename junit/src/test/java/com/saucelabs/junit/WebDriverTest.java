package com.saucelabs.junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import junit.framework.TestResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * @author Ross Rowe
 */
public class WebDriverTest implements SauceOnDemandSessionIdProvider {

    public SauceOnDemandAuthentication authentication = new
            SauceOnDemandAuthentication("sbukhari", "08a5e7d7-50b5-47a2-a1e7-b2ba8f221522");

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
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        TestResult result = new TestResult();
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;

    }

}